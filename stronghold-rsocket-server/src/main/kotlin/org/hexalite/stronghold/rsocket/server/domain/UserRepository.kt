package org.hexalite.stronghold.rsocket.server.domain

import kotlinx.coroutines.flow.Flow
import org.hexalite.stronghold.rsocket.server.model.StrongholdUser
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface UserRepository: CoroutineCrudRepository<StrongholdUser, UUID> {
    suspend fun findByLastUsername(lastUsername: String): Flow<StrongholdUser>
}