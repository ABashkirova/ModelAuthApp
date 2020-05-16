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
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.72"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "application")
    apply(plugin = "kotlin-jpa")
    repositories {
        maven {
            url = uri("https://kotlin.bintray.com/kotlinx")
        }
    }

    val kotlinxCliVersion: String by project
    val kotlinLog4j2Version: String by project
    val log4j2Version: String by project
    val guiceVersion: String by project
    val hibernateVersion: String by project

    dependencies {
        // all
        "implementation"("org.jetbrains.kotlinx:kotlinx-cli:$kotlinxCliVersion")
        "implementation"(platform("org.jetbrains.kotlin:kotlin-bom"))
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        "implementation"("com.google.inject:guice:$guiceVersion")
        "implementation"("com.google.inject.extensions:guice-throwingproviders:$guiceVersion")
        "implementation"("com.google.inject.extensions:guice-persist:$guiceVersion")

        "implementation"("com.google.code.gson:gson:2.8.6")

        "implementation"("org.hibernate:hibernate-entitymanager:$hibernateVersion")
        "implementation"("org.hibernate.javax.persistence:hibernate-jpa-2.1-api:1.0.2.Final")
        "implementation"("org.hibernate:hibernate-core:$hibernateVersion")

        "implementation"("org.apache.logging.log4j:log4j-api-kotlin:$kotlinLog4j2Version")
        "implementation"("org.apache.logging.log4j:log4j-api:$log4j2Version")
        "implementation"("org.apache.logging.log4j:log4j-core:$log4j2Version")

        "testImplementation"("org.jetbrains.kotlin:kotlin-test")
        "testImplementation"("org.jetbrains.kotlin:kotlin-test-junit")
        "testImplementation"("junit:junit:4.12")


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
