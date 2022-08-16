package org.hexalite.stronghold.rsocket.server.authentication

import kotlinx.datetime.toInstant
import org.hexalite.stronghold.data.serialization.parseUuidOrNull
import org.hexalite.stronghold.data.user.UserRole
import org.hexalite.stronghold.rsocket.server.service.UserService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

@JvmInline
value class RenewingForm(val passed: Boolean) {
    @JvmInline
    value class Factory(private val strategy: RenewingKeyStrategy) : AuthenticationFactory<RenewingForm> {
        override suspend fun create(auth: JwtAuthenticationToken): RenewingForm {
            val userId = auth.token.claims["sub"]?.toString()?.parseUuidOrNull() ?: return RenewingForm(false)
            val expiration =
                runCatching { auth.token.claims["exp"]?.toString()?.toInstant() }.getOrNull() ?: return RenewingForm(
                    false
                )
            if (
                expiration.toEpochMilliseconds() < System.currentTimeMillis()
                || strategy.users.findById(userId)?.roles?.value?.contains(UserRole.Administrator) != true
            ) {
                return RenewingForm(false)
            }
            return RenewingForm(true)
        }
    }
}

class RenewingKeyStrategy(val users: UserService) : AuthenticationStrategy<RenewingForm, RenewingForm.Factory> {
    override val factory = RenewingForm.Factory(this)

    override suspend fun passes(form: RenewingForm): Boolean = form.passed
}