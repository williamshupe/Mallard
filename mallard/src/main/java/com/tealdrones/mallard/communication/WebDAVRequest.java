package com.tealdrones.mallard.communication;

/**
 * A request that can be enqueued and executed with a {@link Communicator}.
 */
public interface WebDAVRequest {
    /**
     * Make the request to the WebDAV server.
     */
    void run();
}
