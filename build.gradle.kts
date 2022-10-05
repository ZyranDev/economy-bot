import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("application")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.lucko.me/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://repo.unnamed.team/repository/unnamed-public/")
}

application {
    mainClass.set("com.zittla.tickets.TicketsLauncherKt")
}

dependencies {
    api("org.xerial:sqlite-jdbc:3.39.3.0")
    api("org.spongepowered:configurate-yaml:4.1.2")
    api("net.cosmogrp:economy-api:0.0.2")
    api("org.mongojack:mongojack:4.7.1-SNAPSHOT")
    api("net.dv8tion:JDA:5.0.0-alpha.20") {
        exclude(module = "opus-java")
    }

    api("org.apache.logging.log4j:log4j-core:2.17.2")
    api("org.apache.logging.log4j:log4j-slf4j-impl:2.17.2")

    api("org.jline:jline-terminal-jansi:3.20.0")
    api("net.minecrell:terminalconsoleappender:1.3.0")

    api("com.google.inject:guice:5.1.0")

    api("me.fixeddev:commandflow-discord:0.5.0")


    api("net.kyori:adventure-api:4.11.0") {
        exclude(module = "adventure-bom")
        exclude(module = "checker-qual")
        exclude(module = "annotations")
    }
    api("net.kyori:adventure-text-serializer-plain:4.11.0") {
        exclude(module = "adventure-bom")
        exclude(module = "adventure-api")
    }
    api("net.kyori:ansi:1.0.0-SNAPSHOT")

    kotlin("stdlib")

    testImplementation(kotlin("test"))
}

tasks.shadowJar {
    transform(com.github.jengelman.gradle.plugins.shadow.transformers.Log4j2PluginsCacheFileTransformer::class.java)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
