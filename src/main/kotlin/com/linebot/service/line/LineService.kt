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
        val key = RandomUtils.UUIDString()
        val nonce = RandomUtils.UUIDString()
        nonceCacheService.set(key, nonce)
        return mapOf(
            "nonce" to nonce,
            "token" to encodeNonceJwt(key, nonce)
        )
    }

    fun auth(idToken: String, accessToken: String, onetimeJwt: String, nonce: String): String {
        log.info(
            "LineService auth. idToken: {}, accessToken: {}, onetimeJwt: {}, nonce: {}",
            idToken,
            accessToken,
            onetimeJwt,
            nonce
        )
        if (verityNonce(onetimeJwt, nonce)) {
            val decodedIdToken = lineApiClient.verify(idToken)
            log.info("LineService auth. decodedIdToken: {}", decodedIdToken)
            val user = botUserService.getOrCreate(decodedIdToken.sub)
            return encodeAuthJwt(user, accessToken)
                .also { log.info("token is {}", it) }
        } else throw AuthException("nonceが不正です")
    }

    fun encodeAuthJwt(user: BotUser, accessToken: String): String {
        val algorithm = Algorithm.HMAC256(lineAuthConfig.channelSecret)
        return JWT.create()
            .withIssuer("auth0")
            .withClaim("appUserId", user.appUserId)
            .withClaim("accessToken", accessToken)
            .withExpiresAt(getExpireAt())
            .sign(algorithm)
    }

    fun encodeNonceJwt(id: String, nonce: String): String {
        val algorithm = Algorithm.HMAC256(lineAuthConfig.channelSecret)
        return JWT.create()
            .withIssuer("auth0")
            .withClaim("id", id)
            .withClaim("nonce", nonce)
            .withExpiresAt(getExpireAt())
            .sign(algorithm)
    }

    fun verityNonce(jwt: String, nonce: String): Boolean {
        val algorithm = Algorithm.HMAC256(lineAuthConfig.channelSecret)
        val verifier = JWT.require(algorithm)
            .withIssuer("auth0")
            .build()
        val decodedJwt = verifier.verify(jwt)
        val id = decodedJwt.getClaim("id").asString()
        val result = nonceCacheService.get(id) == nonce
        // どちらにせよ削除する
        nonceCacheService.delete(id)
        return result
    }

    private fun getExpireAt(): Date {
        val calendar = Calendar.getInstance().apply {
            this.time = Date()
            this.add(Calendar.HOUR, 1)
        }
        return calendar.time
    }
}
