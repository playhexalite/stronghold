package org.hexalite.stronghold.rsocket.server.configuration

import io.r2dbc.spi.ConnectionFactory
import org.hexalite.stronghold.rsocket.server.converter.ClanRoleConverter
import org.hexalite.stronghold.rsocket.server.converter.InstantConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.PostgresDialect
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer

@Configuration
class DatabaseConfiguration {
    @Bean
    fun initializer(factory: ConnectionFactory): ConnectionFactoryInitializer {
//        val populator = CompositeDatabasePopulator().apply {
//            val migrations = ClassPathResource("db/migration")
//            migrations.file.list().orEmpty().sorted().forEach {
//                val resource = ClassPathResource("db/migration/$it")
//                addPopulators(ResourceDatabasePopulator(resource))
//            }
//        }
        return ConnectionFactoryInitializer().apply {
            setConnectionFactory(factory)
            //setDatabasePopulator(populator)
        }
    }

    @Bean
    fun converters(): R2dbcCustomConversions {
        val converters = buildList {
            add(InstantConverter)
            add(InstantConverter.Reversed)
            add(ClanRoleConverter)
            add(ClanRoleConverter.Reversed)
        }
        return R2dbcCustomConversions.of(PostgresDialect.INSTANCE, converters)
    }
}