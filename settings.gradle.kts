rootProject.name = "stronghold"

enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("stronghold") {
            from(files("./stronghold.libs.toml"))
        }
    }
}

include(
    ":stronghold-rsocket-server",
    ":stronghold-data-common",
    ":stronghold-rsocket-client",
    ":stronghold-rest-server",
    ":stronghold-rest-client",
)
