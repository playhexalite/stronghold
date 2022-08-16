package org.hexalite.stronghold.rsocket.server.authentication

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

/**
 * A factory type for a possible form implementation that is used on an authentication
 * strategy.
 *
 * @author FromSyntax
 * @see AuthenticationStrategy
 */
interface AuthenticationFactory<F> {
    suspend fun create(auth: JwtAuthenticationToken): F
}