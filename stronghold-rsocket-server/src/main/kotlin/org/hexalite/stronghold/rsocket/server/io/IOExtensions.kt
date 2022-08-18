@file:JvmName("IOExtensions")
package org.hexalite.stronghold.rsocket.server.io

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.SerialDescriptor

@OptIn(ExperimentalSerializationApi::class)
fun SerialDescriptor.hasPolymorphism(alreadyProcessed: MutableSet<String> = mutableSetOf()): Boolean {
    alreadyProcessed.add(serialName)
    if (kind == PolymorphicKind.OPEN) {
        return true
    }
    for (i in 0 until elementsCount) {
        val elementDescriptor = getElementDescriptor(i)
        if (!alreadyProcessed.contains(elementDescriptor.serialName) && elementDescriptor.hasPolymorphism(
                alreadyProcessed
            )
        ) {
            return true
        }
    }
    return false
}

