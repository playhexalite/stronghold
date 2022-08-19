plugins {
    id("mpp-conventions")
}

kotlin {
    js(IR) {
        browser()
        nodejs()
    }
    val jsMain by sourceSets.getting {
        dependencies {
            implementation(npm("redis-om", stronghold.versions.redis.om.get()))
        }
    }
    val jvmMain by sourceSets.getting {
        dependencies {
            implementation(stronghold.kotlinx.coroutines.reactive)
            implementation(stronghold.lettuce.core)
        }
    }
}