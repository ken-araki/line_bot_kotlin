package com.linebot.api.response

data class ValueResponse<V>(
    val data: V,
    val isSuccess: Boolean = true
)
