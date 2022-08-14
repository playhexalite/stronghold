package org.hexalite.stronghold.data.math

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class BasicMathematicalOperation {
    @SerialName("SUM")
    Sum,

    @SerialName("SUBTRACTION")
    Subtraction,
}