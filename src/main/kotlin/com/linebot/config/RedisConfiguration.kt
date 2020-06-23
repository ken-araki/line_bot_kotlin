package com.linebot.config

import com.linebot.model.UserStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfiguration {
    @Bean
    fun userStatusRedisTemplate(connectionFactoryArg: RedisConnectionFactory): RedisTemplate<String, UserStatus> {
        return buildRedisTemplate(connectionFactoryArg, UserStatus::class.java)
    }

    @Bean
    fun nonceRedisTemplate(connectionFactoryArg: RedisConnectionFactory): RedisTemplate<String, String> {
        return buildRedisTemplate(connectionFactoryArg, String::class.java)
    }

    private fun <T> buildRedisTemplate(
        connectionFactoryArg: RedisConnectionFactory,
        clazz: Class<T>
    ): RedisTemplate<String, T> {
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
