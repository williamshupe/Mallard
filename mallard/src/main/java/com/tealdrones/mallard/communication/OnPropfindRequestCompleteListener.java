package com.tealdrones.mallard.communication;

import com.tealdrones.mallard.model.RemoteFile;
import com.tealdrones.mallard.model.RequestError;

import java.util.List;

/**
 * A callback interface for the results of a PROPFIND request.
 */
public interface OnPropfindRequestCompleteListener {
    /**
     * A method called when a {@link PropfindRequest} succeeds.
     * @param remoteFiles The list of {@link RemoteFile} returned from the WebDAV server.
     */
    void onSuccess(List<RemoteFile> remoteFiles);

    /**
     * A method called when a {@link PropfindRequest} fails.
     * @param error A {@link RequestError} with details on why the request failed.
     */
    void onError(RequestError error);
}
