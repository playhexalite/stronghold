package org.hexalite.stronghold.rsocket.server.controller

import org.hexalite.stronghold.data.clan.Clan
import org.hexalite.stronghold.data.clan.protocol.ClanRequestPayload
import org.hexalite.stronghold.data.exception.ClanNotFoundException
import org.hexalite.stronghold.data.user.protocol.UserRequestPayload
import org.hexalite.stronghold.rsocket.server.adapter.stronghold
import org.hexalite.stronghold.rsocket.server.extension.Route
import org.hexalite.stronghold.rsocket.server.extension.map
import org.hexalite.stronghold.rsocket.server.service.ClanMemberService
import org.hexalite.stronghold.rsocket.server.service.ClanService
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
@Route("clans")
class ClanController(private val service: ClanService, private val members: ClanMemberService) {
    @Route("retrieve.id")
    suspend fun retrieveClanById(@Payload request: ClanRequestPayload.Id): Clan =
        request.value.map(service::findById) ?: throw ClanNotFoundException("${request.value}")

    @Route("retrieve.name")
    suspend fun retrieveClanByName(@Payload request: ClanRequestPayload.Name): Clan =
        request.value.map(service::findByName) ?: throw ClanNotFoundException(request.value)

    @Route("retrieve.tag")
    suspend fun retrieveClanByTag(@Payload request: ClanRequestPayload.Tag): Clan =
        request.value.map(service::findByTag) ?: throw ClanNotFoundException(request.value)

    @Route("retrieve.member")
    suspend fun retrieveClanByMember(@Payload request: UserRequestPayload.Id): Clan =
        request.value.map(service::findByLeaderId)
            ?: request.value.map(members::findByMemberId)?.clanId?.map(service::findById)
            ?: throw ClanNotFoundException("member identified as ${request.value}")

    @Route("retrieve.fallback")
    suspend fun retrieveClanFallingBack(@Payload request: ClanRequestPayload.FallingBack): Clan =
        request.value.map(service::findByName)
            ?: request.value.map(service::findByTag)
            ?: throw ClanNotFoundException(request.value)

    @Route("save")
    suspend fun save(@Payload request: Clan): Clan = service.save(request.stronghold())

    @Route("count")
    suspend fun count(): Long = service.count()
}