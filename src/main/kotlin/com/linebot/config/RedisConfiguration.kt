package com.linebot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfiguration {
    @Bean
    fun <T> redisTemplate(connectionFactoryArg: RedisConnectionFactory, clazz: Class<T>): RedisTemplate<String, T> {
        return RedisTemplate<String, T>()
            .apply {
                connectionFactory = connectionFactoryArg
                keySerializer = StringRedisSerializer()
                valueSerializer = Jackson2JsonRedisSerializer(clazz)
                hashKeySerializer = this.keySerializer
                hashValueSerializer = this.valueSerializer
            }
    }
}
