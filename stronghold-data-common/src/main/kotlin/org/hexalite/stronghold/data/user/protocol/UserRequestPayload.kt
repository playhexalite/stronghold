@file:UseSerializers(UUIDSerializer::class)

package org.hexalite.stronghold.data.user.protocol

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.hexalite.stronghold.data.serialization.UUIDSerializer
import java.util.*

@Serializable
sealed interface UserRequestPayload {
    @Serializable
    @JvmInline
    value class Id(val value: UUID): UserRequestPayload

    @Serializable
    @JvmInline
    value class LastUsername(val value: String): UserRequestPayload

    @Serializable
    @JvmInline
    value class FallingBack(val value: String)
}
