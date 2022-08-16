package org.hexalite.stronghold.rsocket.server.adapter

import org.hexalite.stronghold.data.serialization.optional
import org.hexalite.stronghold.data.user.User
import org.hexalite.stronghold.rsocket.server.model.StrongholdClan
import org.hexalite.stronghold.rsocket.server.model.StrongholdUser
import org.hexalite.stronghold.rsocket.server.model.StrongholdUserRole

object UserDataAdapter {
    fun asCommonData(
        stronghold: StrongholdUser,
        clan: StrongholdClan? = null,
        roles: List<StrongholdUserRole> = emptyList()
    ) = User(
        id = stronghold.id,
        hexes = stronghold.hexes.toUInt(),
        lastUsername = stronghold.lastUsername,
        lastSeen = stronghold.lastSeen,
        createdAt = stronghold.createdAt,
        updatedAt = stronghold.updatedAt,
        clan = clan?.common().optional(),
        roles = roles.ifEmpty { null }?.mapNotNull(StrongholdUserRole::commonEnumerated).optional()
    )

    fun asStrongholdData(common: User) = StrongholdUser(
        id = common.id,
        hexes = common.hexes.toInt(),
        lastUsername = common.lastUsername,
        lastSeen = common.lastSeen,
        createdAt = common.createdAt,
        updatedAt = common.updatedAt
    )
}

@Suppress("nothing_to_inline")
inline fun StrongholdUser.common(clan: StrongholdClan? = null, roles: List<StrongholdUserRole> = emptyList()): User {
    return UserDataAdapter.asCommonData(this, clan, roles)
}

@Suppress("nothing_to_inline")
inline fun User.stronghold() = UserDataAdapter.asStrongholdData(this)
