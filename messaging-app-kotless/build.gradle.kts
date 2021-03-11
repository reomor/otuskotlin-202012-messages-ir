plugins {
    kotlin("jvm")
    id("io.kotless")
}

group = rootProject.group
version = rootProject.version

dependencies {

    val kotlessVersion: String by project

    implementation(kotlin("stdlib-jdk8"))
    implementation("io.kotless", "kotless-lang", kotlessVersion)
}
