package com.tealdrones.mallard.communication;

import com.tealdrones.mallard.model.RequestError;

/**
 * A callback interface for the results of a DELETE request.
 */
public interface OnDeleteRequestCompleteListener {
    /**
     * A method called when the DELETE operation succeeds.
     */
    void onSuccess();

    /**
     * A method called when a DELETE operation fails.
     * @param error A {@link RequestError} with details on why the request failed.
     */
    void onError(RequestError error);
}
