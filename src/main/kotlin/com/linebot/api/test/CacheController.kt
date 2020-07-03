package com.linebot.api.test

import com.linebot.model.UserStatus
import com.linebot.service.cache.UserStatusCacheService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/test/cache"])
@Profile("local")
class CacheController(
    private val service: UserStatusCacheService
) {
    val log: Logger = LoggerFactory.getLogger(CacheController::class.java)

    @GetMapping(path = ["/set"])
    operator fun set(
        @RequestParam(name = "userId", required = false, defaultValue = "1") userId: String,
        @RequestParam(name = "nextAction", required = false, defaultValue = "nextAction") nextAction: String
    ): String {
        val userStatus = UserStatus(userId, nextAction)
        service.set(userId, userStatus)
        return "Succes"
    }

    @GetMapping(path = ["/get"])
    operator fun get(
        @RequestParam(name = "id", required = false, defaultValue = "1") userId: String
    ): UserStatus? {
        return service.get(userId)
    }
}
