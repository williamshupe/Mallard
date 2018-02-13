package com.tealdrones.mallard.model;

/**
 * An encapsulation of errors that can happen during WebDAV requests.
 *
 * {@link RequestError}s have a {@link #errorType} and an optional {@link #errorException}.
 * The {@link #errorType} gives a general idea why the request failed. The optional
 * {@link #errorException} is the exception that caused the request to fail.
 */
public class RequestError {
    private Type errorType;
    private Exception errorException;

    /**
     * The type of error of a WebDAV request.
     */
    public enum Type {
        /**
         * An unknown error.
         */
        UNKNOWN_ERROR,

        /**
         * An error that happened with network communication.
         */
        NETWORK_ERROR,

        /**
         * An error when a response is expected to have a response body but is empty.
         */
        EMPTY_RESPONSE,

        /**
         * The server responded with a payload but the XML is invalid.
         */
        XML_PARSING_ERROR,

        /**
         * The WebDAV server responded with 403
         */
        HTTP_403_FORBIDDEN,

        /**
         * The WebDAV server responded with 404
         */
        HTTP_404_NOT_FOUND,

        /**
         * The WebDAV server responded with 423
         */
        HTTP_423_LOCKED;

        public static Type fromStatusCode(int statusCode) {
            switch (statusCode) {
                case 403:
                    return HTTP_403_FORBIDDEN;
                case 404:
                    return HTTP_404_NOT_FOUND;
                case 423:
                    return HTTP_423_LOCKED;
                default:
                    return UNKNOWN_ERROR;
            }
        }
    }

    public RequestError(Type errorType) {
        this(errorType, null);
    }

    public RequestError(Type errorType, Exception errorException) {
        this.errorType = errorType;
        this.errorException = errorException;
    }

    /**
     * @return The error type.
     */
    public Type getErrorType() {
        return errorType;
    }

    /**
     * @return The exception that caused the error.
     */
    public Exception getErrorException() {
        return errorException;
    }
}
