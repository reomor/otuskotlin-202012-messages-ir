plugins {
    application
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

dependencies {
    val kotlinVersion: String by project
    val ktorVersion: String by project
    val logbackVersion: String by project

    implementation(project(":ok-multiplatform-mapping"))
    implementation(project(":ok-common-backend"))
    implementation(project(":ok-transport-multiplatform-api"))
    implementation(project(":ok-transport-openapi"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src/main/kotlin")
kotlin.sourceSets["test"].kotlin.srcDirs("src/test/kotlin")

sourceSets["main"].resources.srcDirs(
    "resources",
    "src/main/resources"
)
sourceSets["test"].resources.srcDirs(
    "resources",
    "src/test/resources"
)
