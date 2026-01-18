plugins {
    kotlin("jvm") version "2.2.21"
    `maven-publish`
}

group = "com.hytalist"
version = "1.0.0"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.hypixel.net/repository/Hytale/") }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    // standalone
}

tasks.jar {
    archiveBaseName.set("hyvote-api")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.hytalist"
            artifactId = "hyvote-api"
            version = project.version.toString()

            from(components["java"])

            pom {
                name.set("HyVote API")
                description.set("API for HyVote - Votifier implementation for Hytale")
                url.set("https://github.com/hytalist/hyvote-api")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
            }
        }
    }
}
