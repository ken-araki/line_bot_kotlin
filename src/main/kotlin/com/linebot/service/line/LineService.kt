package com.linebot.service.line

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.linebot.client.line.LineApiClient
import com.linebot.config.LineConfig
import com.linebot.entity.BotUser
import com.linebot.service.user.BotUserService
import java.util.Date
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LineService(
    private val lineApiClient: LineApiClient,
    private val botUserService: BotUserService,
    private val lineConfig: LineConfig
) {
    val log: Logger = LoggerFactory.getLogger(LineService::class.java)
    fun auth(code: String, redirectUri: String): String {
        log.info("LineService auth. parameters code:{}, redirectUri:{}", code, redirectUri)
        val response = lineApiClient.auth(code, redirectUri)
        log.info("LineService auth. lineApiClient.auth response: {}", response)
        val exp = response.expiresIn
        val accessToken = response.accessToken
        val decodedIdToken = JWT.decode(response.idToken)
        log.info("LineService auth. decodedIdToken: {}, exp: {}, accessToken:{}", decodedIdToken, exp, accessToken)
        val mid = decodedIdToken.getClaim("sub").asString()
        val user = botUserService.getOrCreate(mid)
        return encodeJwt(user, accessToken, exp)
            .also { log.info("token is {}", this) }
    }

    fun encodeJwt(user: BotUser, accessToken: String, expierd: Long): String {
        val algorithm = Algorithm.HMAC256(lineConfig.channelSecret)
        return JWT.create()
            .withIssuer("auth0")
            .withClaim("appUserId", user.appUserId)
            .withClaim("accessToken", accessToken)
            .withExpiresAt(Date(expierd - (60 * 1000)))
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
}
