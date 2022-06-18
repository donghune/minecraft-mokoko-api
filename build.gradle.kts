val kotlinVersion = "1.7.0"

plugins {
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.5.21"
    id("maven-publish")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("io.github.monun:kommand-api:2.12.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")

    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.3")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
}

val pluginName = rootProject.name.split('-').joinToString("") { it.capitalize() }

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/donghune/minecraft-mokoko-api")
            credentials {
                username = System.getenv()["GITHUB_ACTOR"]
                password = System.getenv()["GITHUB_TOKEN"]
            }
        }
    }
    publications {
        register<MavenPublication>("jar") {
            from(components["java"])
        }
    }
}