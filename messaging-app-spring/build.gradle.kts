plugins {
    id("myproject.java-conventions")
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
    val jacksonKotlinVersion: String by project

    implementation(project(":ok-multiplatform-mapping"))
    implementation(project(":ok-common-backend"))
    implementation(project(":ok-transport-multiplatform-api"))
    implementation(project(":ok-transport-openapi"))

    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.fu:spring-fu-kofu:$springFuVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonKotlinVersion")
    implementation("org.springframework:spring-webmvc")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
}

tasks {

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

// Equivalent to above
//    compileKotlin {
//        kotlinOptions {
//            freeCompilerArgs = listOf("-Xjsr305=strict")
//            jvmTarget = "11"
//        }
//    }
//
//    compileTestKotlin {
//        kotlinOptions {
//            freeCompilerArgs = listOf("-Xjsr305=strict")
//            jvmTarget = "11"
//        }
//    }

    test {
        useJUnitPlatform()
    }
}
