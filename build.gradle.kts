// root
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allprojects {
    group = "xyz.sashenka.webapplication"
    version = "7.0"

    repositories {
        jcenter()
        maven {
            url = uri("https://kotlin.bintray.com/kotlinx")
        }
    }
}

plugins {
    base
    kotlin("jvm") version "1.3.72" apply false
}

subprojects {
    val kotlinLog4j2Version: String by project
    val log4j2Version: String by project
    apply(plugin = "java")
    apply(plugin = "application")

    dependencies {
        // all
        "implementation"(platform("org.jetbrains.kotlin:kotlin-bom"))
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        "implementation"("com.google.inject:guice:4.2.3")

        "implementation"("org.apache.logging.log4j:log4j-api-kotlin:$kotlinLog4j2Version")
        "implementation"("org.apache.logging.log4j:log4j-api:$log4j2Version")
        "implementation"("org.apache.logging.log4j:log4j-core:$log4j2Version")

        "testImplementation"("org.jetbrains.kotlin:kotlin-test")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit")
        "testImplementation"("junit:junit:4.12")

        "implementation"("com.google.code.gson:gson:2.8.6")

        subprojects.forEach {
            archives(it)
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        println("Configuring $name in project ${project.name}...")
        kotlinOptions {
            suppressWarnings = true
        }
    }
}
