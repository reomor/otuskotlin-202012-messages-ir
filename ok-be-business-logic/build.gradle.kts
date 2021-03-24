plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val junitVersion: String by project

    implementation(project(":ok-common-backend"))
    implementation(project(":ok-multiplatform-mapping"))
    implementation(project(":ok-pipelines-multiplatform"))
    implementation(project(":ok-transport-multiplatform-api"))

    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testImplementation("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
    testImplementation("org.jetbrains.kotlin", "kotlin-test-junit5")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
