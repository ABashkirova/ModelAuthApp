#!/usr/bin/env bash
# from rootProject
./gradlew clean test \
            check \
            detektMain \
            ktlintCheck \
            jacocoTestReport \
            jacocoTestCoverageVerification \
            generateJacocoBadge \
            dokka

