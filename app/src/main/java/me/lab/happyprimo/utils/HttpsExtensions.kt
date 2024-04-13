package me.lab.happyprimo.utils

import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

fun xmlToJson(xmlString: String): JSONObject {
    val json = JSONObject()
    val factory = XmlPullParserFactory.newInstance()
    val parser = factory.newPullParser()
    parser.setInput(StringReader(xmlString))

    var currentArray: JSONArray? = null
    var currentElement: JSONObject? = null
    var tagName: String? = null
    var isCData = false
    var textContent: String? = null

    var eventType = parser.eventType
    while (eventType != XmlPullParser.END_DOCUMENT) {
        when (eventType) {
            XmlPullParser.START_TAG -> {
                tagName = parser.name
                if (tagName == "item") {
                    if (currentArray == null) {
                        currentArray = JSONArray()
                        json.put(tagName, currentArray)
                    }
                    currentElement = JSONObject()
                    currentArray.put(currentElement)
                } else {
                    val tagList = currentElement?.opt(tagName)
                    if (tagList == null) {
                        if (currentElement != null) {
                            if (tagName == "category") {
                                val categoryList = JSONArray()
                                categoryList.put(parser.nextText())
                                currentElement.put(tagName, categoryList)
                            } else {
                                val newObject = JSONObject()
                                currentElement.put(tagName, newObject)
                                currentElement = newObject
                            }
                        } else {
                            currentElement = JSONObject()
                            json.put(tagName, currentElement)
                        }
                    } else {
                        if (tagList is JSONArray) {
                            tagList.put(parser.nextText())
                        } else {
                            // If it's not an array, convert it to an array and add the existing value
                            val newArray = JSONArray()
                            if (tagList is String) {
                                newArray.put(tagList)
                            } else {
                                // If it's not a string or an array, skip for now
                            }
                            newArray.put(parser.nextText())
                            currentElement?.put(tagName, newArray)
                        }
                    }
                }
            }
            XmlPullParser.TEXT -> {
                val text = parser.text.trim()
                if (text.isNotEmpty()) {
                    if (isCData) {
                        textContent = text
                    } else {
                        currentElement?.put(tagName + "_data", text)
                    }
                }
            }
            XmlPullParser.END_TAG -> {
                tagName = parser.name
                if (tagName == "item") {
                    currentElement = null
                }
            }
        }
        eventType = parser.next()
    }

    return json
}


