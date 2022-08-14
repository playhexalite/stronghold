@file:UseSerializers(UUIDSerializer::class)
package org.hexalite.stronghold.data.clan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.hexalite.stronghold.data.serialization.UUIDSerializer
import java.util.*

/**
 * A data class representing an [User][org.hexalite.stronghold.data.user.User] that joined a [Clan].
 * @see Clan
 */
@Serializable
data class ClanMember(
    @SerialName("clan_id")
    val clanId: UInt = 0u,
    @SerialName("member_id")
    val memberId: UUID = UUID.randomUUID(),
    val role: ClanRole? = null
) {
    companion object
}
