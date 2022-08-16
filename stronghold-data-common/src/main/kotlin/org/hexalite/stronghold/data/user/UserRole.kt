package org.hexalite.stronghold.data.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class UserRole {
    @SerialName("administrator")
    Administrator,

    @SerialName("developer")
    Developer,

    @SerialName("artist")
    Artist;

    companion object
}