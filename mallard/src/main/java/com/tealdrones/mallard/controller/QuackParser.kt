package com.tealdrones.mallard.controller

import com.tealdrones.mallard.model.Duck
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.parser.Parser

/**
 * Created by kodyvanry on 8/7/17
 * <p>
 *     A QuackParser parses the xml returned from WebDav
 * </p>
 *
 * @constructor Creates a [QuackParser] with a provided xml String
 */
class QuackParser(val xml: String) {

    val xmlDoc: Document = Jsoup.parse(xml, "", Parser.xmlParser())
    val ducks = ArrayList<Duck>()

    /**
     * Parses the xml into a bunch of ducks with ducklings
     *
     * @return an [ArrayList] of [Duck] from the xml (We also give birth to ducklings :D)
     */
    fun parse() : ArrayList<Duck> {
        xmlDoc.select(RESPONSE_NAME).forEach {
            ducks.add(parseResponse(it))
        }
        return ducks
    }


    /**
     * Recursive function that takes the xml and converts it into a bunch of ducks with ducklings
     *
     * @return a cute little Duck (she might even have children!!)
     */
    private fun parseResponse(element: Element) : Duck {
        if (element.isBlock) {
            val children = ArrayList<Duck>()
            element.allElements.forEach { child ->
                children.add(parseResponse(child))
            }
            return Duck(element.id(), element.text(), *children.toTypedArray())
        }
        return Duck(element.id(), element.text())
    }


    companion object {
        @JvmStatic private val RESPONSE_NAME = "D|response"
    }
}