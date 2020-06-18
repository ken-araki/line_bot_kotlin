package com.linebot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app.line.auth")
data class LineAuthConfig(
    var channelId: String? = null,
    var channelSecret: String? = null
)
