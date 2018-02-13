package com.tealdrones.mallard;

import com.tealdrones.mallard.communication.Communicator;
import com.tealdrones.mallard.communication.DeleteRequest;
import com.tealdrones.mallard.communication.Depth;
import com.tealdrones.mallard.communication.OnDeleteRequestCompleteListener;
import com.tealdrones.mallard.communication.OnPropfindRequestCompleteListener;
import com.tealdrones.mallard.communication.PropfindRequest;

/**
 * {@link Mallard} is a library to enable making WebDAV requests easy.
 *
 * {@link Mallard} implements two WebDAV methods: `PROPFIND` and `DELETE`.
 */
public class Mallard {
    /**
     * Retrieves a list of files and directories from a WebDAV server.
     *
     * Retrieves a {@link java.util.List} of {@link com.tealdrones.mallard.model.RemoteFile} from
     * a WebDAV server. Defaults to a Depth of {@link Depth#ONE}.
     *
     * @param url The full url of the file or directory to list.
     * @param listener A {@link OnPropfindRequestCompleteListener} to be called when the request
     *                 returns from the server.
     */
    public static void propfind(String url, OnPropfindRequestCompleteListener listener) {
        propfind(url, Depth.ONE, listener);
    }

    /**
     * Retrieves a list of files and directories from a WebDAV server.
     *
     * Retrieves a {@link java.util.List} of {@link com.tealdrones.mallard.model.RemoteFile} from
     * a WebDAV server.
     *
     * @param url The full url of the file or directory to list.
     * @param depth The depth of the request.
     * @param listener A {@link OnPropfindRequestCompleteListener} to be called when the request
     *                 returns from the server.
     */
    public static void propfind(String url, Depth depth, OnPropfindRequestCompleteListener listener) {
        PropfindRequest request = new PropfindRequest.Builder()
                .setUrl(url)
                .setDepth(depth)
                .setListener(listener)
                .build();
        Communicator.getInstance().enqueue(request);
    }

    /**
     * Deletes a file or directory on a WebDAV server.
     *
     * @param url The full url of the file or directory to delete.
     * @param listener A {@link OnDeleteRequestCompleteListener} to be called when the request
     *                 returns from the server.
     */
    public static void delete(String url, OnDeleteRequestCompleteListener listener) {
        DeleteRequest request = new DeleteRequest.Builder()
                .setUrl(url)
                .setListener(listener)
                .build();
        Communicator.getInstance().enqueue(request);
    }
}
