package org.hexalite.stronghold.data.clan

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.serialization.UUIDSerializer
import java.util.*

@Serializable
data class Clan(
    val id: UInt = 0u,
    val name: String = "",
    val tag: String = "",
    @SerialName("leader_id")
    @Serializable(with = UUIDSerializer::class)
    val leaderId: UUID = UUID.randomUUID(),
    val experience: ULong = 0u,
)
