package com.linebot.service.line

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.linebot.client.line.LineApiClient
import com.linebot.config.LineAuthConfig
import com.linebot.entity.BotUser
import com.linebot.service.cache.NonceCacheService
import com.linebot.service.user.BotUserService
import com.linebot.util.RandomUtils
import java.util.Calendar
import java.util.Date
import javax.security.auth.message.AuthException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LineService(
    private val lineApiClient: LineApiClient,
    private val nonceCacheService: NonceCacheService,
    private val botUserService: BotUserService,
    private val lineAuthConfig: LineAuthConfig
) {
    val log: Logger = LoggerFactory.getLogger(LineService::class.java)

    fun nonce(): Map<String, String> {
        val nonce = RandomUtils.UUIDString()
        val nonceId = RandomUtils.UUIDString()
        nonceCacheService.set(nonceId, nonce)
        return mapOf(
            "nonce" to nonce,
            "nonceId" to nonceId
        )
    }

    fun auth(idToken: String, accessToken: String, nonceId: String): String {
        log.info("LineService auth. idToken: {}, accessToken: {}, nonceId: {}", idToken, accessToken, nonceId)
        val nonce = nonceCacheService.get(nonceId) ?: throw AuthException("nonce idが不正です")
        val decodedIdToken = lineApiClient.verify(idToken, nonce)
        log.info("LineService auth. decodedIdToken: {}", decodedIdToken)
        nonceCacheService.delete(nonceId)
        val user = botUserService.getOrCreate(decodedIdToken.sub)
        return encodeJwt(user, accessToken)
            .also { log.info("token is {}", it) }
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
    private fun getExpireAt(): Date {
        val calendar = Calendar.getInstance().apply {
            this.time = Date()
            this.add(Calendar.HOUR, 1)
        }
        return calendar.time
    }
}
