package org.hexalite.stronghold.rsocket.server.service

import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.toList
import org.hexalite.stronghold.data.clan.Clan
import org.hexalite.stronghold.rsocket.server.adapter.common
import org.hexalite.stronghold.rsocket.server.domain.ClanMemberRepository
import org.hexalite.stronghold.rsocket.server.domain.ClanRepository
import org.hexalite.stronghold.rsocket.server.domain.UserRepository
import org.hexalite.stronghold.rsocket.server.model.StrongholdClan
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*
import org.hexalite.stronghold.rsocket.server.extension.map as map2

@Service
@CacheConfig(cacheNames = ["clans"])
class ClanService(
    private val repository: ClanRepository,
    private val members: ClanMemberRepository,
    private val users: UserRepository
) {
    @Cacheable
    suspend fun StrongholdClan.mapped() = map2 {
        val leader = users.findById(it.leaderId)
        val members = members.findAllByClanId(it.id)
            .mapNotNull { member -> users.findById(member.memberId) }
            .toList()
        common(leader, members)
    }

    @Cacheable
    suspend fun findById(id: UInt): Clan? = repository.findById(id.toInt())?.mapped()

    @Cacheable
    suspend fun findByName(name: String): Clan? = repository.findByName(name)?.mapped()

    @Cacheable
    suspend fun findByTag(tag: String): Clan? = repository.findByTag(tag)?.mapped()

    @Cacheable
    suspend fun findByLeaderId(id: UUID): Clan? = repository.findByLeaderId(id)?.mapped()

    @Cacheable
    suspend fun save(clan: StrongholdClan): Clan = repository.save(clan).mapped()

    @Cacheable
    suspend fun count(): Long = repository.count()
}