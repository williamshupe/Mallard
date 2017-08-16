package com.teal.mallard.controller;

import com.teal.mallard.model.QuackErrorResponse;

/**
 * Created by kodyvanry on 8/16/17
 *
 * <p>
 *     OnErrorListener is an interface where {@link OnErrorListener#onError(QuackErrorResponse)}
 *     is called when an error occurs during a Quack conversation
 * </p>
 */
public interface OnErrorListener {
    void onError(QuackErrorResponse quackErrorResponse);
}
