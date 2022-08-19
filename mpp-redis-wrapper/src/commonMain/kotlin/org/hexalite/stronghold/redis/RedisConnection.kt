package org.hexalite.stronghold.redis

expect class RedisConnection {
    suspend fun get(key: String): String?

    suspend fun set(key: String, value: String): String?

    suspend fun close()
}