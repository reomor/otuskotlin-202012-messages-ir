rootProject.name = "kotlin-messages"

include(
    "common-multiplatform",
    "common-backend",
    "transport-multiplatform-api",
    "transport-openapi"
)

pluginManagement{

    val kotlinVersion: String by settings
    val openapiVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion
    }
}
