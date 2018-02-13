package com.tealdrones.mallard.communication;

import java.util.HashMap;
import java.util.Map;

/**
 * A representation of a WebDAV request.
 */
public class Request {
    protected String url;
    protected String method;
    protected Map<String, String> headers;

    public Request() {
        headers = new HashMap<>();
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public static class Builder {
        protected String url;
        protected String method;
        protected Map<String, String> headers;

        public Builder() {
            headers = new HashMap<>();
        }

        public Request build() {
            Request request = new Request();
            request.url = this.url;
            request.method = this.method;
            request.headers.putAll(headers);
            return request;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder addHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder addHeaders(Map<String, String> headers) {
            this.headers.putAll(headers);
            return this;
        }
    }
}
