plugins {
    id("java")
    id("maven-publish")
}

group = "de.votuu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // maven central

    implementation("org.mongodb:mongodb-driver-sync:4.7.1")
    implementation("com.google.code.gson:gson:2.9.0")
}