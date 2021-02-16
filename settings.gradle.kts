rootProject.name = "m2-init-project"

include(
    "common",
    "backend"
)

pluginManagement{
    val kotlinVersion: String by settings
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.multiplatform" -> {
                    useVersion(kotlinVersion)
                }
                "org.jetbrains.kotlin.jvm" -> {
                    useVersion(kotlinVersion)
                }
            }
        }
    }
}
