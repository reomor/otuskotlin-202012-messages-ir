import io.kotless.plugin.gradle.dsl.KotlessConfig.Optimization.Autowarm
import io.kotless.plugin.gradle.dsl.Webapp.Route53
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

        bucket = "ru.kotless.messaging"
//        prefix = "dev"

        dsl {
            type = io.kotless.DSLType.Kotless
        }

        terraform {
            profile = "kotless"
            region = "us-east-1"
            version = "0.13.6"
        }

        optimization {
            autowarm = Autowarm(enable = false, minutes = -1)
        }
    }

    webapp {

        route53 = Route53(
            "messaging",
            "ioninremlabs.com",
            "ioninremlabs.com"
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

tasks {

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
}
