@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(stronghold.plugins.spring.boot)
    alias(stronghold.plugins.spring.dependency.management)
    alias(stronghold.plugins.kotlin.spring)
}

dependencies {
    api(project(":stronghold-data-common"))
    implementation(stronghold.bundles.kotlin)
    implementation(stronghold.kotlinx.serialization.cbor)
    implementation("org.springframework.security:spring-security-rsocket")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    //implementation("org.springframework.security:spring-security-rsocket")
    implementation("org.springframework.security:spring-security-messaging")
    implementation("org.springframework.security:spring-security-oauth2-jose")
}