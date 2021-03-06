plugins {
    id("io.spring.dependency-management")
    id("org.springframework.boot")
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
    val springFuVersion: String by project
    val serializationVersion: String by project

    implementation(kotlin("stdlib"))

    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.fu:spring-fu-kofu:$springFuVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("org.springframework:spring-webmvc")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks {

    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    test {
        useJUnitPlatform()
    }
}
