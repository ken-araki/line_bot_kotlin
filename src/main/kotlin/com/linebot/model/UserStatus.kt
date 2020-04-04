package com.linebot.model

import java.io.Serializable
import org.springframework.stereotype.Component

@Component
class UserStatus(
    var userId: String? = null,
    var nextAction: String? = null
) : Serializable
