plugins {
    id("myproject.jacoco-aggregation")
}

dependencies {
    implementation(project(":ok-common-backend"))
    implementation(project(":ok-common-multiplatform"))
    implementation(project(":ok-multiplatform-mapping"))
}
