package com.teal.mallard.model

import com.teal.mallard.Mallard

/**
 * Created by kodyvanry on 8/16/17
 *
 * Delete is a subclass of [Quack]
 * <p>
 *     Delete's purpose is to delete a file from a WebDav server
 * </p>
 *
 * @see Quack
 */
class Delete internal constructor(url: String): Quack(url, "DELETE") {

    override fun getContentType(): String {
        return "text/plain"
    }

    override fun getRequestBody(): String {
        return ""
    }
}

/**
 * [DeleteIncubator] is how you construct a [Delete] [Quack]
 *
 * @property mallard [Mallard] instance from your application
 * @property file the file you would like to delete on WebDav
 */
class DeleteIncubator(val mallard: Mallard, val file: String) {

    /**
     * Hatch your incubator to bring your [Delete] [Quack] to life
     *
     * @return an instance of [Delete]
     */
    fun hatch() : Delete {
        return Delete(mallard.url!! + "/" + file)
    }

}