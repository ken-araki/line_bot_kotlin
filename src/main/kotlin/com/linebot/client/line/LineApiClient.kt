package com.linebot.client.line

import com.fasterxml.jackson.databind.ObjectMapper
import com.linebot.api.auth.AuthController
import com.linebot.client.line.response.LineVerityResponse
import com.linebot.config.LineAuthConfig
import com.mashape.unirest.http.Unirest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class LineApiClient(
    private val lineAuthConfig: LineAuthConfig,
    private val objectMapper: ObjectMapper
) {
    companion object {
        const val VERITY_URL = "https://api.line.me/oauth2/v2.1/verify"
    }

    val log: Logger = LoggerFactory.getLogger(AuthController::class.java)

    // https://developers.line.biz/ja/reference/social-api/#verify-id-token
    fun verify(idToken: String): LineVerityResponse {
        val request = Unirest.post(VERITY_URL)
            .header("Content-Type", "application/x-www-form-urlencoded")
            .field("id_token", idToken)
            .field("client_id", lineAuthConfig.channelId)
        log.info("request: {}", request)
        val response = request.asString()
        log.info("LineApiClient auth response: {}, body: {}", response, response.body)
        return objectMapper.readValue(response.body, LineVerityResponse::class.java)
    }
}
