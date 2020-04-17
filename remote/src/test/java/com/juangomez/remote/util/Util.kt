package com.juangomez.remote.util

import com.google.gson.GsonBuilder

enum class JSONFile(val jsonName: String) {
    GET_EPISODES_SINGLE_PAGE_RESPONSE("get_episodes_single_page_response.json"),
    GET_EPISODES_MULTIPLE_PAGES_FIRST_RESPONSE("get_episodes_multiple_pages_first_response.json"),
    GET_EPISODES_MULTIPLE_PAGES_SECOND_RESPONSE("get_episodes_multiple_pages_second_response.json"),
    GET_EPISODE_RESPONSE("get_episode_response.json"),
}

fun JSONFile.string(): String = ClassLoader.getSystemResource(jsonName).readText()
fun <R> pojo(jsonString: String, pojoClass: Class<R>) =
    GsonBuilder().create().fromJson(jsonString, pojoClass)
