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

publishing {
    apply(plugin = "maven-publish")
    configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/Votuu/i18n")
                credentials {
                    username = property("git.user") as String?
                    password = property("git.token") as String?
                }
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}