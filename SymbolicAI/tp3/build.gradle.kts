import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.0"
    application
}

group = "fr.uge.ai"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // blazegraph
    implementation("com.blazegraph:bigdata-core:2.1.4")
    implementation("com.blazegraph:bigdata-client:2.1.4")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}