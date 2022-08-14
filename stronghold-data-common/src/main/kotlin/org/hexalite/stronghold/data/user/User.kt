package org.hexalite.stronghold.data.user

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.clan.Clan
import org.hexalite.stronghold.data.serialization.Optional
import org.hexalite.stronghold.data.serialization.UUIDSerializer
import java.util.*

/**
 * A data class representing an [User] that joined any of all Hexalite Network servers at
 * least once.
 */
@Serializable
data class User(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val hexes: UInt = 0u,
    @SerialName("last_username")
    val lastUsername: String = "",
    @SerialName("last_seen")
    val lastSeen: Instant = Clock.System.now(),
    @SerialName("created_at")
    val createdAt: Instant = Clock.System.now(),
    @SerialName("updated_at")
    val updatedAt: Instant = Clock.System.now(),
    val clan: Optional<Clan> = Optional.Empty(),
) {
    companion object
}
