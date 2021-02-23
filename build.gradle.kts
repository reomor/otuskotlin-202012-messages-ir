plugins {
    kotlin("multiplatform") apply false
    kotlin("jvm") apply false
}

group = "ru.otus.kotlin.messaging"
version = "0.0.1-SNAPSHOT"

subprojects {
    group = rootProject.group
    version = rootProject.version

    repositories {
        jcenter()
        mavenCentral()
    }
}
