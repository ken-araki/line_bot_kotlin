package com.linebot.service.cache

import java.util.concurrent.TimeUnit
import org.springframework.data.redis.core.RedisTemplate

interface RedisCacheService<T> {
    val redisTemplate: RedisTemplate<String, T>

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
