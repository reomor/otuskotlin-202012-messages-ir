import org.gradle.api.JavaVersion.*

plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

java.sourceCompatibility = VERSION_11
java.targetCompatibility = VERSION_11

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    val junitVersion: String by project
    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testImplementation("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
    testImplementation("org.jetbrains.kotlin", "kotlin-test-junit5")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
