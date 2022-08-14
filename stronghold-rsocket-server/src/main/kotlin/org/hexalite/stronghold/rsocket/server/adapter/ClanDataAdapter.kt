package org.hexalite.stronghold.rsocket.server.adapter

import org.hexalite.stronghold.data.clan.Clan
import org.hexalite.stronghold.data.serialization.optional
import org.hexalite.stronghold.rsocket.server.model.StrongholdClan
import org.hexalite.stronghold.rsocket.server.model.StrongholdUser

object ClanDataAdapter {
    fun asCommonData(stronghold: StrongholdClan, leader: StrongholdUser? = null, members: List<StrongholdUser>? = null) = Clan(
        id = stronghold.id.toUInt(),
        name = stronghold.name,
        tag = stronghold.tag,
        leaderId = stronghold.leaderId,
        leader = leader?.common().optional(),
        members = members?.map(UserDataAdapter::asCommonData).optional(),
    )

    fun asStrongholdData(common: Clan) = StrongholdClan(
        id = common.id.toInt(),
        name = common.name,
        tag = common.tag,
        leaderId = common.leaderId
    )
}

@Suppress("nothing_to_inline")
inline fun Clan.stronghold() = ClanDataAdapter.asStrongholdData(this)

@Suppress("nothing_to_inline")
inline fun StrongholdClan.common(leader: StrongholdUser? = null, members: List<StrongholdUser>? = null): Clan {
    return ClanDataAdapter.asCommonData(this, leader, members)
}
