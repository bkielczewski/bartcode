import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    val springBootVersion = "2.0.0.M7"

    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    }
}

plugins {
    val kotlinVersion = "1.2.0"

    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.3.RELEASE"
}

apply {
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("kotlin-noarg")
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
}

noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.Embeddable")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
}

dependencies {
    val logstashLogbackEncoderVersion = "4.11"
    val flexmarkVersion = "0.27.0"
    val springSocialFacebookVersion = "2.0.3.RELEASE"

    compile("org.springframework.boot:spring-boot-starter-data-rest")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.springframework.boot:spring-boot-starter-security")
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework.boot:spring-boot-configuration-processor")
    compile("org.springframework.social:spring-social-facebook:$springSocialFacebookVersion")
    compile("com.vladsch.flexmark:flexmark:$flexmarkVersion")
    compile("org.jetbrains.kotlin:kotlin-stdlib")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    runtime("com.h2database:h2")
    runtime("net.logstash.logback:logstash-logback-encoder:$logstashLogbackEncoderVersion")
    testCompile("org.springframework.boot:spring-boot-starter-test")
}
