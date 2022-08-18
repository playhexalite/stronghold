@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(stronghold.plugins.kotlin.jvm) apply false
    alias(stronghold.plugins.kotlin.multiplatform) apply false
    alias(stronghold.plugins.kotlin.serialization) apply false
    alias(stronghold.plugins.kotlin.spring) apply false
    alias(stronghold.plugins.spring.boot) apply false
    alias(stronghold.plugins.spring.dependency.management) apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}