package org.hexalite.stronghold.data.user

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.clan.Clan
import org.hexalite.stronghold.data.serialization.Optional
import org.hexalite.stronghold.data.serialization.UuidSerializer
import org.hexalite.stronghold.data.serialization.emptyOptional

/**
 * A data class representing an [User] that joined any of all Hexalite Network servers at
 * least once.
 */
@Serializable
data class User(
    @Serializable(with = UuidSerializer::class)
    val id: Uuid = uuid4(),
    val hexes: UInt = 0u,
    @SerialName("last_username")
    val lastUsername: String = "",
    @SerialName("last_seen")
    val lastSeen: Instant = Clock.System.now(),
    @SerialName("created_at")
    val createdAt: Instant = Clock.System.now(),
    @SerialName("updated_at")
    val updatedAt: Instant = Clock.System.now(),
    val clan: Optional<Clan> = emptyOptional(),
    val roles: Optional<List<UserRole>> = emptyOptional(),
) {
    companion object
}
