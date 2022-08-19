package org.hexalite.stronghold.rsocket.client

import io.ktor.client.engine.*
import io.ktor.util.network.*

data class StrongholdClientConfiguration(
    val engine: HttpClientEngineFactory<*>,
    val address: NetworkAddress,
    val redis: RedisConfiguration
) {
    data class RedisConfiguration(
        val address: NetworkAddress,
        val password: String? = null,
        val databaseIndex: Int = 0,
    ) {
        override fun toString() = buildString {
            append("redis://")
            if (password != null) {
                append(password)
                append('@')
            }
            append("${address.hostname}:${address.port}/$databaseIndex")
        }
    }

    fun rsocket(): String = "tcp://${address.hostname}:${address.port}"
}
