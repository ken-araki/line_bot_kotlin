package com.linebot.service.line

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.linebot.client.line.LineApiClient
import com.linebot.config.LineAuthConfig
import com.linebot.entity.BotUser
import com.linebot.service.user.BotUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.Calendar
import java.util.Date

@Service
class LineService(
    private val lineApiClient: LineApiClient,
    private val botUserService: BotUserService,
    private val lineAuthConfig: LineAuthConfig
) {
    val log: Logger = LoggerFactory.getLogger(LineService::class.java)
    fun auth(jwt: String, accessToken: String): String {
        log.info("LineService auth. jwt: {}, accessToken: {}", jwt, accessToken)
        val decodedIdToken = JWT.decode(jwt)
        log.info("LineService auth. decodedIdToken: {}", decodedIdToken)
        val mid = decodedIdToken.getClaim("sub").asString()
        val user = botUserService.getOrCreate(mid)
        return encodeJwt(user, accessToken)
            .also { log.info("token is {}", this) }
    }

    fun encodeJwt(user: BotUser, accessToken: String): String {
        val algorithm = Algorithm.HMAC256(lineAuthConfig.channelSecret)
        return JWT.create()
            .withIssuer("auth0")
            .withClaim("appUserId", user.appUserId)
            .withClaim("accessToken", accessToken)
            .withExpiresAt(getExpireAt())
            .sign(algorithm)
    }
    // 現状使わないためコメントアウトする
    // fun decodeJwt(token: String): String {
    //     val algorithm = Algorithm.HMAC256("secret")
    //     val verifier = JWT.require(algorithm)
    //         .withIssuer("auth0")
    //         .build()
    //     val jwt = verifier.verify(token)
    //     return jwt.getClaim("appUserId").asString()
    // }
    fun getExpireAt(): Date {
        val calendar = Calendar.getInstance().apply {
            this.time = Date()
            this.add(Calendar.HOUR, 1)
        }
        return calendar.time
    }
}
