package org.hexalite.stronghold.data.clan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.serialization.UUIDSerializer
import org.hexalite.stronghold.data.user.User
import java.util.*

/**
 * A data class representing a [Clan] created by an [User].
 * @see [ClanMember]
 */
@Serializable
data class Clan(
    val id: UInt = 0u,
    val name: String = "",
    val tag: String = "",
    @SerialName("leader_id")
    @Serializable(with = UUIDSerializer::class)
    val leaderId: UUID = UUID.randomUUID(),
) {
    companion object
}
