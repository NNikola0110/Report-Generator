plugins {
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":specifikacija" ))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}