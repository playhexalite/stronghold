package org.hexalite.stronghold.rsocket.server.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.hexalite.stronghold.data.clan.ClanMember
import org.hexalite.stronghold.rsocket.server.adapter.ClanMemberDataAdapter
import org.hexalite.stronghold.rsocket.server.adapter.common
import org.hexalite.stronghold.rsocket.server.domain.ClanMemberRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClanMemberService(private val repository: ClanMemberRepository) {
    suspend fun findByMemberId(id: UUID): ClanMember? = repository.findById(id)?.common()

    suspend fun findAllByClanId(id: Int): Flow<ClanMember> = repository.findAllByClanId(id)
        .map(ClanMemberDataAdapter::asCommonData)
}