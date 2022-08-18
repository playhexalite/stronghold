@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mpp-conventions")
}

kotlin {
    val commonMain by sourceSets.getting {
        dependencies {
        }
    }
}
