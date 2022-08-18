package org.hexalite.stronghold.data.clan

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.serialization.Optional
import org.hexalite.stronghold.data.serialization.UuidSerializer
import org.hexalite.stronghold.data.serialization.emptyOptional
import org.hexalite.stronghold.data.user.User

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
    @Serializable(with = UuidSerializer::class)
    val leaderId: Uuid = uuid4(),
    val leader: Optional<User> = emptyOptional(),
    val members: Optional<List<User>> = emptyOptional(),
) {
    companion object
}
