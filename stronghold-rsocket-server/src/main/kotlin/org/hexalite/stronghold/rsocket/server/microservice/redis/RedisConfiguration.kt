package org.hexalite.stronghold.rsocket.server.microservice.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisSocketConfiguration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import java.time.Duration

@Configuration
class RedisConfiguration(
    @Value("\${redis.prefer-unix-domain-socket}") val preferUnixDomainSocket: Boolean,
    @Value("\${redis.use-ssl}") val useSsl: Boolean,
    @Value("\${redis.command-timeout}") val commandTimeout: Long,
    @Value("\${redis.host}") val host: String,
    @Value("\${redis.port}") val port: Int,
    @Value("\${redis.username}") val username: String,
    @Value("\${redis.password}") val password: String,
    @Value("\${redis.database-index}") val databaseIndex: Int
) {
    @Bean
    fun connection(): LettuceConnectionFactory = LettuceConnectionFactory(
        if (preferUnixDomainSocket && !System.getProperty("os.name").startsWith("Windows"))
            RedisSocketConfiguration("/var/run/redis.sock")
        else
            RedisStandaloneConfiguration(host, port).apply {
                val pass = this@RedisConfiguration.password
                val user = this@RedisConfiguration.username
                if (user.isNotBlank()) {
                    username = user
                }
                if (pass.isNotBlank()) {
                    password = RedisPassword.of(pass)
                }
                database = databaseIndex
            },
        LettuceClientConfiguration.builder()
            .also {
                if (useSsl)
                    it.useSsl()
            }
            .commandTimeout(Duration.ofSeconds(commandTimeout))
            .shutdownTimeout(Duration.ZERO)
            .build()
    )

    @Bean
    fun template(factory: LettuceConnectionFactory) = ReactiveStringRedisTemplate(factory)
}