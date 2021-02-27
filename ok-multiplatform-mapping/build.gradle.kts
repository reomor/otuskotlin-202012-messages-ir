plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    val junitVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    implementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    implementation("org.jetbrains.kotlin:kotlin-test-junit5")

    implementation(project(":ok-common-backend"))
    implementation(project(":ok-transport-multiplatform-api"))
    implementation(project(":ok-transport-openapi"))
}

tasks {
    test {
        useJUnitPlatform()
    }
}
