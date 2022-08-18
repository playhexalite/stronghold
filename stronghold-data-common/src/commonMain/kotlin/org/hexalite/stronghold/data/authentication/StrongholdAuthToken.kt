package org.hexalite.stronghold.data.authentication

import com.benasher44.uuid.Uuid
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.serialization.UuidSerializer

@Serializable
data class StrongholdAuthToken(
    @SerialName("sub")
    @Serializable(with = UuidSerializer::class)
    val userId: Uuid,
    @SerialName("exp")
    val expirationDate: Instant
)
