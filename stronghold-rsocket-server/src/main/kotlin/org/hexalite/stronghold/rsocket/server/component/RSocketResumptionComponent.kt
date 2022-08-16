package org.hexalite.stronghold.rsocket.server.component

import io.rsocket.core.RSocketServer
import io.rsocket.core.Resume
import org.springframework.boot.rsocket.server.RSocketServerCustomizer
import org.springframework.stereotype.Component

@Component
class RSocketResumptionComponent: RSocketServerCustomizer {
    override fun customize(server: RSocketServer) {
        server.resume(Resume())
    }
}