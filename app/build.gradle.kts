// app
plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt") version "1.7.4"
    id("org.jetbrains.dokka") version "0.10.0"
    id("org.flywaydb.flyway") version "6.3.2"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("com.github.dawnwords.jacoco.badge") version "0.2.0"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    jacoco
    application
}

val aaaVersion: String by project
// Gradle plugins
val kotlinVersion: String by project
val jacocoVersion: String by project
val detektVersion: String by project
val spekVersion: String by project
val dokkaVersion: String by project

// Project dependencies
val flywayVersion: String by project
val kotlinxCliVersion: String by project
val h2databaseVersion: String by project
val kotlinLog4j2Version: String by project
val log4j2Version: String by project
val mockkVersion: String by project

dependencies {
    // app:
    implementation("org.jetbrains.kotlinx:kotlinx-cli:$kotlinxCliVersion")
    implementation("com.h2database:h2:$h2databaseVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    // test:
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
}

tasks {
    test {
        useJUnitPlatform {
            includeEngines("spek2")
        }
        testLogging {
            events("passed", "skipped", "failed")
        }
        reports.html.isEnabled = true
        reports.html.destination = file("$buildDir/reports/test")
        reports.junitXml.isEnabled = true
        reports.junitXml.destination = file("$buildDir/reports/test")
    }

    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/reports/javadoc"
    }

    jacocoTestReport {
        reports {
            xml.isEnabled = true
            xml.destination = file("$buildDir/reports/jacoco/xml/jacocoTestReport.xml")
            html.isEnabled = true
            html.destination = file("$buildDir/reports/jacoco/html")
        }
    }

    jacocoTestCoverageVerification {
        violationRules {
            rule { limit { minimum = BigDecimal.valueOf(0.2) } }
        }
    }

    shadowJar {
        archiveVersion.set(version)
    }
}

detekt {
    input = files("src/main/kotlin")
    config = files("$rootDir/detekt-config.yml")
    buildUponDefaultConfig = false
    ignoreFailures = true
    reports {
        xml {
            enabled = true
            destination = file("$buildDir/reports/detekt/report_detekt.xml")
        }
        html {
            enabled = true
            destination = file("$buildDir/reports/detekt/report_detekt.html")
        }
    }
}

flyway {
    url = System.getenv("DBURL" + System.getenv("DBFILE"))
    user = System.getenv("DBLOGIN")
    password = System.getenv("DBPASS")
    baselineOnMigrate = true
    locations = arrayOf("filesystem: resources / db / migration")
}

application {
    mainClassName = "xyz.sashenka.modelauthapp.Main"
    applicationName = "app"
}

jacoco {
    toolVersion = "$jacocoVersion"
    reportsDir = file("$buildDir/reports/jacoco")
}

jacocoBadgeGenSetting {
    jacocoReportPath = "$buildDir/reports/jacoco/xml/jacocoTestReport.xml"
    readmePath = "$projectDir/README.md"
}
