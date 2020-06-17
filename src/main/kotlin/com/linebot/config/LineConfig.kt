package com.linebot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "line.bot")
data class LineConfig(
    var channelId: String? = null,
    var channelSecret: String? = null
)
