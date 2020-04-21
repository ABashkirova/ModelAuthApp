import org.gradle.jvm.tasks.Jar

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

group = "xyz.sashenka"
version = "$aaaVersion"

plugins {
    kotlin("jvm") version "1.3.71"
    id("io.gitlab.arturbosch.detekt") version "1.7.4"
    id("org.jetbrains.dokka") version "0.10.0"
    id("org.flywaydb.flyway") version "6.3.2"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("com.github.dawnwords.jacoco.badge") version "0.2.0"
    id("org.gretty") version "3.0.2"
    war
    jacoco
    application
}

apply {
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

jacoco {
    toolVersion = "$jacocoVersion"
    reportsDir = file("$buildDir/reports/jacoco")
}

jacocoBadgeGenSetting {
    jacocoReportPath = "$buildDir/reports/jacoco/xml/jacocoTestReport.xml"
    readmePath = "$projectDir/README.md"
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

tasks {
    build {
        dependsOn(fatJar)
    }

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
}

application {
    mainClassName = "xyz.sashenka.modelauthapp.Main"
    applicationName = "modelauthapp"
}

repositories {
    jcenter()
    maven {
        url = uri("https://kotlin.bintray.com/kotlinx")
    }
}

dependencies {

    providedCompile("javax.servlet:javax.servlet-api:3.1.0")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("junit:junit:4.12")

    // app:
    implementation("org.jetbrains.kotlinx:kotlinx-cli:$kotlinxCliVersion")
    implementation("com.h2database:h2:$h2databaseVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.apache.logging.log4j:log4j-api-kotlin:$kotlinLog4j2Version")
    implementation("org.apache.logging.log4j:log4j-api:$log4j2Version")
    implementation("org.apache.logging.log4j:log4j-core:$log4j2Version")


    // test:
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")


}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Implementation-Title"] = "AAAJar"
        attributes["Implementation-Version"] = aaaVersion
        attributes["Main-Class"] = application.mainClassName
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

flyway {
    url = System.getenv("DBURL" + System.getenv("DBFILE"))
    user = System.getenv("DBLOGIN")
    password = System.getenv("DBPASS")
    baselineOnMigrate = true
    locations = arrayOf("filesystem: resources / db / migration")
}
