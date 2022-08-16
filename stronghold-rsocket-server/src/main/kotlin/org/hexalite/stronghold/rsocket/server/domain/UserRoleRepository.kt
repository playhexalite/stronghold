package org.hexalite.stronghold.rsocket.server.domain

import kotlinx.coroutines.flow.Flow
import org.hexalite.stronghold.rsocket.server.model.StrongholdUserRole
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRoleRepository: CoroutineCrudRepository<StrongholdUserRole, UUID> {
    fun findAllByUserId(id: UUID): Flow<StrongholdUserRole>

    fun findAllByRoleId(id: String): Flow<StrongholdUserRole>
}