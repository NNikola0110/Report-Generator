plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":specifikacija"))
    runtimeOnly(project(":pdf"))
    runtimeOnly(project(":txt"))
    runtimeOnly(project(":csv"))
    runtimeOnly(project(":exel"))
}

application {
    mainClass.set("app.MainKt")
}
tasks.shadowJar {
    archiveClassifier.set("all")
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
    mergeServiceFiles()
}
tasks.build{
    dependsOn(tasks.shadowJar)
}
tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}