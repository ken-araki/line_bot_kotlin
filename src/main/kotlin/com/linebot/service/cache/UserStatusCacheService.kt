package com.linebot.service.cache

import com.linebot.model.UserStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class UserStatusCacheService(
    @Qualifier("userStatusRedisTemplate")
    override val redisTemplate: RedisTemplate<String, UserStatus>
) : RedisCacheService<UserStatus>
