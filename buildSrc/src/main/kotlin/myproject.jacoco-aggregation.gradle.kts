plugins {
    java
    jacoco
}

repositories {
    jcenter()
}

jacoco {
    val jacocoVersion: String by project
    toolVersion = jacocoVersion
}

// A resolvable configuration to collect source code
val sourcesPath: Configuration by configurations.creating {
    isVisible = false
    isCanBeResolved = true
    isCanBeConsumed = false
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("source-folders"))
    }
}

// A resolvable configuration to collect JaCoCo coverage data
val coverageDataPath: Configuration by configurations.creating {
    isVisible = false
    isCanBeResolved = true
    isCanBeConsumed = false
    extendsFrom(configurations.implementation.get())
    attributes {
        attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage.JAVA_RUNTIME))
        attribute(Category.CATEGORY_ATTRIBUTE, objects.named(Category.DOCUMENTATION))
        attribute(DocsType.DOCS_TYPE_ATTRIBUTE, objects.named("jacoco-coverage-data"))
    }
}

// Task to gather code coverage from multiple subprojects
val codeCoverageReport by tasks.registering(JacocoReport::class) {

    println("jacoco runtime classpath:")
    val matching = configurations.runtimeClasspath.get()
        .asFileTree
        .filter { file -> file.isFile && file.path.contains(projectDir.parent) }
    matching.forEach { file -> println(file.path) }
    additionalClassDirs(matching)

    println("jacoco source dirs:")
    val files = sourcesPath.incoming.artifactView { lenient(true) }
        .files
        .filter { file -> file.path.contains(projectDir.parent) }
    files.forEach { file -> println(file.path) }
    additionalSourceDirs(files)

    println("jacoco execution data:")
    val executionData = coverageDataPath.incoming.artifactView { lenient(true) }
        .files
        .filter {
            it.exists() && it.path.endsWith("exec")
        }
    executionData.forEach { file -> println(file.path) }
    executionData(executionData)

    reports {

        // xml is usually used to integrate code coverage with
        // other tools like SonarQube, Coveralls or Codecov
        xml.isEnabled = true
        // HTML reports can be used to see code coverage
        // without any external tools
        html.isEnabled = true
    }
}

//val customJacocoTestCoverageVerification by tasks.registering(JacocoCoverageVerification::class) {
//    violationRules {
//        rule {
//            element = "CLASS"
//            limit {
//                counter = "LINE"
//                value = "COVEREDRATIO"
//                minimum = BigDecimal.valueOf(1.0)
//            }
//            excludes = listOf(
//                "io.reflectoring.coverage.part.PartlyCovered"
//            )
//        }
//    }
//}

// Make JaCoCo report generation part of the 'check' lifecycle phase
tasks {
    check {
//        dependsOn(customJacocoTestCoverageVerification)
        dependsOn(codeCoverageReport)
    }
}
