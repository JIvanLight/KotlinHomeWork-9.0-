import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    jacoco
}

group = "ru.netology"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/junit/junit
    testImplementation("junit:junit:4.13.2")
}

//tasks.test {
//    useJUnitPlatform()
//}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}