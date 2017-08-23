package com.tealdrones.mallard.model

/**
 * Created by kodyvanry on 8/7/17
 * <p>
 * A QuackResponse is a wrapper for an OkHttp response from WebDav
 * </p>
 *
 * @constructor creates an instance of QuackResponse
 */
class QuackErrorResponse(val requestCode: Int, val exception: Exception) {

}