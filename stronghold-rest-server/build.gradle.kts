@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("spring-conventions")
}

dependencies {
}

springBoot {
    mainClass.set("org.hexalite.spring.rest.server.StrongholdRestApplication")
}