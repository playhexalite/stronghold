package org.hexalite.stronghold.rsocket.server.microservice.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisSocketConfiguration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class RedisConfiguration(
    @Value("\${redis.prefer-unix-domain-socket}") val preferUnixDomainSocket: Boolean,
    @Value("\${redis.host}") val host: String,
    @Value("\${redis.port}") val port: Int,
) {
    @Bean
    fun connection() = LettuceConnectionFactory(
        if (preferUnixDomainSocket && !System.getProperty("os.name").startsWith("Windows"))
            RedisSocketConfiguration("/var/run/redis.sock")
        else
            RedisStandaloneConfiguration(host, port)
    )
}