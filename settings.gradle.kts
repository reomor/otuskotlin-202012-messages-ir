rootProject.name = "kotlin-course-202012"

include("useful-stuff")

pluginManagement{
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion
    }
}
