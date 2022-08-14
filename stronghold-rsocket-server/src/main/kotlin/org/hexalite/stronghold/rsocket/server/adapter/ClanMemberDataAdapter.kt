package org.hexalite.stronghold.rsocket.server.adapter

import org.hexalite.stronghold.data.clan.ClanMember
import org.hexalite.stronghold.rsocket.server.model.StrongholdClanMember

object ClanMemberDataAdapter {
    fun asCommonData(stronghold: StrongholdClanMember) = ClanMember(
        clanId = stronghold.clanId.toUInt(),
        memberId = stronghold.memberId,
        role = stronghold.role
    )
    fun asStrongholdData(common: ClanMember) = StrongholdClanMember(
        clanId = common.clanId.toInt(),
        memberId = common.memberId,
        role = common.role
    )
}

inline fun StrongholdClanMember.common() = ClanMemberDataAdapter.asCommonData(this)

inline fun ClanMember.stronghold() = ClanMemberDataAdapter.asStrongholdData(this)
