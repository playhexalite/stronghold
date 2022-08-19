import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("spring-conventions")
}

dependencies {
    implementation(stronghold.caffeine)
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-rsocket")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-rsocket")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    implementation("org.flywaydb:flyway-core")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
}

tasks.named<BootJar>("bootJar") {
    mainClass.set("org.hexalite.spring.rsocket.server.StrongholdServerApplication")
}
