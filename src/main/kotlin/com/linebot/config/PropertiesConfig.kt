package com.linebot.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "app")
data class PropertiesConfig(
    var id: String? = null,
    var ownHost: String? = null,
    var ownPort: String? = null,
    var token: String? = null,
    var allowedOriginUri: String? = null
)
