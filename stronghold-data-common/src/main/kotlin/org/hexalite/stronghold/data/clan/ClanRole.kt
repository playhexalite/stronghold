package org.hexalite.stronghold.data.clan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ClanRole {
    @SerialName("LEADER")
    Leader,

    @SerialName("OFFICER")
    Officer,
}