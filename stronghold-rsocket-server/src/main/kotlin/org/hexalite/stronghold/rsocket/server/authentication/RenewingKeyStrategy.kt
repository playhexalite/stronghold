package org.hexalite.stronghold.rsocket.server.authentication

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jwt.JWTClaimsSet
import kotlinx.coroutines.*
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.datetime.toInstant
import org.hexalite.stronghold.data.microservice.RedisBaseKey
import org.hexalite.stronghold.data.serialization.parseUuidOrNull
import org.hexalite.stronghold.data.user.UserRole
import org.hexalite.stronghold.rsocket.server.service.UserService
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.security.SecureRandom
import java.time.Instant
import java.util.*
import kotlin.random.asKotlinRandom

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

class RenewingKeyStrategy(
    val users: UserService,
    private val redis: ReactiveStringRedisTemplate,
    private val secretKey: String,
) : AuthenticationStrategy<RenewingForm, RenewingForm.Factory> {
    override val factory = RenewingForm.Factory(this)

    @Suppress("MemberVisibilityCanBePrivate")
    val job = Job()

    init {
        val scope = CoroutineScope(Dispatchers.IO + job)
        scope.launch {
            val administrator = "595ce756-dce6-42d7-bf05-75515fbf9c7e"
            val seed = SecureRandom().asKotlinRandom()
            val minutes = 4..16L
            val signer = MACSigner(secretKey)
            while (true) {
                val interval = minutes.random(seed)
                val expiration = Date.from(Instant.now().plusSeconds(interval * 60))
                val claims = JWTClaimsSet.Builder()
                    .subject(administrator)
                    .expirationTime(expiration)
                    .jwtID(UUID.randomUUID().toString())
                    .build()
                val header = JWSHeader.Builder(JWSAlgorithm.HS256).build()
                val token = JWSObject(header, claims.toPayload()).apply { sign(signer) }
                redis.opsForValue().set(RedisBaseKey.AccessToken, token.serialize()).awaitSingleOrNull()
                println("debug => set token to $token")
                delay(interval)
            }
        }
    }

    override suspend fun passes(form: RenewingForm): Boolean = form.passed
}