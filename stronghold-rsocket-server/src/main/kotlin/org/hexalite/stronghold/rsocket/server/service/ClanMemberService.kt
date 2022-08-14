package org.hexalite.stronghold.rsocket.server.service

import org.hexalite.stronghold.data.clan.ClanMember
import org.hexalite.stronghold.rsocket.server.adapter.common
import org.hexalite.stronghold.rsocket.server.domain.ClanMemberRepository
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
@CacheConfig(cacheNames = ["clan_members"])
class ClanMemberService(private val repository: ClanMemberRepository) {
    @Cacheable
    suspend fun findByMemberId(id: UUID): ClanMember? = repository.findById(id)?.common()
}