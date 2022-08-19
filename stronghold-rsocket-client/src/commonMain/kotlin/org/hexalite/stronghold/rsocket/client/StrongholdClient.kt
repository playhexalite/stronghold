package org.hexalite.stronghold.rsocket.client

import io.ktor.client.*
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.RSocketRequestHandler
import io.rsocket.kotlin.core.WellKnownMimeType
import io.rsocket.kotlin.keepalive.KeepAlive
import io.rsocket.kotlin.ktor.client.RSocketSupport
import io.rsocket.kotlin.ktor.client.rSocket
import io.rsocket.kotlin.payload.PayloadMimeType
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class StrongholdClient(val configuration: StrongholdClientConfiguration) {
    val client = HttpClient(configuration.engine) {
        install(RSocketSupport) {
            connector {
                maxFragmentSize = 1024
                connectionConfig {
                    keepAlive = KeepAlive(
                        interval = 30.seconds,
                        maxLifetime = 2.minutes
                    )
                    payloadMimeType = PayloadMimeType(
                        data = WellKnownMimeType.ApplicationCbor,
                        metadata = WellKnownMimeType.MessageRSocketCompositeMetadata
                    )
                }
                acceptor {
                    RSocketRequestHandler {
                        requestResponse {
                            it
                        }
                    }
                }
            }
        }
    }

    var connection: RSocket? = null
        private set

    suspend fun start() {
        connection = client.rSocket(configuration.rsocket())
    }
}

