package com.teal.mallard

import com.teal.mallard.controller.OnErrorListener
import com.teal.mallard.controller.OnResponseListener
import com.teal.mallard.controller.Quacker
import com.teal.mallard.model.DeleteIncubator
import com.teal.mallard.model.Propfind

/**
 * Created by kodyvanry on 7/26/17
 */
class Mallard {

    var url: String? = null
        get() {
            return field ?: throw MallardException("URL never was set")
        }

    /**
     * Get a list of a directory in WebDav
     *
     * @param folder the folder inside of the WebDav server to run the PROPFIND on
     * @param onResponseListener the listener that will listen for when the response comes through
     * @param onErrorListener the listener that will be called if an error occurs
     */
    fun getList(folder: String, requestCode: Int, onResponseListener: OnResponseListener?, onErrorListener: OnErrorListener?) {
        val propfind = Propfind(folder)
        propfind.requestCode = requestCode
        propfind.onResponseListener = onResponseListener
        propfind.onErrorListener = onErrorListener
        Quacker().quack(propfind)
    }


    /**
     * Get a list of a directory in WebDav
     *
     * @param file the file on the WebDav server to run the DELETE on
     * @param onResponseListener the listener that will listen for when the response comes through
     * @param onErrorListener the listener that will be called if an error occurs
     */
    fun delete(file: String, requestCode: Int, onResponseListener: OnResponseListener?, onErrorListener: OnErrorListener?) {
        val delete = DeleteIncubator(this, file).hatch()
        delete.requestCode = requestCode
        delete.onResponseListener = onResponseListener
        delete.onErrorListener = onErrorListener
        Quacker().quack(delete)
    }

    companion object {

        @JvmStatic val instance by lazy { Mallard() }

    }

}

class MallardException(val mallardMessage: String) : Exception() {
    override val message: String?
        get() = "MallardException : $mallardMessage"
}
