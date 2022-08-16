package org.hexalite.stronghold.rsocket.server.configuration

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.asFlux
import org.hexalite.stronghold.rsocket.server.authentication.RenewingKeyStrategy
import org.hexalite.stronghold.rsocket.server.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.messaging.rsocket.RSocketStrategies
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.rsocket.EnableRSocketSecurity
import org.springframework.security.config.annotation.rsocket.RSocketSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.messaging.handler.invocation.reactive.AuthenticationPrincipalArgumentResolver
import org.springframework.security.messaging.handler.invocation.reactive.CurrentSecurityContextArgumentResolver
import org.springframework.security.oauth2.jose.jws.MacAlgorithm
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter
import org.springframework.security.rsocket.core.PayloadSocketAcceptorInterceptor
import org.springframework.web.util.pattern.PathPatternRouteMatcher
import reactor.kotlin.core.publisher.toMono
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableRSocketSecurity
@EnableReactiveMethodSecurity
class SecurityConfiguration(
    @Value("\${security.auth.strategy}") strategy: String,
    @Value("\${security.jwt.secret-key}") private val secretKey: String,
    redis: ReactiveStringRedisTemplate,
    users: UserService
) {


    val strategy = when (strategy.lowercase()) {
        "renewing" -> RenewingKeyStrategy(users, redis, secretKey)
        else -> throw IllegalStateException("Invalid auth strategy provided: '$strategy'.")
    }

    @Bean
    fun messageHandler(strategies: RSocketStrategies) = RSocketMessageHandler().apply {
        routeMatcher = PathPatternRouteMatcher()
        argumentResolverConfigurer.addCustomResolver(AuthenticationPrincipalArgumentResolver())
        argumentResolverConfigurer.addCustomResolver(CurrentSecurityContextArgumentResolver())
        rSocketStrategies = strategies
    }

    @Bean
    fun decoder(): ReactiveJwtDecoder = NimbusReactiveJwtDecoder.withSecretKey(
        SecretKeySpec(
            secretKey.encodeToByteArray(),
            Mac.getInstance("HmacSHA256").algorithm
        )
    ).macAlgorithm(MacAlgorithm.HS256).build()


    @OptIn(FlowPreview::class)
    @Suppress("ReactiveStreamsUnusedPublisher")
    @Bean
    fun authentication(decoder: ReactiveJwtDecoder): ReactiveAuthenticationManager {
        val converter = ReactiveJwtAuthenticationConverterAdapter(JwtAuthenticationConverter())
        return ReactiveAuthenticationManager { auth ->
            flowOf(auth)
                .filterIsInstance<BearerTokenAuthenticationToken>()
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMapConcat { decoder.decode(it).asFlow() }
                .flatMapConcat { converter.convert(it)!!.asFlow() }
                .filter {
                    strategy.passes(strategy.factory.create(it as JwtAuthenticationToken))
                }
                .asFlux()
                .toMono()
                .cast(Authentication::class.java)
        }
    }

    @Bean
    fun interceptor(
        manager: ReactiveAuthenticationManager,
        security: RSocketSecurity
    ): PayloadSocketAcceptorInterceptor = security
        .authorizePayload { authorize ->
            authorize
                .anyRequest()
                .authenticated()
                .anyExchange()
                .permitAll()
        }
        .jwt { spec ->
            try {
                spec.authenticationManager(manager)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
        .build()
}