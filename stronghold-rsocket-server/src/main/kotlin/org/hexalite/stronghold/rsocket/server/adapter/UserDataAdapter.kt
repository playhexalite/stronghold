package org.hexalite.stronghold.rsocket.server.adapter

import org.hexalite.stronghold.data.user.User
import org.hexalite.stronghold.rsocket.server.model.StrongholdUser

object UserDataAdapter {
    fun asCommonData(stronghold: StrongholdUser) = User(
        id = stronghold.id,
        hexes = stronghold.hexes,
        lastUsername = stronghold.lastUsername,
        lastSeen = stronghold.lastSeen,
        createdAt = stronghold.createdAt,
        updatedAt = stronghold.updatedAt
    )

    fun asStrongholdData(common: User) = StrongholdUser(
        id = common.id,
        hexes = common.hexes,
        lastUsername = common.lastUsername,
        lastSeen = common.lastSeen,
        createdAt = common.createdAt,
        updatedAt = common.updatedAt
    )
}

@Suppress("nothing_to_inline")
inline fun StrongholdUser.common() = UserDataAdapter.asCommonData(this)

@Suppress("nothing_to_inline")
inline fun User.stronghold() = UserDataAdapter.asStrongholdData(this)
