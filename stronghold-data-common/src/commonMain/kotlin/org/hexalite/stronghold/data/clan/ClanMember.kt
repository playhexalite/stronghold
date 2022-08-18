@file:UseSerializers(UuidSerializer::class)
package org.hexalite.stronghold.data.clan

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.hexalite.stronghold.data.serialization.UuidSerializer

/**
 * A data class representing an [User][org.hexalite.stronghold.data.user.User] that joined a [Clan].
 * @see Clan
 */
@Serializable
data class ClanMember(
    @SerialName("clan_id")
    val clanId: UInt = 0u,
    @SerialName("member_id")
    val memberId: Uuid = uuid4(),
    val role: ClanRole? = null
) {
    companion object
}
