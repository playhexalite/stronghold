package org.hexalite.stronghold.data.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
object UserIdentityNotProvidedException: RuntimeException("The user identity wasn't provided.")

