package com.linebot.client.line

import com.fasterxml.jackson.databind.ObjectMapper
import com.linebot.client.line.response.LineAuthResponse
import com.linebot.config.LineConfig
import com.linebot.controller.auth.AuthController
import com.mashape.unirest.http.Unirest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LineApiClient(
    private val lineConfig: LineConfig,
    private val objectMapper: ObjectMapper
) {
    val log: Logger = LoggerFactory.getLogger(AuthController::class.java)

    companion object {
        val URL = "https://api.line.me/oauth2/v2.1/token"
        val AUTH_GRANT_TYPE = "authorization_code"
    }

    fun auth(code: String, redirectUri: String): LineAuthResponse {
        val response = Unirest.post(URL)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .field("grant_type", AUTH_GRANT_TYPE)
            .field("code", code)
            .field("client_id", lineConfig.channelId)
            .field("client_secret", lineConfig.channelSecret)
            .field("redirect_uri", redirectUri)
            .asString()
        log.info("LineApiClient auth response: {}", response)
        return objectMapper.readValue(response.body, LineAuthResponse::class.java)
    }
}
