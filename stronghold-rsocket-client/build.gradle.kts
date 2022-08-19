plugins {
    id("mpp-conventions")
}

kotlin {
    val commonMain by sourceSets.getting {
        dependencies {
            implementation(stronghold.bundles.ktor)
        }
    }
    val jvmMain by sourceSets.getting {
        dependencies {
            implementation(stronghold.ktor.client.cio)
        }
    }
    val jsMain by sourceSets.getting {
        dependencies {
            implementation(stronghold.ktor.client.js)
        }
    }
}
