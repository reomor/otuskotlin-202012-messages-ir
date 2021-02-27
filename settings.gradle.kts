rootProject.name = "kotlin-messages"

include(
    "ok-common-multiplatform",
    "ok-common-backend",
    "ok-transport-multiplatform-api",
    "ok-transport-openapi",
    "ok-multiplatform-mapping"
)

pluginManagement{

    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}
