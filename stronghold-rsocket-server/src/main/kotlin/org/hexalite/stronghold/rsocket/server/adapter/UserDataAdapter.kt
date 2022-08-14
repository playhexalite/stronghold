package org.hexalite.stronghold.rsocket.server.adapter

import org.hexalite.stronghold.data.serialization.optional
import org.hexalite.stronghold.data.user.User
import org.hexalite.stronghold.rsocket.server.model.StrongholdClan
import org.hexalite.stronghold.rsocket.server.model.StrongholdUser

object UserDataAdapter {
    fun asCommonData(stronghold: StrongholdUser, clan: StrongholdClan? = null) = User(
        id = stronghold.id,
        hexes = stronghold.hexes.toUInt(),
        lastUsername = stronghold.lastUsername,
        lastSeen = stronghold.lastSeen,
        createdAt = stronghold.createdAt,
        updatedAt = stronghold.updatedAt,
        clan = clan?.common().optional(),
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
inline fun StrongholdUser.common(clan: StrongholdClan? = null): User {
    return UserDataAdapter.asCommonData(this, clan)
}

@Suppress("nothing_to_inline")
inline fun User.stronghold() = UserDataAdapter.asStrongholdData(this)
