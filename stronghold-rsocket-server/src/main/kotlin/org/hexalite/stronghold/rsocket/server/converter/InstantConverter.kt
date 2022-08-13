package org.hexalite.stronghold.rsocket.server.converter

import kotlinx.datetime.Instant
import org.springframework.core.convert.converter.Converter

object InstantConverter: Converter<Instant, String> {
    override fun convert(source: Instant) = source.toString()

    object Reversed: Converter<String, Instant> {
        override fun convert(source: String): Instant = Instant.parse(source)
    }
}