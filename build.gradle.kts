allprojects {
    group = "uk.co.bartcode"
    version = "1.0"

    repositories {
        mavenCentral()
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

task<Wrapper>("wrapper") {
    gradleVersion = "4.2"
}