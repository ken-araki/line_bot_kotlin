package com.linebot.client.line

import com.fasterxml.jackson.databind.ObjectMapper
import com.linebot.client.line.response.LineAuthResponse
import com.linebot.config.LineAuthConfig
import com.linebot.controller.auth.AuthController
import com.mashape.unirest.http.Unirest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LineApiClient(
    private val lineAuthConfig: LineAuthConfig,
    private val objectMapper: ObjectMapper
) {
    val log: Logger = LoggerFactory.getLogger(AuthController::class.java)

    companion object {
        val URL = "https://api.line.me/oauth2/v2.1/token"
        val AUTH_GRANT_TYPE = "authorization_code"
    }

    fun auth(code: String, redirectUri: String): LineAuthResponse {
        val request = Unirest.post(URL)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .field("grant_type", AUTH_GRANT_TYPE)
            .field("code", code)
            .field("client_id", lineAuthConfig.channelId)
            .field("client_secret", lineAuthConfig.channelSecret)
            .field("redirect_uri", redirectUri)
        log.info("request: {}", request)
        val response = request.asString()
        log.info("LineApiClient auth response: {}", response.body)
        return objectMapper.readValue(response.body, LineAuthResponse::class.java)
    }
}
