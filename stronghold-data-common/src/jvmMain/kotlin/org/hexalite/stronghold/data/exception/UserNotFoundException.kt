package org.hexalite.stronghold.data.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
actual data class UserNotFoundException(val id: String): RuntimeException("The user identified as '$id' was not found.")
