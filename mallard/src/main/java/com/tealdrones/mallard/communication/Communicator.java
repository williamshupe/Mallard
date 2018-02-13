package com.tealdrones.mallard.communication;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A class to enqueue and execute {@link WebDAVRequest}s.
 */
public class Communicator {
    private static Communicator communicator;
    private static Queue<WebDAVRequest> requestQueue;
    private Thread queueThread = null;

    public static Communicator getInstance() {
        if (communicator == null) {
            communicator = new Communicator();
        }

        return communicator;
    }

    private Communicator() {
        requestQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * @param request a request to be run.
     */
    public void enqueue(WebDAVRequest request) {
        requestQueue.add(request);

        if (queueThread == null || !queueThread.isAlive()) {
            queueThread = new Thread(new RequestQueueProcessor());
            queueThread.start();
        }
    }

    private class RequestQueueProcessor implements Runnable {
        @Override
        public void run() {
            while (requestQueue.size() > 0) {
                WebDAVRequest currentRequest = requestQueue.poll();

                currentRequest.run();
            }
        }
    }
}
