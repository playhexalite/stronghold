plugins {
    id("mpp-conventions")
}

kotlin {
    js(IR) {
        browser()
        nodejs()
    }
    val commonMain by sourceSets.getting {
        dependencies {
        }
    }
}
