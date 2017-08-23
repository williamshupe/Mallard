package com.tealdrones.mallard.controller;

import com.tealdrones.mallard.model.QuackErrorResponse;
import com.tealdrones.mallard.model.QuackResponse;

/**
 * Created by kodyvanry on 8/16/17
 *
 * <p>
 *     OnErrorListener is an interface where {@link OnResponseListener#onResponse(QuackResponse)}
 *     is called during a {@link com.tealdrones.mallard.model.Quack} conversation when a
 *     {@link QuackResponse} is available
 * </p>
 */
public interface OnResponseListener {
    void onResponse(QuackResponse response);
}
