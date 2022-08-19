import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("spring-conventions")
}

dependencies {
}

tasks.named<BootJar>("bootJar") {
    mainClass.set("org.hexalite.spring.rest.server.StrongholdRestApplication")
}