plugins {
    java
    kotlin("jvm") version "1.8.0"
    id("com.github.davidmc24.gradle.plugin.avro") version "1.5.0"
}

repositories {
    mavenCentral()
}

val kafkaVer = "3.1.0"

dependencies {
    implementation("org.apache.kafka", "kafka-clients", kafkaVer)
    implementation("org.apache.kafka", "kafka-streams", kafkaVer)
    implementation("com.fasterxml.jackson.core", "jackson-databind", "2.10.0")
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin", "2.10.0")
    implementation("org.postgresql", "postgresql", "42.2.8")
    implementation("com.twitter", "bijection-avro_2.13", "0.9.7")
    implementation("com.github.javafaker", "javafaker", "0.16")
    implementation("org.apache.avro", "avro", "1.11.0")
}