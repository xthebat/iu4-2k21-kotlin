plugins {
    kotlin("jvm") version "1.5.21"
}

group = "ru.bmstu.iu4"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.github.pityka:fileutils_2.13:1.2.5")
}