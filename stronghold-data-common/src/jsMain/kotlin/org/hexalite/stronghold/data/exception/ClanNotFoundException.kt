package org.hexalite.stronghold.data.exception

actual data class ClanNotFoundException(val id: String): RuntimeException("The clan identified as '$id' was not found.")