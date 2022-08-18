@file:UseSerializers(UuidSerializer::class)

package org.hexalite.stronghold.data.user.protocol

import com.benasher44.uuid.Uuid
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.hexalite.stronghold.data.serialization.UuidSerializer
import kotlin.jvm.JvmInline

@Serializable
sealed interface UserRequestPayload {
    @Serializable
    @JvmInline
    value class Id(val value: Uuid): UserRequestPayload

    @Serializable
    @JvmInline
    value class LastUsername(val value: String): UserRequestPayload

    @Serializable
    @JvmInline
    value class FallingBack(val value: String)
}
