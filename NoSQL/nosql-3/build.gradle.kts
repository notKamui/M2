plugins {
    java
}

group = "fr.uge"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.mongodb:mongodb-driver-sync:4.8.0")
    implementation("org.slf4j:slf4j-api:2.0.5")
}