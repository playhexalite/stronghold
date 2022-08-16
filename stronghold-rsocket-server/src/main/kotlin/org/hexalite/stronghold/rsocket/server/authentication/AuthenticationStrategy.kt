package org.hexalite.stronghold.rsocket.server.authentication

/**
 * An agnostic type representing possible implementations of authentication strategies.
 *
 * ## How does it work?
 *
 * By providing a form data class as the [F] generic type, you can provide a form validation
 * implementation to check whether it passes on the authentication step, and if it is valid,
 * you can tell it back, and it will be returned as a JWT Bearer token.
 *
 * You also need to provide your implementation to Spring Boot. The way it is implemented by
 * default here on `stronghold` is by detecting a value at the environment variables to make
 * sure what kind of strategy will be enabled for this session.
 *
 * ### How can I provide my implementation to Spring Boot?
 *
 * The way Spring Boot + RSocket handles authentication `@Bean`'s is pretty straightforward
 * and only require you to override their RSocket security class through a function marked
 * with that annotation. For example, to allow all requests that provide authentication code
 * and are powered by [the renewing key strategy][RenewingKeyStrategy], you could use:
 * ```kotlin
 * @Bean
 * fun interceptor(security: RSocketSecurity) = security
 *  .authorizePayload { authorize ->
 *      authorize
 *          .anyRequest()
 *          .authenticated()
 *          .anyExchange()
 *          .permitAll()
 *  }
 *  .apply {
 *      val strategy = RenewingKeyStrategy()
 *      .addPayloadInterceptor { exchange, chain ->
 *
 *      }
 *  }
 *  .build()
 * ```
 *
 * @author FromSyntax
 */
interface AuthenticationStrategy<F, FX: AuthenticationFactory<F>> {
    val factory: FX

    suspend fun passes(form: F): Boolean
}