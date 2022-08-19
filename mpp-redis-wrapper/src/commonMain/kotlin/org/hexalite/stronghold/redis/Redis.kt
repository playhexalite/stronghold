package org.hexalite.stronghold.redis

expect class Redis {
    suspend fun connect(): RedisConnection
}