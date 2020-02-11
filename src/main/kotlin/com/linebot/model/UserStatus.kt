package com.linebot.model

import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class UserStatus(
        var userId: String? = null,
        var nextAction: String? = null
) : Serializable
