package org.hexalite.stronghold.rsocket.server.domain

import kotlinx.coroutines.flow.Flow
import org.hexalite.stronghold.rsocket.server.model.StrongholdClanMember
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClanMemberRepository : CoroutineCrudRepository<StrongholdClanMember, UUID> {
    fun findAllByClanId(id: Int): Flow<StrongholdClanMember>
}