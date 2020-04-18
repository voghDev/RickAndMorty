package com.juangomez.remote.util

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.juangomez.remote.models.RemoteCharacter
import java.lang.reflect.Type

enum class JSONFile(val jsonName: String) {
    GET_EPISODES_SINGLE_PAGE_RESPONSE("get_episodes_single_page_response.json"),
    GET_EPISODES_MULTIPLE_PAGES_FIRST_RESPONSE("get_episodes_multiple_pages_first_response.json"),
    GET_EPISODES_MULTIPLE_PAGES_SECOND_RESPONSE("get_episodes_multiple_pages_second_response.json"),
    GET_EPISODE_RESPONSE("get_episode_response.json"),
    GET_CHARACTERS_RESPONSE("get_characters_response.json"),
}

fun JSONFile.string(): String = ClassLoader.getSystemResource(jsonName).readText()

fun <R> pojo(jsonString: String, pojoClass: Class<R>): R =
    GsonBuilder().create().fromJson(jsonString, pojoClass)

fun <R> pojoList(jsonString: String, pojoClass: Class<R>): List<R> {
    val listType = TypeToken.getParameterized(ArrayList::class.java, pojoClass).type
    return GsonBuilder().create().fromJson(jsonString, listType)
}