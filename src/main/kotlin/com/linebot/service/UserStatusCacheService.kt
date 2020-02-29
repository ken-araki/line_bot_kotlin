package com.linebot.service

import com.linebot.model.UserStatus
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class UserStatusCacheService(
        private val redisTemplate: RedisTemplate<String, UserStatus>
) {

    operator fun set(userId: String, userStatus: UserStatus) {
        redisTemplate.opsForValue().set(userId, userStatus)
        redisTemplate.expire(userId, 30, TimeUnit.MINUTES)
    }

    operator fun get(userId: String): UserStatus? {
        return if (redisTemplate.hasKey(userId)) {
            redisTemplate.opsForValue().get(userId)
        } else null
    }

    fun delete(userId: String): Boolean {
        return redisTemplate.delete(userId)
    }
}
