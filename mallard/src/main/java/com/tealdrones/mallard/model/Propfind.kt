package com.tealdrones.mallard.model

import com.tealdrones.mallard.Mallard

/**
 * Created by kodyvanry on 7/26/17
 * <p>
 * [Propfind] is a class used to query the WebDav server for a directory listing
 * </p>
 * @property folder folder to get a listing of
 * @property properties all the custom properties you are searching for
 *
 * @see Quack
 */
class Propfind internal constructor(val folder: String, vararg properties: Property) : Quack(Mallard.instance.url + "/" + folder, "PROPFIND") {

    init {
        headers.put(DEPTH, "1")
        headers.put("Content-Type", "text/xml")
    }

    /**
     * Set your search depth. By default it is 1.
     */
    fun setSearchDepth(depth: Int, root: Boolean = false) {
        headers.put(DEPTH, "$depth" + if (!root) ",noroot" else "")
    }

    /**
     * Build a request body with all of your custom properties
     *
     * @return String of request body
     */
    override fun getRequestBody(): String {
        val sb = StringBuilder()
        sb.append("<d:propfind xmlns:d=\"DAV:\"")
        properties.forEach {
            sb.append(" xmlns:${it.namespace[0]}=\"${it.namespace}\"")
        }
        sb.append(">\n")
        sb.append("   <d:prop>\n")
        sb.append("      <d:href/>\n")
        properties.forEach {
            sb.append(String.format("      <${it.namespace[0]}:${it.name}/>\n"))
        }
        sb.append("   </d:prop>\n")
        sb.append("</d:propfind>")
        return sb.toString()
    }

    /**
     * Returns a string of your content type (text/plain, etc...)
     */
    override fun getContentType(): String {
        return getRequestBody()
    }

    val properties: ArrayList<Property> = ArrayList(properties.asList())

    companion object {
        @JvmStatic private val DEPTH = "DEPTH"
    }
}

/**
 * [PropfindIncubator] is how you build your Propfind.
 *
 * @property mallard [Mallard] instance from your app
 * @property folder String of the folder you want to query
 */
class PropfindIncubator(val mallard: Mallard, val folder: String) {

    val properties = ArrayList<Property>()

    /**
     * Add a custom [Property] to search for
     *
     * @param prop Property you are adding
     * @return an instance of your [PropfindIncubator]
     */
    fun addCustomProperty(prop: Property) : PropfindIncubator {
        properties.add(prop)
        return this
    }

    /**
     * Hatch your incubator to bring your [Propfind] [Quack] to life
     *
     * @return an instance of [Propfind]
     */
    fun hatch() : Propfind {
        return Propfind(folder, *properties.toTypedArray())
    }
}
