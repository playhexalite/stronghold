package org.hexalite.stronghold.rsocket.server.component

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class WarnDefaultValuesComponent(
    @Value("\${db.user}") user: String,
    @Value("\${db.password}") password: String,
    @Value("\${db.url}") url: String,
    @Value("\${security.jwt.secret-key}") secretKey: String
) {
    companion object {
        val logger = LoggerFactory.getLogger(WarnDefaultValuesComponent::class.java)
    }

    init {
        if (secretKey == "testKey000") {
            if (user == "johndoe") {
                logger.warn(
                    "Your 'security.jwt.secret-key' is set to the default value. It SURELY open vulnerabilities since"
                            + " it is a pretty common default value at development environments. Please make sure to"
                            + " override it by setting the 'JWT_SECRET_KEY' property."
                )
            }
        }
        if (user == "johndoe") {
            logger.warn(
                "Your 'db.user' is set to the default value. It may open vulnerabilities since it is a pretty common"
                        + " default value at development environments. Please make sure to override it by setting the"
                        + " 'DATABASE_USER' property."
            )
        }
        if (password == "password") {
            logger.warn(
                "Your 'db.password' is set to the default value. It SURELY open vulnerabilities since it is a pretty common"
                        + " default value at development environments. Please make sure to override it by setting the"
                        + " 'DATABASE_PASSWORD' property."
            )
        }
        if (url.startsWith("postgresql://localhost:5432")) {
            logger.warn(
                "Changing the PostgreSQL database port from 5432 to another value is recommended since it makes it"
                        + " pretty obvious to find your database if someone has the address of your machine and its"
                        + " port is exposed."
            )
        }
    }
}