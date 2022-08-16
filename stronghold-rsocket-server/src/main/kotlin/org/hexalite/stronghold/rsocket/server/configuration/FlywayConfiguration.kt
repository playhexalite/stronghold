package org.hexalite.stronghold.rsocket.server.configuration

import org.flywaydb.core.Flyway
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlywayConfiguration(
    @Value("\${db.user}") val user: String,
    @Value("\${db.password}") val password: String,
    @Value("\${db.url}") val url: String
) {
    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        val config = Flyway.configure().dataSource("jdbc:$url", user, password)
        return Flyway(config)
    }
}