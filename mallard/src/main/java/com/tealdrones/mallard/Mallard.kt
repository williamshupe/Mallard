package com.tealdrones.mallard

import com.tealdrones.mallard.controller.OnErrorListener
import com.tealdrones.mallard.controller.OnResponseListener
import com.tealdrones.mallard.controller.Quacker
import com.tealdrones.mallard.model.DeleteIncubator
import com.tealdrones.mallard.model.Propfind

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

        @JvmStatic var _instance : Mallard? = null
        @JvmStatic val instance: Mallard
            get() {
                _instance = _instance ?: Mallard()
                return _instance!!
            }

    }

}

class MallardException(val mallardMessage: String) : Exception() {
    override val message: String?
        get() = "MallardException : $mallardMessage"
}
