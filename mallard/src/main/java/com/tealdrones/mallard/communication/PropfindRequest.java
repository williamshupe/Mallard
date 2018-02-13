package com.tealdrones.mallard.communication;

import com.tealdrones.mallard.model.RemoteFile;
import com.tealdrones.mallard.model.RequestError;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

/**
 * A class to make a PROPFIND request to a WebDAV server.
 */
public class PropfindRequest implements WebDAVRequest {
    protected static final String METHOD = "PROPFIND";

    protected String url;
    protected Map<String, String> headers;
    protected OnPropfindRequestCompleteListener listener;

    public PropfindRequest() {
        headers = new HashMap<>();
        headers.put("Content-Type", "text/xml");
    }

    public void run() {
        // TODO: add Propfind XML body when WebDAV server supports it.
        Request request = new Request.Builder()
                .setUrl(url)
                .setMethod(METHOD)
                .addHeaders(headers)
                .build();

        try (Response response = new Call(request).execute()) {
            if (response.code() != 207) {
                RequestError requestError = new RequestError(RequestError.Type.fromStatusCode(response.code()));
                error(requestError);
                return;
            }

            try (InputStream responseStream = response.getInputStream()) {
                PropfindResponseParser parser = new PropfindResponseParser();

                List<RemoteFile> remoteFiles = parser.parse(responseStream);

                success(remoteFiles);
            } catch (SAXException | ParserConfigurationException | IOException e) {
                RequestError requestError = new RequestError(RequestError.Type.XML_PARSING_ERROR, e);
                error(requestError);
            }
        } catch (IOException e) {
            RequestError requestError = new RequestError(RequestError.Type.NETWORK_ERROR, e);
            error(requestError);
        }
    }

    protected void error(RequestError requestError) {
        if (listener != null) {
            listener.onError(requestError);
        }
    }

    protected void success(List<RemoteFile> remoteFiles) {
        if (listener != null) {
            listener.onSuccess(remoteFiles);
        }
    }

    public static class Builder {
        protected String url;
        protected Map<String, String> headers;
        protected OnPropfindRequestCompleteListener listener;

        public Builder() {
            headers = new HashMap<>();
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setDepth(Depth depth) {
            headers.put("DEPTH", depth.toString());
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder setListener(OnPropfindRequestCompleteListener listener) {
            this.listener = listener;
            return this;
        }

        public PropfindRequest build() {
            PropfindRequest request = new PropfindRequest();
            request.url = this.url;
            request.headers.putAll(this.headers);
            request.listener = this.listener;
            return request;
        }
    }
}
