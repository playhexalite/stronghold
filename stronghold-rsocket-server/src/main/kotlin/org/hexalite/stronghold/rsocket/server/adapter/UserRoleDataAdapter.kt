package org.hexalite.stronghold.rsocket.server.adapter

import org.hexalite.stronghold.data.user.UserRole
import org.hexalite.stronghold.data.user.UserRoleWrapper
import org.hexalite.stronghold.rsocket.server.model.StrongholdUserRole

object UserRoleDataAdapter {
    fun asCommonData(stronghold: StrongholdUserRole) = UserRoleWrapper(
        userId = stronghold.userId,
        roleId = stronghold.roleId,
        assignDate = stronghold.assignDate,
        assignId = stronghold.assignId.toUInt(),
    )

    fun asStrongholdData(common: UserRoleWrapper) = StrongholdUserRole(
        userId = common.userId,
        roleId = common.roleId,
        assignDate = common.assignDate,
        assignId = common.assignId.toInt(),
    )
}

@Suppress("nothing_to_inline")
inline fun StrongholdUserRole.common(): UserRoleWrapper {
    return UserRoleDataAdapter.asCommonData(this)
}

@Suppress("nothing_to_inline")
inline fun UserRoleWrapper.stronghold() = UserRoleDataAdapter.asStrongholdData(this)

@Suppress("nothing_to_inline")
inline fun StrongholdUserRole.commonEnumerated() = UserRole.values().find { it.name == this.roleId }
