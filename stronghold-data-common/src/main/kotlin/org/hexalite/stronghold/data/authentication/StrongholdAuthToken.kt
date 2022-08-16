package org.hexalite.stronghold.data.authentication

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.serialization.UUIDSerializer
import java.util.*

@Serializable
data class StrongholdAuthToken(
    @SerialName("sub")
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    @SerialName("exp")
    val expirationDate: Instant
)
