// root

plugins {
    kotlin("jvm") version "1.3.71"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


allprojects {
    repositories {
        maven {
            url = uri("https://kotlin.bintray.com/kotlinx")
        }
    }

    dependencies {
        // all
        "implementation"("org.jetbrains.kotlinx:kotlinx-cli:$kotlinxCliVersion")
        "implementation"(platform("org.jetbrains.kotlin:kotlin-bom"))
        "implementation"("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        "implementation"("com.google.inject:guice:4.2.3")
        "implementation"("com.google.inject.extensions:guice-throwingproviders:4.2.0")

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
}

dependencies {
    // all
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("junit:junit:4.12")
}

