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

    // LINE Developers #IDトークンを送信してユーザー情報を取得する
    // https://developers.line.biz/ja/docs/liff/using-user-profile/#%E3%83%A6%E3%83%BC%E3%82%B6%E3%83%BC%E6%83%85%E5%A0%B1%E3%82%92%E3%82%B5%E3%83%BC%E3%83%90%E3%83%BC%E3%81%A7%E4%BD%BF%E7%94%A8%E3%81%99%E3%82%8B
    @PostMapping("/token")
    @CrossOrigin
    fun auth(
        @RequestHeader(JWT) jwt: String,
        @RequestHeader(ACCESS_TOKEN) accessToken: String,
        @RequestHeader("token") token: String
    ): ValueResponse<String> {
        return ValueResponse(lineService.auth(jwt, accessToken))
    }
}
