import com.moowork.gradle.node.npm.NpmInstallTask
import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask

plugins {
    id("com.moowork.node") version "1.2.0"
}

node {
    version = "8.7.0"
    download = true
}

task<NpmTask>("npmBuild") {
    dependsOn(NpmInstallTask.NAME)
    setArgs(listOf("run-script", "build"))
}

task("build").dependsOn("npmBuild")
