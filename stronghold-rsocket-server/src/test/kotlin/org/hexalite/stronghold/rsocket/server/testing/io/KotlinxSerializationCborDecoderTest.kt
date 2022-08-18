package org.hexalite.stronghold.rsocket.server.testing.io

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.encodeToByteArray
import org.hexalite.stronghold.data.user.User
import org.hexalite.stronghold.rsocket.server.io.KotlinxSerializationCborDecoder
import org.junit.jupiter.api.Test
import org.springframework.core.ResolvableType
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.MediaType

@OptIn(ExperimentalSerializationApi::class)
class KotlinxSerializationCborDecoderTest {
    @Test
    fun `should return back the same data`() {
        val decoder = KotlinxSerializationCborDecoder(Cbor { ignoreUnknownKeys = true })
        val type = ResolvableType.forClass(User::class.java)
        assert(decoder.canDecode(type, MediaType.APPLICATION_CBOR))
        val entity = User()
        val encoded = decoder.cbor.encodeToByteArray(entity)
        val bufferFactory = DefaultDataBufferFactory()
        val buffer = bufferFactory.allocateBuffer(encoded.size).also { it.write(encoded) }
        val decoded = decoder.decode(buffer, type, null, null)
        println(entity)
        println(decoded)
        assert(decoded == entity)
    }
}