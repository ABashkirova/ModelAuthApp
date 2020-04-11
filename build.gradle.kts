group = "xyz.sashenka"
version = "3.1"

apply {
    plugin("application")
}

application {

    mainClassName = "xyz.sashenka.modelauthapp.Main"
    applicationName = "modelauthapp"
}

plugins {
    kotlin("jvm") version "1.3.71"
    id("org.jetbrains.dokka") version "0.10.0"
    id("org.flywaydb.flyway") version "6.3.2"
    application
}

repositories {
    jcenter()
    maven {
        url = uri("https://kotlin.bintray.com/kotlinx")
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("junit:junit:4.12")
    // app:
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.2.1")
    implementation("com.h2database:h2:1.4.200")
    implementation("org.flywaydb:flyway-core:6.3.2")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")
    implementation("org.apache.logging.log4j:log4j-api:2.13.1")
    implementation("org.apache.logging.log4j:log4j-core:2.13.1")
}

tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}