package com.teal.mallard.model

import com.teal.mallard.controller.QuackParser
import okhttp3.Response

/**
 * Created by kodyvanry on 8/7/17
 * <p>
 * A QuackResponse is a wrapper for an OkHttp response from WebDav
 * </p>
 *
 * @constructor creates an instance of QuackResponse
 */
class QuackResponse(val requestCode: Int, val response: Response) {

    val ducks = ArrayList<Duck>()
    val succesful = response.isSuccessful
        @JvmName("isSuccesful") get

    init {
        if (succesful)
            ducks.addAll(QuackParser(response.body().string()).parse())
    }

}