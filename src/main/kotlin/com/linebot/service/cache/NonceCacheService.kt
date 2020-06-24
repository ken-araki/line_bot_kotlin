package com.linebot.service.cache

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class NonceCacheService(
    @Qualifier("nonceRedisTemplate")
    override val redisTemplate: RedisTemplate<String, String>
) : RedisCacheService<String>
