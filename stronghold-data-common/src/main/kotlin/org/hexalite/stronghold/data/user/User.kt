package org.hexalite.stronghold.data.user

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.serialization.UUIDSerializer
import java.util.*

@Serializable
data class User(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val hexes: UInt = 0u,
    @SerialName("last_username")
    val lastUsername: String = "",
    @SerialName("last_seen")
    val lastSeen: String = "",
    @SerialName("created_at")
    val createdAt: Instant = Clock.System.now(),
    @SerialName("updated_at")
    val updatedAt: Instant = Clock.System.now()
)
