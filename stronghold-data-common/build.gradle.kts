plugins {
    id("mpp-conventions")
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
