package org.hexalite.stronghold.rsocket.server.service

import kotlinx.coroutines.flow.toSet
import org.hexalite.stronghold.data.user.User
import org.hexalite.stronghold.rsocket.server.adapter.common
import org.hexalite.stronghold.rsocket.server.domain.ClanMemberRepository
import org.hexalite.stronghold.rsocket.server.domain.ClanRepository
import org.hexalite.stronghold.rsocket.server.domain.UserRepository
import org.hexalite.stronghold.rsocket.server.extension.map
import org.hexalite.stronghold.rsocket.server.model.StrongholdUser
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val repository: UserRepository,
    private val clans: ClanRepository,
    private val members: ClanMemberRepository
) {
    suspend fun StrongholdUser.mapped() = map {
        val member = members.findById(it.id)
        val clan = if(member != null) clans.findById(member.clanId) else null
        it.common(clan)
    }

    suspend fun findByLastUsername(value: String): User? = repository
        .findByLastUsername(value)
        .toSet()
        .maxByOrNull(StrongholdUser::lastSeen)
        ?.mapped()

    suspend fun findById(uuid: UUID): User? = repository.findById(uuid)?.mapped()

    suspend fun save(user: StrongholdUser): User = repository.save(user).mapped()

    suspend fun count(): Long = repository.count()
}

