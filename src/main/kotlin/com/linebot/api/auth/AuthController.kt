package com.linebot.api.auth

import com.linebot.api.response.ValueResponse
import com.linebot.service.line.LineService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// https://developers.line.biz/ja/docs/line-login/secure-login-process/#openidを使用して新規ユーザーを登録する
@RestController
@RequestMapping(path = ["/line"])
class AuthController(
    private val lineService: LineService
) {
    companion object {
        const val JWT = "X_LIFF_JWT"
        const val ACCESS_TOKEN = "X_LIFF_ACCESS_TOKEN"
        const val NONCE_ID = "X_LIFF_NONCE_ID"
    }

    val log: Logger = LoggerFactory.getLogger(AuthController::class.java)
    @PostMapping("/nonce")
    @CrossOrigin
    fun nonce(@RequestHeader("token") token: String): ValueResponse<Map<String, String>> {
        return ValueResponse(lineService.nonce())
    }

    @PostMapping("/token")
    @CrossOrigin
    fun auth(
        @RequestHeader(JWT) jwt: String,
        @RequestHeader(ACCESS_TOKEN) accessToken: String,
        @RequestHeader(NONCE_ID) nonceId: String,
        @RequestHeader("token") token: String
    ): ValueResponse<String> {
        return ValueResponse(lineService.auth(jwt, accessToken, nonceId))
    }
}
