package com.juangomez.remote.models

import com.google.gson.annotations.SerializedName
import okhttp3.HttpUrl

data class RemotePaginationInfo(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
) {

    companion object {
        private const val PAGE_QUERY_PARAMETER_NAME = "page"
    }

    fun getNextPage() = HttpUrl.parse(next ?: "")?.queryParameter(PAGE_QUERY_PARAMETER_NAME)?.toInt()
}