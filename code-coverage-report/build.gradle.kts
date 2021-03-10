import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("myproject.jacoco-aggregation")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

repositories {
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    implementation(project(":messaging-app-spring"))
    implementation(project(":ok-common-backend"))
    implementation(project(":ok-common-multiplatform"))
    implementation(project(":ok-multiplatform-mapping"))
}

tasks {
    "bootJar"(BootJar::class) {
        enabled = false
    }
}
