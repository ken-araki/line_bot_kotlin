package com.linebot.service.cache

import java.util.concurrent.TimeUnit
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class RedisCacheService<T>(
    private val redisTemplate: RedisTemplate<String, T>
) {

    fun set(key: String, value: T, expire: Long = 1, time: TimeUnit = TimeUnit.HOURS) {
        redisTemplate.opsForValue().set(key, value)
        redisTemplate.expire(key, expire, time)
    }

    fun get(key: String): T? {
        return if (redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().get(key)
        } else null
    }

    fun delete(key: String): Boolean {
        return redisTemplate.delete(key)
    }
}
