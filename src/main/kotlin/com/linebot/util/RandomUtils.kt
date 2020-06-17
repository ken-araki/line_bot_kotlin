package com.linebot.util

import org.apache.commons.lang3.RandomStringUtils

object RandomUtils {
    val KEY = "AbCd!1234&"
    fun createRandomString(count: Int) = RandomStringUtils.random(count, KEY)
    fun createUserId(): String {
        return "A1-" + createRandomString(20)
    }
}
