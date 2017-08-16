package com.teal.mallard.controller;

import com.teal.mallard.model.QuackErrorResponse;
import com.teal.mallard.model.QuackResponse;

/**
 * Created by kodyvanry on 8/16/17
 *
 * <p>
 *     OnErrorListener is an interface where {@link OnResponseListener#onResponse(QuackResponse)}
 *     is called during a {@link com.teal.mallard.model.Quack} conversation when a
 *     {@link QuackResponse} is available
 * </p>
 */
public interface OnResponseListener {
    void onResponse(QuackResponse response);
}
