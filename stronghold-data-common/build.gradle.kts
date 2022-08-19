plugins {
    id("mpp-conventions")
    id("publishing-conventions")
}

kotlin {
    js(IR) {
        browser()
        nodejs()
    }
    val jvmMain by sourceSets.getting {
        dependencies {
            implementation(stronghold.spring.web)
        }
    }
}
