plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka") version "1.8.10"
    `java-library`
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}
tasks.dokkaJavadoc{
    outputDirectory.set(file("build/dokka/javadoc"))
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}