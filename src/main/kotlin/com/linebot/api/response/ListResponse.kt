package com.linebot.api.response

data class ListResponse<T>(
    val data: List<T> = emptyList()
) {
    fun getCount() = data.size
}
