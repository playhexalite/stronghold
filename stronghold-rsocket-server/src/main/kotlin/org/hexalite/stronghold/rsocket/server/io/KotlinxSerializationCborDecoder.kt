package org.hexalite.stronghold.rsocket.server.io

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.serializer
import org.reactivestreams.Publisher
import org.springframework.core.ResolvableType
import org.springframework.core.codec.AbstractDecoder
import org.springframework.core.codec.DecodingException
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.MediaType
import org.springframework.lang.Nullable
import org.springframework.util.ConcurrentReferenceHashMap
import org.springframework.util.MimeType
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.io.IOException
import java.lang.reflect.Type

/**
 * Decode a byte stream into CBOR through `kotlinx.serialization`.
 * @author FromSyntax
 */
@OptIn(ExperimentalSerializationApi::class)
class KotlinxSerializationCborDecoder(
    val cbor: Cbor
) : AbstractDecoder<Any>(MediaType.APPLICATION_CBOR, MediaType("application", "*+cbor")) {
    var maxInMemorySize = 256 * 1024

    companion object {
        private val serializerCache = ConcurrentReferenceHashMap<Type, KSerializer<Any>>()
    }

    private fun Type.serializer(): KSerializer<Any> = serializerCache.computeIfAbsent(this) {
        val serializer = serializer(it)
        if (serializer.descriptor.hasPolymorphism()) {
            throw UnsupportedOperationException("Open polymorphic serialization is not supported yet")
        }
        serializer
    }

    override fun canDecode(elementType: ResolvableType, @Nullable mimeType: MimeType?): Boolean = runCatching {
        serializer(elementType.type)
        super.canDecode(elementType, mimeType) && !CharSequence::class.java.isAssignableFrom(elementType.toClass())
    }.isSuccess

    override fun decode(
        inputStream: Publisher<DataBuffer>,
        elementType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Flux<Any> = Flux.error(UnsupportedOperationException("Does not support stream decoding yet"))

    override fun decode(
        buffer: DataBuffer, targetType: ResolvableType, mimeType: MimeType?, hints: MutableMap<String, Any>?
    ): Any {
        val serializer = targetType.type.serializer()
        return try {
            val bytes = ByteArray(buffer.writePosition() - buffer.readPosition()).also { buffer.read(it) }
            cbor.decodeFromByteArray(serializer, bytes)
        } catch (ex: IOException) {
            return DecodingException("I/O error while parsing input stream", ex)
        }
    }

    override fun decodeToMono(
        inputStream: Publisher<DataBuffer>,
        elementType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Mono<Any> = DataBufferUtils.join(inputStream, maxInMemorySize)
        .flatMap { Mono.justOrEmpty(decode(it, elementType, mimeType, hints)) }
}