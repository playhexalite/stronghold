@file:Suppress("NOTHING_TO_INLINE")

package org.hexalite.stronghold.data.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@JvmInline
@Serializable(with = OptionalSerializer::class)
actual value class Optional<T: Any>(val value: Any?) {
    actual fun get(): T? = value as? T?
}

actual class OptionalSerializer<T: Any>(private val serializer: KSerializer<T>) : KSerializer<Optional<T>> {
    override val descriptor = serializer.descriptor

    @OptIn(ExperimentalSerializationApi::class)
    override fun deserialize(decoder: Decoder): Optional<T> {
        return if (decoder.decodeNotNullMark()) {
           serializer.deserialize(decoder).optional()
        } else {
            emptyOptional()
        }
    }

    override fun serialize(encoder: Encoder, value: Optional<T>) {
        serializer.serialize(encoder, value.get() ?: return)
    }
}

actual inline fun <T> emptyOptional() = Optional<T & Any>(null)

actual inline fun <T> T?.optional() = Optional<T & Any>(this)
