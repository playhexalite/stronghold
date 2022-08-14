package org.hexalite.stronghold.data.clan.protocol

import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.math.BasicMathematicalOperation

@Serializable
data class ClanExperienceOperationPayload(
    val operation: BasicMathematicalOperation,
    val value: Int,
)