package org.hexalite.stronghold.rsocket.server.testing.io

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.encodeToByteArray
import org.hexalite.stronghold.data.user.User
import org.hexalite.stronghold.data.io.KotlinxSerializationCborEncoder
import org.junit.jupiter.api.Test
import org.springframework.core.ResolvableType
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.MediaType
import reactor.core.publisher.Mono

@OptIn(ExperimentalSerializationApi::class)
class KotlinxSerializationCborEncoderTest {
    @Test
    fun `should return the same data as kotlinx-serialization`() {
        val encoder = KotlinxSerializationCborEncoder(Cbor { ignoreUnknownKeys = true })
        val type = ResolvableType.forClass(User::class.java)
        assert(encoder.canEncode(type, MediaType.APPLICATION_CBOR))
        val entity = User()
        val ktxSerializationEncoded = encoder.cbor.encodeToByteArray(entity)
        val factory = DefaultDataBufferFactory()
        val bytes = ByteArray(ktxSerializationEncoded.size)
         encoder.encode(Mono.just(entity), factory, type, null, null)
            .blockFirst()
            ?.read(bytes)
        assert(ktxSerializationEncoded.contentEquals(bytes))
    }
}