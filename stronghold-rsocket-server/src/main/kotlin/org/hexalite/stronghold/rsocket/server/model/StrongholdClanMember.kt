package org.hexalite.stronghold.rsocket.server.model

import org.hexalite.stronghold.data.clan.ClanRole
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("clan_members")
data class StrongholdClanMember(
    @Id
    val memberId: UUID = UUID.randomUUID(),
    val clanId: Int = 0,
    val role: ClanRole? = null,
)
