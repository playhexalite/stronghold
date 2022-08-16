@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(stronghold.plugins.spring.boot)
    alias(stronghold.plugins.spring.dependency.management)
    alias(stronghold.plugins.kotlin.spring)
}

dependencies {
    implementation(stronghold.bundles.kotlin)
    //implementation(stronghold.spring.web)
    implementation(stronghold.kotlinx.serialization.cbor)
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.security:spring-security-rsocket")
}