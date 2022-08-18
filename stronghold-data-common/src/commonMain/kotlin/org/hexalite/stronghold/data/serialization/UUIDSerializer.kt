package org.hexalite.stronghold.data.serialization

import com.benasher44.uuid.Uuid
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

inline fun String.parseUuid(): Uuid = UuidSerializer.parse(this)

inline fun String.parseUuidOrNull(): Uuid? = runCatching {
    UuidSerializer.parse(this)
}.getOrNull()

object UuidSerializer : KSerializer<Uuid> {
    private val digits = IntArray(127) { -1 }

    init {
        for (i in 0..9) {
            digits['0'.code + i] = i
        }
        for (i in 0..5) {
            digits['a'.code + i] = 10 + i
            digits['A'.code + i] = 10 + i
        }
    }

    private fun String.throwBadCharSerializationException(index: Int, invalidChar: Char): Nothing =
        throw SerializationException(
            "Non-hex character '$invalidChar' at position $index not valid for Uuid deserialization of $this"
        )

    private fun String.throwBadFormatSerializationException(): Nothing =
        throw SerializationException("Invalid Uuid format: $this")

    private fun String.byte(index: Int): Int {
        val character = get(index)
        val front = get(index + 1)
        if (character.code < 128 && front.code < 128) {
            val hex = digits[character.code] shl 4 or digits[front.code]
            if (hex >= 0) {
                return hex
            }
        }
        return if (character.code > 127 || digits[character.code] < 0) {
            throwBadCharSerializationException(index, character)
        } else throwBadCharSerializationException(index + 1, front)
    }

    fun String.int(index: Int): Int = ((byte(index) shl 24)
            + (byte(index + 2) shl 16)
            + (byte(index + 4) shl 8)
            + byte(index + 6))

    private fun String.short(index: Int): Int = (byte(index) shl 8) + byte(index + 2)

    fun parse(input: String): Uuid {
        println(input)
        if (input[8] != '-' || input[13] != '-' || input[18] != '-' || input[23] != '-') {
            input.throwBadFormatSerializationException()
        }
        val l1 = input.int(0).toLong() shl 32
        val l2 = (input.short(9).toLong() shl 16) or input.short(14).toLong()
        val hi = l1 + l2
        val i1 = (input.short(19) shl 16 or input.short(24)).toLong() shl 32
        val i2 = (input.int(28).toLong() shl 32) ushr 32
        val lo = i1 or i2
        return Uuid(hi, lo)
    }

    override val descriptor = PrimitiveSerialDescriptor("java.util.Uuid", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Uuid = parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: Uuid) = encoder.encodeString(value.toString())
}