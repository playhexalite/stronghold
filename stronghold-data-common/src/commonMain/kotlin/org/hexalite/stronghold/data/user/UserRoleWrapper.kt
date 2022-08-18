package org.hexalite.stronghold.data.user

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.uuid4
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.hexalite.stronghold.data.serialization.UuidSerializer

@Serializable
data class UserRoleWrapper(
    @SerialName("user_id")
    @Serializable(with = UuidSerializer::class)
    val userId: Uuid = uuid4(),
    @SerialName("role_id")
    val roleId: String = "",
    @SerialName("assign_date")
    val assignDate: Instant = Clock.System.now(),
    @SerialName("assign_id")
    val assignId: UInt = 0u,
)