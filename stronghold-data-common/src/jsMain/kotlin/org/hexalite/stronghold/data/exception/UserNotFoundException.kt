package org.hexalite.stronghold.data.exception

actual data class UserNotFoundException(val id: String): RuntimeException("The user identified as '$id' was not found.")
