package org.hexalite.stronghold.rsocket.server.domain

import org.hexalite.stronghold.rsocket.server.model.StrongholdClan
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ClanRepository: CoroutineCrudRepository<StrongholdClan, Int> {
    suspend fun findByName(name: String): StrongholdClan?

    suspend fun findByTag(tag: String): StrongholdClan?

    suspend fun findByLeaderId(id: UUID): StrongholdClan?
}