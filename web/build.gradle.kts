// web
plugins {
    id("org.gretty") version "3.0.2"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    id ("com.heroku.sdk.heroku-gradle") version "1.0.4"
    war
    kotlin("jvm")
    jacoco
}

gretty {
    contextPath = "/"
    logDir = "${projectDir}/logs"
    loggingLevel = "TRACE"
}

heroku {
    includes = listOf("./web/build/server/webapp-runner*.jar", "./web/build/libs/*.war")
    includeBuildDir = true
    jdkVersion        = "8"
}

val staging: Configuration by configurations.creating
val kotlinLog4j2Version: String by project
val log4j2Version: String by project
dependencies {
    // heroku app runner
    staging("com.heroku:webapp-runner-main:9.0.31.0")
    providedCompile("javax.servlet:javax.servlet-api:3.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.inject:guice:4.2.3")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:$kotlinLog4j2Version")
    implementation("org.apache.logging.log4j:log4j-api:$log4j2Version")
    implementation("org.apache.logging.log4j:log4j-core:$log4j2Version")
}

tasks {
    val copyToLib by registering(Copy::class) {
        into("$buildDir/server")
        from(staging) {
            include("webapp-runner*")
        }
    }

    register("stage") {
        dependsOn(war, copyToLib)
    }
}
