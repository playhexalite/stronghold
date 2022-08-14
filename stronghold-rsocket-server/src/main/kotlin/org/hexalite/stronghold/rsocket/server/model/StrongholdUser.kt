package org.hexalite.stronghold.rsocket.server.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * The User entity represented as a [Table] or model.
 * @see org.hexalite.stronghold.data.user.User
 */
@Table("users")
data class StrongholdUser(
    @Id
    val id: UUID = UUID.randomUUID(),
    val hexes: Int = 0,
    val lastUsername: String = "",
    val lastSeen: Instant = Clock.System.now(),
    @CreatedDate
    val createdAt: Instant = Clock.System.now(),
    @LastModifiedDate
    val updatedAt: Instant = Clock.System.now(),
)
