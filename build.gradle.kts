import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val springBootVersion = "2.1.1.RELEASE"
    val kotlinVersion = "1.3.10"
    val nodePluginVersion = "1.2.0"

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion")
        classpath("com.moowork.gradle:gradle-node-plugin:$nodePluginVersion")
    }
}

allprojects {
    group = "uk.co.bartcode"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf("-Xjsr305=strict")
            }
        }
    }
}

plugins {
    base
}

dependencies {
    subprojects.forEach {
        archives(it)
    }
}