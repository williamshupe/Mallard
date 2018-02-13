package com.tealdrones.mallard.communication;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * A representation of the response from a WebDAV server.
 */
public class Response implements Closeable{
    protected HttpURLConnection urlConnection;
    protected InputStream connectionInputStream = null;

    public Response(HttpURLConnection urlConnection) {
        this.urlConnection = urlConnection;
    }

    /**
     * @return The response code from the WebDAV server or -1 if the connection failed.
     */
    public int code() {
        try {
            return urlConnection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * @return The {@link InputStream} of the response body of the WebDAV request.
     * @throws IOException if an I/O exception happens when creating the {@link InputStream}
     */
    public InputStream getInputStream() throws IOException {
        connectionInputStream = urlConnection.getInputStream();
        return connectionInputStream;
    }

    @Override
    public void close() throws IOException {
        if (connectionInputStream != null) {
            connectionInputStream.close();
        }
    }
}
