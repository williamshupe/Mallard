package com.teal.mallard.model

/**
 * Created by kodyvanry on 8/16/17
 * <p>
 * A duck is an element in WebDav.
 * Ducks can have ducklings and have an ID and Text associated with them.
 * </p>
 *
 * @constructor Creates a duck, with any ducklings provided
 */
class Duck internal constructor(val id: String, val text: String?, vararg ducklings: Duck) {

    val ducklings: ArrayList<Duck> = ArrayList(ducklings.toList())
    val hasChildren: Boolean
        get() = ducklings.size <= 0

    /**
     * Recursively searches through this duck (and ducklings) and returns duck if found in
     * the family tree
     *
     * @param id id of [Duck] you are searching for (namespace|id)
     * @return [Duck] if found
     */
    fun getValue(id: String) : Duck? {
        if (this.id == id)
            return this
        if (hasChildren) {
            ducklings.forEach { duckling ->
                duckling.getValue(id)?.let {
                    return it
                }
            }
        }
        return null
    }


    /**
     * Recursively searches through this duck (and ducklings) and returns duck if found in
     * the family tree
     *
     * @param idContains search parameters
     * @return [Duck] if found
     */
    fun findValue(idContains: String) : Duck? {
        if (this.id.contains(idContains))
            return this
        if (hasChildren) {
            ducklings.forEach { duckling ->
                duckling.findValue(idContains)?.let {
                    return it
                }
            }
        }
        return null
    }

}