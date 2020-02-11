package com.linebot.model.common

import org.springframework.util.MultiValueMap
import java.util.*

interface RequestParameter {
    fun convert(): MultiValueMap<String, String>

    fun add(param: MultiValueMap<String, String>, keyName: String, value: Any?) {
        value?.let {
            param.add(keyName, Objects.toString(it))
        }
    }

    fun required(keyName: String, value: Any?) {
        if (value == null) {
            throw IllegalArgumentException("$keyName is required.")
        }
    }
}
