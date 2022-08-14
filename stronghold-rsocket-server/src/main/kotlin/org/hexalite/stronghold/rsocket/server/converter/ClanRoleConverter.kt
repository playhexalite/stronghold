package org.hexalite.stronghold.rsocket.server.converter

import org.hexalite.stronghold.data.clan.ClanRole
import org.springframework.core.convert.converter.Converter

object ClanRoleConverter : Converter<ClanRole, String> {
    override fun convert(source: ClanRole): String = source.name.uppercase()

    object Reversed : Converter<String, ClanRole> {
        override fun convert(source: String): ClanRole = ClanRole.values()
            .first { it.name.equals(source, true) }
    }
}