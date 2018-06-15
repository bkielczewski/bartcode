plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.spring")
    id("org.jetbrains.kotlin.plugin.jpa")
}

val logstashLogbackEncoderVersion: String by project
val mockitoKotlinVersion: String by project
val springSocialFacebookVersion: String by project
val flexmarkVersion: String by project

dependencies {
    compile("com.vladsch.flexmark:flexmark:$flexmarkVersion")
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework.boot:spring-boot-starter-data-jpa")
    compile("org.springframework.boot:spring-boot-starter-data-rest")
    compile("org.springframework.boot:spring-boot-starter-security")
    compile("org.springframework.boot:spring-boot-configuration-processor")
    compile("org.springframework.social:spring-social-facebook:$springSocialFacebookVersion")
    runtime("com.fasterxml.jackson.module:jackson-module-kotlin")
    runtime("com.h2database:h2")
    runtime("net.logstash.logback:logstash-logback-encoder:$logstashLogbackEncoderVersion")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("com.nhaarman:mockito-kotlin:$mockitoKotlinVersion")
}
