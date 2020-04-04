package com.linebot.client.self

import com.linebot.client.WebClient
import com.linebot.config.PropertiesConfig
import com.linebot.model.response.RestApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SelfClient(
    private val webClient: WebClient,
    private val config: PropertiesConfig
) {
    val log: Logger = LoggerFactory.getLogger(SelfClient::class.java)

    fun keepAlive(): RestApiResponse {
        val url = "${config.ownHost}:${config.ownPort}/self/keep"
        return webClient[url, RestApiResponse::class.java] ?: RestApiResponse("error")
    }
}
