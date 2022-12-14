package org.hexalite.stronghold.rsocket.server.domain

import kotlinx.coroutines.flow.Flow
import org.hexalite.stronghold.rsocket.server.model.StrongholdUser
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CoroutineCrudRepository<StrongholdUser, UUID> {
    fun findByLastUsername(lastUsername: String): Flow<StrongholdUser>
}
