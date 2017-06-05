package com.natodriod.kotlin.rssreader.model
/**
 * Created by natiqmustafa on 05.06.2017.
 */

data class Item(val title: String, val pubDate: String, val link: String, val guid: String,
                val author: String, val thumbnail: String, val description: String, val content: String,
                val enclosure: Object, val categories : List<String>)

