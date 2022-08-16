package org.hexalite.stronghold.data.io

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.serializer
import org.reactivestreams.Publisher
import org.springframework.core.ResolvableType
import org.springframework.core.codec.AbstractEncoder
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.http.MediaType
import org.springframework.lang.Nullable
import org.springframework.util.ConcurrentReferenceHashMap
import org.springframework.util.MimeType
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.lang.reflect.Type

/**
 * Decode an object into a CBOR byte stream through `kotlinx.serialization`.
 * @author FromSyntax
 */
@OptIn(ExperimentalSerializationApi::class)
class KotlinxSerializationCborEncoder(
    val cbor: Cbor
) : AbstractEncoder<Any>(MediaType.APPLICATION_CBOR, MediaType("application", "*+cbor")) {
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

    override fun canEncode(elementType: ResolvableType, @Nullable mimeType: MimeType?): Boolean = runCatching {
        serializer(elementType.type)
        super.canEncode(elementType, mimeType) && !CharSequence::class.java.isAssignableFrom(elementType.toClass())
    }.isSuccess

    override fun encode(
        inputStream: Publisher<out Any>,
        bufferFactory: DataBufferFactory,
        elementType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): Flux<DataBuffer> {
        return if(inputStream is Mono) {
            Mono.from(inputStream)
                .map { encodeValue(it, bufferFactory, elementType, mimeType, hints) }
                .flux()
        } else {
            val listType = ResolvableType.forClassWithGenerics(MutableList::class.java, elementType)
            Flux.from(inputStream)
                .collectList()
                .map { encodeValue(it, bufferFactory, listType, mimeType, hints) }
                .flux()
        }
    }

    override fun encodeValue(
        value: Any,
        bufferFactory: DataBufferFactory,
        valueType: ResolvableType,
        mimeType: MimeType?,
        hints: MutableMap<String, Any>?
    ): DataBuffer {
        val bytes = cbor.encodeToByteArray(valueType.type.serializer(), value)
        return bufferFactory.allocateBuffer(bytes.size).also { it.write(bytes) }
    }
}