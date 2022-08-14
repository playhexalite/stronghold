package org.hexalite.stronghold.rsocket.server.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("clans")
data class StrongholdClan(
    @Id
    val id: Int = 0,
    val name: String = "",
    val tag: String = "",
    val leaderId: UUID = UUID.randomUUID(),
)