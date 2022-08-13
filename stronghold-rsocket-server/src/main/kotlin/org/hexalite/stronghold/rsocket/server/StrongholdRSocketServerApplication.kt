package org.hexalite.stronghold.rsocket.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@ConfigurationPropertiesScan
@SpringBootApplication
class StrongholdRSocketServerApplication

fun main(args: Array<String>) {
    runApplication<StrongholdRSocketServerApplication>(*args)
}
