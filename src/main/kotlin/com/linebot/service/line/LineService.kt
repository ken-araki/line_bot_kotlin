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
import javax.security.auth.message.AuthException

@Service
class LineService(
    private val lineApiClient: LineApiClient,
    private val botUserService: BotUserService,
    private val lineAuthConfig: LineAuthConfig
) {
    val log: Logger = LoggerFactory.getLogger(LineService::class.java)

    fun auth(idToken: String, accessToken: String): String {
        log.info("LineService auth. idToken: {}, accessToken: {}", idToken, accessToken)
        if (verityAccessToken(accessToken)) {
            val decodedIdToken = lineApiClient.verifyIdToken(idToken)
            val user = botUserService.getOrCreate(decodedIdToken.sub)
            return encodeAuthJwt(user, accessToken)
                .also { log.info("token is {}", it) }
        } else throw AuthException("invalid access_token.")
    }

    fun verityAccessToken(accessToken: String): Boolean {
        val response = lineApiClient.verifyAccessToken(accessToken)
        log.info("verity access_token. 1 hour < expire: {} and channel_id is match", response.expiresIn)
        return (60 * 60) < response.expiresIn && lineAuthConfig.channelId == response.clientId
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

    fun decodeAuthJwt(jwt: String): String {
        val algorithm = Algorithm.HMAC256(lineAuthConfig.channelSecret)
        val verifier = JWT.require(algorithm)
            .withIssuer("auth0")
            .build()
        val decodedJwt = verifier.verify(jwt)
        return decodedJwt.getClaim("appUserId").asString()
    }

    private fun getExpireAt(): Date {
        val calendar = Calendar.getInstance().apply {
            this.time = Date()
            this.add(Calendar.HOUR, 1)
        }
        return calendar.time
    }
}
