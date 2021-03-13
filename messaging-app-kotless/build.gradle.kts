import io.kotless.plugin.gradle.dsl.kotless

plugins {
    kotlin("jvm")
    id("io.kotless")
}

group = rootProject.group
version = rootProject.version

dependencies {
    val kotlessVersion: String by project
    val coroutineVersion: String by project

    implementation(project(":ok-multiplatform-mapping"))
    implementation(project(":ok-common-backend"))
    implementation(project(":ok-transport-multiplatform-api"))
    implementation(project(":ok-transport-openapi"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("io.kotless", "kotless-lang", kotlessVersion)
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
}

kotless {

    config {

        bucket = "ru.otus.kotlin.messaging"

        dsl {
            type = io.kotless.DSLType.Kotless
        }

        terraform {
            profile = "messaging"
            region = "eu-north-1"
        }

        optimization {
            autowarm = io.kotless.plugin.gradle.dsl.KotlessConfig.Optimization.Autowarm(enable = false, minutes = -1)
        }
    }

    webapp {

        route53 = io.kotless.plugin.gradle.dsl.Webapp.Route53(
            "messaging",
            "rem.kotlin.otus.com",
            "rem.kotlin.otus.com"
        )

        lambda {
            memoryMb = 256
        }
    }

    extensions {
        local {
            //enable AWS emulation (disabled by default)
            useAWSEmulation = true
        }
        terraform {
            allowDestroy = true
        }
    }
}
