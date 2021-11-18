plugins {
    kotlin("jvm") version "1.5.21"
}

group = "com.github.galads"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
}