@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mpp-conventions")
}

kotlin {
    val jvmMain by sourceSets.getting {
        dependencies {
            implementation(stronghold.spring.web)
        }
    }
}
