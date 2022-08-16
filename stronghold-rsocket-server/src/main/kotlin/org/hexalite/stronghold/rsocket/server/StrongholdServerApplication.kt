package org.hexalite.stronghold.rsocket.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories
class StrongholdServerApplication

fun main(args: Array<String>) {
    runApplication<StrongholdServerApplication>(*args)
}