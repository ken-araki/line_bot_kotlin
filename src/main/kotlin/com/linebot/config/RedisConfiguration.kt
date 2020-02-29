package com.linebot.config

import com.linebot.model.UserStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfiguration {
    @Bean
    fun redisTemplate(connectionFactoryArg: RedisConnectionFactory): RedisTemplate<String, UserStatus> {
        return RedisTemplate<String, UserStatus>()
                .apply {
                    connectionFactory = connectionFactoryArg
                    keySerializer = StringRedisSerializer()
                    valueSerializer = GenericJackson2JsonRedisSerializer("UserStatus")
                    hashKeySerializer = this.keySerializer
                    hashValueSerializer = this.valueSerializer
                }
    }
}
