package org.hexalite.stronghold.rsocket.server.model

import kotlinx.datetime.Instant
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("user_roles")
data class StrongholdUserRole(
    val userId: UUID,
    val roleId: String,
    val assignDate: Instant,
    @Id
    val assignId: Int,
)