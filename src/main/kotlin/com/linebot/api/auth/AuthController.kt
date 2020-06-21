package com.linebot.api.auth

import com.linebot.api.response.ValueResponse
import com.linebot.service.line.LineService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/line"])
class AuthController(
    private val lineService: LineService
) {
    companion object {
        const val JWT = "X_LIFF_JWT"
        const val ACCESS_TOKEN = "X_LIFF_ACCESS_TOKEN"
    }
    val log: Logger = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/token")
    fun auth(
        @RequestHeader(JWT) jwt: String,
        @RequestHeader(ACCESS_TOKEN) accessToken: String,
        @RequestHeader("token") token: String,
        @RequestHeader("nonce") nonce: String
    ): ValueResponse<String> {
        return ValueResponse(lineService.auth(jwt, accessToken))
    }
}
