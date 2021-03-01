import org.gradle.api.JavaVersion.*

plugins {
    id("myproject.java-conventions")
    kotlin("jvm")
}

java.sourceCompatibility = VERSION_11
java.targetCompatibility = VERSION_11

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    val junitVersion: String by project
    val logbackVersion: String by project
    val slf4jVersion: String by project

    api("org.slf4j", "slf4j-api", slf4jVersion)
    implementation("ch.qos.logback", "logback-classic", logbackVersion)
    implementation("ch.qos.logback", "logback-core", logbackVersion)

    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testImplementation("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
    testImplementation("org.jetbrains.kotlin", "kotlin-test-junit5")
}
