import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(stronghold.plugins.spring.dependency.management) apply false
    alias(stronghold.plugins.kotlin.jvm)
    alias(stronghold.plugins.kotlin.spring) apply false
    alias(stronghold.plugins.kotlin.serialization) apply false
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = rootProject.stronghold.plugins.kotlin.jvm.get().pluginId)
    apply(plugin = rootProject.stronghold.plugins.kotlin.serialization.get().pluginId)

    repositories {
        mavenCentral()
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
        }
        withType<KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "17"
                apiVersion = "1.8"
                languageVersion = "1.8"
            }
        }
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}