package org.hexalite.stronghold.rsocket.server.configuration

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import org.hexalite.stronghold.rsocket.server.io.KotlinxSerializationCborDecoder
import org.hexalite.stronghold.rsocket.server.io.KotlinxSerializationCborEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.web.util.pattern.PathPatternRouteMatcher

@Configuration
class RSocketConfiguration {
    @OptIn(ExperimentalSerializationApi::class)
    @Bean
    fun cbor() = RSocketStrategies.builder()
        .also { strat ->
            val cbor = Cbor {
                ignoreUnknownKeys = true
            }
            strat.encoders { it.add(KotlinxSerializationCborEncoder(cbor)) }
                .decoders { it.add(KotlinxSerializationCborDecoder(cbor)) }
        }
        .routeMatcher(PathPatternRouteMatcher())
        .build()
}