@file:Suppress("NOTHING_TO_INLINE")

package org.hexalite.stronghold.data.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@JvmInline
@Serializable(with = Optional.Serializer::class)
value class Optional<T>(val value: T?) {
    companion object {
        val Empty = Optional(null)

        @Suppress("UNCHECKED_CAST")
        inline fun <T> Empty() = Empty as Optional<T>
    }

    class Serializer<T>(private val serializer: KSerializer<T>) : KSerializer<Optional<T>> {
        override val descriptor = serializer.descriptor

        @OptIn(ExperimentalSerializationApi::class)
        override fun deserialize(decoder: Decoder): Optional<T> {
            return if (decoder.decodeNotNullMark()) {
                Optional(serializer.deserialize(decoder))
            } else {
                Empty()
            }
        }

        override fun serialize(encoder: Encoder, value: Optional<T>) {
            serializer.serialize(encoder, value.value ?: return)
        }
    }
}

inline fun <T> T?.optional() = Optional(this)
