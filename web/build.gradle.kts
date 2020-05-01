// web
plugins {
    kotlin("jvm")
    id("org.gretty") version "3.0.2"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    id("com.heroku.sdk.heroku-gradle") version "1.0.4"
//    war
    jacoco
    application
}

apply {
    application
}
application {
    mainClassName = "xyz.sashenka.webapplication.JettyServer"
    applicationName = "app"
}
gretty {
    contextPath = "/"
    logDir = "${projectDir}/logs"
    loggingLevel = "TRACE"
}

val staging: Configuration by configurations.creating
val kotlinLog4j2Version: String by project
val log4j2Version: String by project

heroku {
    includes = listOf("./web/build/libs/web-all.jar")
    includeBuildDir = true
    jdkVersion = "8"
}

dependencies {
    // heroku app runner
    staging("com.heroku:webapp-runner-main:9.0.31.0")
    compile("org.eclipse.jetty:jetty-server:9.4.25.v20191220")
    compile("org.eclipse.jetty:jetty-servlet:9.4.25.v20191220")
    compile("org.eclipse.jetty:jetty-webapp:9.4.25.v20191220")
    compile("org.eclipse.jetty:jetty-annotations:9.4.25.v20191220")
    compile("org.eclipse.jetty:apache-jsp:9.4.25.v20191220")
    compile("javax.servlet:javax.servlet-api:3.1.0")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.google.inject:guice:4.2.3")
    implementation("com.google.inject.extensions:guice-servlet:4.2.3")
    implementation("com.google.code.gson:gson:2.8.6")

    implementation("org.apache.logging.log4j:log4j-api-kotlin:$kotlinLog4j2Version")
    implementation("org.apache.logging.log4j:log4j-api:$log4j2Version")
    implementation("org.apache.logging.log4j:log4j-core:$log4j2Version")
}

tasks {
    val copyToLib by registering(Copy::class) {
        into("${rootProject.buildDir}/libs")
        from("$buildDir/libs")
    }

    copyToLib {
        dependsOn(shadowJar)
    }

    register("stage") {
        dependsOn(clean, shadowJar)
    }
}
