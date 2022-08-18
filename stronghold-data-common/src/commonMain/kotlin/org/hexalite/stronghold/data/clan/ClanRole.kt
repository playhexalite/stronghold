package org.hexalite.stronghold.data.clan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * An enumeration that represents all possible roles for a [ClanMember].
 * @see Clan
 * @see ClanMember
 */
@Serializable
enum class ClanRole {
    @SerialName("officer")
    Officer;

    companion object
}
