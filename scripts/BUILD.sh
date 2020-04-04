#!/usr/bin/env bash
rm -rf ./out
mkdir ./out

kotlinc \
        -cp "lib/kotlinx-cli.jar:lib/h2-1.4.200.jar:lib/log4j-api-2.13.1.jar:lib/log4j-api-kotlin-1.0.0.jar:lib/log4j-core-2.13.1.jar" \
        src \
        -include-runtime \
        -d ./out/app.jar/