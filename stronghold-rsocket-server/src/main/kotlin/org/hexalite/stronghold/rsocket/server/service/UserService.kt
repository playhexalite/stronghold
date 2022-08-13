package org.hexalite.stronghold.rsocket.server.service

import kotlinx.coroutines.flow.toSet
import org.hexalite.stronghold.data.user.User
import org.hexalite.stronghold.rsocket.server.adapter.common
import org.hexalite.stronghold.rsocket.server.domain.UserRepository
import org.hexalite.stronghold.rsocket.server.model.StrongholdUser
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(val repository: UserRepository) {
    suspend fun findByLastUsername(value: String): User? = repository
        .findByLastUsername(value)
        .toSet()
        .maxByOrNull(StrongholdUser::lastSeen)
        ?.common()

    suspend fun findById(uuid: UUID): User? = repository.findById(uuid)?.common()

    suspend fun save(user: StrongholdUser): User = repository.save(user).common()

    suspend fun count(): Long = repository.count()
}

