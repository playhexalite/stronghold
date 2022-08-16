package org.hexalite.stronghold.data.user

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.serialization.UUIDSerializer
import java.util.*

@Serializable
data class UserRoleWrapper(
    @SerialName("user_id")
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID,
    @SerialName("role_id")
    val roleId: String,
    @SerialName("assign_date")
    val assignDate: Instant,
    @SerialName("assign_id")
    val assignId: UInt,
)