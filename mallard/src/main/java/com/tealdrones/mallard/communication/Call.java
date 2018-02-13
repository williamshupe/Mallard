package com.tealdrones.mallard.communication;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * A class that transitions a {@link Request} into a {@link Response}.
 *
 * A {@link Request} sets up all the values for the connection but a
 * {@link Call} actually makes the connection to the remote WebDAV server.
 */
public class Call {
    protected static final int CONNECTION_TIMEOUT = 10 * 1000;
    protected Request request;

    public Call(Request request) {
        this.request = request;
    }

    /**
     * Take a {@link Request}, make a connection to the WebDAV server, and
     * produce a {@link Response}.
     *
     * @return a {@link Response} representing the response of the WebDAV server.
     * @throws IOException if no protocol is specified, or an unknown protocol is found.
     */
    public Response execute() throws IOException {
        URL url = new URL(request.getUrl());

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // HttpURLConnection doesn't support the PROPFIND method so this is what
        // we have to do to work around that.
        if ("PROPFIND".equals(request.getMethod())) {
            try {
                final Object target;
                if (connection instanceof HttpsURLConnection) {
//                    final Field delegate = HttpsURLConnection.class.getDeclaredField("delegate");
//                    delegate.setAccessible(true);
//                    target = delegate.get(connection);
                    target = connection;
                } else {
                    target = connection;
                }
                final Field f = HttpURLConnection.class.getDeclaredField("method");
                f.setAccessible(true);
                f.set(target, request.getMethod());
            } catch (IllegalAccessException | NoSuchFieldException ex) {
                throw new AssertionError(ex);
            }
        } else {
            connection.setRequestMethod(request.getMethod());
        }

        for(Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        connection.setConnectTimeout(CONNECTION_TIMEOUT);

        connection.connect();

        return new Response(connection);
    }
}
