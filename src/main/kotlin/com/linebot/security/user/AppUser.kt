package com.linebot.security.user

import com.linebot.entity.BotUser

data class AppUser(
    val user: BotUser,
    val accessToken: String,
    val appUserId: String
)
