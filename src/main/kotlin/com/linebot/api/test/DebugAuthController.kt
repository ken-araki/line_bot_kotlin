package com.linebot.api.test

import com.linebot.api.response.ValueResponse
import com.linebot.repository.BotUserRepository
import com.linebot.service.line.LineService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/test/line"])
class DebugAuthController(
    private val lineService: LineService,
    private val botUserRepository: BotUserRepository
) {
    val log: Logger = LoggerFactory.getLogger(DebugAuthController::class.java)

    // 適当なjwt tokenを作成する
    @PostMapping("/token")
    @CrossOrigin
    fun auth(
        @RequestHeader("user_id") userId: String
    ): ValueResponse<String> {
        val user = botUserRepository.findByUserId(userId) ?: throw RuntimeException()
        return ValueResponse(lineService.encodeAuthJwt(user, "sample_access_token"))
    }
}
