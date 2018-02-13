package com.tealdrones.mallard.communication;

import com.tealdrones.mallard.model.RequestError;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to make a DELETE request to a WebDAV server.
 */
public class DeleteRequest implements WebDAVRequest {
    protected String url;
    protected static final String method = "DELETE";
    protected Map<String, String> headers;
    protected OnDeleteRequestCompleteListener listener;

    public DeleteRequest() {
        headers = new HashMap<>();
    }

    public void run() {
        Request request = new Request.Builder()
                .setUrl(url)
                .setMethod(method)
                .addHeaders(headers)
                .build();

        try (Response response = new Call(request).execute()) {
            if (response.code() != 204) {
                RequestError requestError = new RequestError(RequestError.Type.fromStatusCode(response.code()));
                error(requestError);
                return;
            }

            success();
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

    protected void success() {
        if (listener != null) {
            listener.onSuccess();
        }
    }

    public static class Builder {
        protected String url;
        protected Map<String, String> headers;
        protected OnDeleteRequestCompleteListener listener;

        public Builder() {
            headers = new HashMap<>();
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder addHeaders(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public Builder setListener(OnDeleteRequestCompleteListener listener) {
            this.listener = listener;
            return this;
        }

        public DeleteRequest build() {
            DeleteRequest request = new DeleteRequest();
            request.url = this.url;
            request.headers.putAll(this.headers);
            request.listener = this.listener;
            return request;
        }
    }
}
