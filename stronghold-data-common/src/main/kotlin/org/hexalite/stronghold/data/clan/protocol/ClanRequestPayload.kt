@file:UseSerializers(UUIDSerializer::class)

package org.hexalite.stronghold.data.clan.protocol

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import org.hexalite.stronghold.data.serialization.UUIDSerializer
import java.util.*

@Serializable
sealed interface ClanRequestPayload {
    @Serializable
    @JvmInline
    value class Id(val value: UInt): ClanRequestPayload

    @Serializable
    @JvmInline
    value class Name(val value: String): ClanRequestPayload

    @Serializable
    @JvmInline
    value class Tag(val value: String): ClanRequestPayload

    @Serializable
    @JvmInline
    value class FallingBack(val value: String)
}
