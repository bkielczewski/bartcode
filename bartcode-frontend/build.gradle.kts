import com.moowork.gradle.node.npm.NpmInstallTask
import com.moowork.gradle.node.npm.NpmTask
import com.moowork.gradle.node.task.NodeTask

plugins {
  id("java")
  id("com.moowork.node")
}

node {
  version = "10.4.1"
  download = true
}

task<NpmTask>("npmBuild") {
  dependsOn(NpmInstallTask.NAME)
  setArgs(listOf("run-script", "build"))
}

task<Copy>("dist") {
  from("dist")
  into("build/resources/main/public")
}

(tasks.getByName("processResources") as ProcessResources).apply {
  dependsOn("npmBuild", "dist")
}

