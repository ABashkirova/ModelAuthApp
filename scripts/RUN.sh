#!/usr/bin/env bash

java -Dlog4j.configurationFile=src/main/resources/log4j2.xml \
    -classpath "src/main/kotlin:src:src/main/resources:AAA.jar" \
    xyz.sashenka.modelauthapp.Main "$@"
