package org.hexalite.stronghold.rsocket.server.configuration

import org.flywaydb.core.Flyway
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class FlywayConfiguration(val environment: Environment) {
    @Bean(initMethod = "migrate")
    fun flyway(): Flyway {
        val url = "jdbc:${environment.getRequiredProperty("db.url")}"
        val user = environment.getRequiredProperty("db.user")
        val password = environment.getRequiredProperty("db.password")
        val config = Flyway.configure().dataSource(url, user, password)
        return Flyway(config)
    }
}