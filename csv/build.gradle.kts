plugins {
    kotlin("jvm") version "1.9.23"
}

group = "org.example"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":specifikacija"))
    implementation("org.apache.commons:commons-csv:1.12.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}