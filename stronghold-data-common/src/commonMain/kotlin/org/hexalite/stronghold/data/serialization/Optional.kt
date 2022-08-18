@file:Suppress("NOTHING_TO_INLINE")

package org.hexalite.stronghold.data.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable(with = OptionalSerializer::class)
expect value class Optional<T: Any> internal constructor(val value: Any?) {
    fun get(): T?
}

expect class OptionalSerializer<T: Any>: KSerializer<Optional<T>>

expect inline fun <T> emptyOptional(): Optional<T & Any>

expect inline fun <T> T?.optional(): Optional<T & Any>

