package org.hexalite.stronghold.redis

import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.coroutines
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalLettuceCoroutinesApi::class)
actual class RedisConnection(private val lettuce: StatefulRedisConnection<String, String>) {
    val commands: RedisCoroutinesCommands<String, String> = lettuce.coroutines()

    actual suspend fun get(key: String): String? = commands.get(key)

    actual suspend fun set(key: String, value: String): String? = commands.set(key, value)

    actual suspend fun close() = withContext(Dispatchers.Default) {
        lettuce.close()
    }
}