package org.hexalite.stronghold.redis

import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.RedisClient

actual class Redis(val address: String) {
    actual suspend fun connect(): RedisConnection {
        val lettuce = RedisClient.create(address)
        val connection = lettuce.connect()
        return RedisConnection(connection)
    }
}