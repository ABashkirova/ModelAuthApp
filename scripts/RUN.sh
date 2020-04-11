#!/usr/bin/env bash

java -Dlog4j.configurationFile=src/resources/log4j2.xml \
    -classpath "lib/kotlinx-cli.jar:lib/h2-1.4.200.jar:lib/log4j-api-2.13.1.jar:lib/log4j-api-kotlin-1.0.0.jar:lib/log4j-core-2.13.1.jar:lib/flyway-core-6.3.2.jar:src/main/resources:out/app.jar" \
    xyz.sashenka.modelauthapp.MainKt "$@"
