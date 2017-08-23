package com.tealdrones.mallard.model

/**
 * Created by kodyvanry on 7/26/17
 *
 * Property is something you are searching for in WebDav
 *
 * @property namespace the namespace of your custom property
 * @property name of your property (href, name, artist, etc...)
 */
class Property(val namespace: String, val name: String)