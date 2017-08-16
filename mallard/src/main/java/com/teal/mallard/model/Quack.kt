package com.teal.mallard.model

import com.teal.mallard.controller.OnErrorListener
import com.teal.mallard.controller.OnResponseListener

/**
 * Created by kodyvanry on 7/26/17
 *
 * A Quack is a form of communication with the WebDAV server. Just like a duck would quack so must
 * a Mallard instance quack to the server and get a quack back.
 *
 * @property url the url to run the quack on
 * @property method the method used to query the WebDav server
 * @property onResponseListener [OnResponseListener] to be called when the [Quack] gets a [QuackResponse]
 * @property onErrorListener [OnErrorListener] to be called if the [Quack] gets an error
 */
abstract class Quack(val url: String, val method: String, var onResponseListener: OnResponseListener? = null, var onErrorListener: OnErrorListener? = null, var requestCode: Int = 0) {

    val headers = HashMap<String, String>()

    abstract fun getRequestBody() : String

    abstract fun getContentType() : String
}