package org.hexalite.stronghold.rsocket.server.service

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import org.hexalite.stronghold.data.user.User
import org.hexalite.stronghold.rsocket.server.adapter.common
import org.hexalite.stronghold.rsocket.server.domain.ClanMemberRepository
import org.hexalite.stronghold.rsocket.server.domain.ClanRepository
import org.hexalite.stronghold.rsocket.server.domain.UserRepository
import org.hexalite.stronghold.rsocket.server.domain.UserRoleRepository
import org.hexalite.stronghold.rsocket.server.extension.map
import org.hexalite.stronghold.rsocket.server.model.StrongholdUser
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.*

@Service
@CacheConfig(cacheNames = ["users"])
class UserService(
    private val repository: UserRepository,
    private val clans: ClanRepository,
    private val members: ClanMemberRepository,
    private val roles: UserRoleRepository,
) {
    @Cacheable
    suspend fun StrongholdUser.mapped() = map {
        val member = members.findById(it.id)
        val roles = roles.findAllByUserId(it.id)
        val clan = member?.clanId?.map(clans::findById)
        it.common(clan, roles.toList())
    }

    @Cacheable
    suspend fun findByLastUsername(value: String): User? = repository
        .findByLastUsername(value)
        .toSet()
        .maxByOrNull(StrongholdUser::lastSeen)
        ?.mapped()

    @Cacheable
    suspend fun findById(uuid: UUID): User? = repository.findById(uuid)?.mapped()

    @Cacheable
    suspend fun save(user: StrongholdUser): User = repository.save(user).mapped()

    @Cacheable
    suspend fun count(): Long = repository.count()
}

