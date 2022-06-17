plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.5.21"
    id("maven-publish")
}

apply(plugin = "com.github.johnrengelman.shadow")

val kotlinVersion = "1.7.0"
group = "io.github.donghune"

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.github.johnrengelman:shadow:7.1.2")
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

val shade = configurations.create("shade")
shade.extendsFrom(configurations.implementation.get())

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveFileName.set("${project.name}-${project.property("version")}.jar")
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://maven.enginehub.org/repo/")
    maven("https://jitpack.io")
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")

    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("io.github.monun:kommand-api:2.12.0")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.0-SNAPSHOT")

    compileOnly("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core:1.3.3")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
}

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