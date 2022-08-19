package org.hexalite.stronghold.redis

actual class RedisConnection {
    actual suspend fun get(key: String): String? {
        TODO("Not yet implemented")
    }

    actual suspend fun set(key: String, value: String): String? {
        TODO("Not yet implemented")
    }

    actual suspend fun close() {
    }

}