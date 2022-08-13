package org.hexalite.stronghold.rsocket.server.controller

import org.hexalite.stronghold.data.exceptions.UserNotFoundException
import org.hexalite.stronghold.data.serialization.parseUuidOrNull
import org.hexalite.stronghold.data.user.User
import org.hexalite.stronghold.data.user.protocol.UserRequestPayload
import org.hexalite.stronghold.rsocket.server.adapter.stronghold
import org.hexalite.stronghold.rsocket.server.extension.Route
import org.hexalite.stronghold.rsocket.server.extension.map
import org.hexalite.stronghold.rsocket.server.service.UserService
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller

@Controller
@Route("users")
class UserController(private val service: UserService) {
    @Route("retrieve.id")
    suspend fun retrieveUserById(@Payload request: UserRequestPayload.Id): User? =
         request.value.map(service::findById) ?: throw UserNotFoundException("${request.value}")

    @Route("retrieve.last_username")
    suspend fun retrieveUserByLastUsername(@Payload request: UserRequestPayload.LastUsername): User? =
        request.value.map(service::findByLastUsername) ?: throw UserNotFoundException(request.value)

    @Route("retrieve.fallback")
    suspend fun retrieveUserFallingBack(@Payload request: UserRequestPayload.FallingBack): User? =
        request.value.parseUuidOrNull()?.map(service::findById) ?:
        request.value.map(service::findByLastUsername) ?:
        throw UserNotFoundException(request.value)

    @Route("save")
    suspend fun save(@Payload request: User): User =
        service.save(request.stronghold())

    @Route("count")
    suspend fun count(): Long = service.count()
}