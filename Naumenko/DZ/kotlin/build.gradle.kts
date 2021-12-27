import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "me.aleksandr"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven("https://repo.kotlin.link")
}

dependencies {
    val KOTLIN_DL_VERSION = "0.3.0"
    val GPU_VERSION = "2.17.0"

    implementation ("org.apache.logging.log4j:log4j-api:${GPU_VERSION}")
    implementation ("org.apache.logging.log4j:log4j-core:${GPU_VERSION}")
    implementation ("org.apache.logging.log4j:log4j-slf4j-impl:${GPU_VERSION}")

    implementation("org.jetbrains.kotlinx:kotlin-deeplearning-api:${KOTLIN_DL_VERSION}")
    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-onnx:${KOTLIN_DL_VERSION}")
    implementation ("org.jetbrains.kotlinx:kotlin-deeplearning-visualization:${KOTLIN_DL_VERSION}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.0.6")
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