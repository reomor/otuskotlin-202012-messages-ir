rootProject.name = "kotlin-messages"

include(
    "common-multiplatform",
    "common-backend"
)

pluginManagement{
    plugins {
        val kotlinVersion: String by settings
        kotlin("jvm") version kotlinVersion apply false
        kotlin("multiplatform") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false
    }
}
