@file:JvmName("GenericExt")
package org.hexalite.stronghold.rsocket.server.extension

@Suppress("REDUNDANT_INLINE_SUSPEND_FUNCTION_TYPE")
suspend inline fun <T, R> T.map(result: suspend (T) -> R) = result(this)
