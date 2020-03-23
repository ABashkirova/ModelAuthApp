#!/usr/bin/env bash
rm -rf ./out
rm -rf ./lib
mkdir ./out
mkdir ./lib

cd ./out/
git clone https://github.com/Kotlin/kotlinx.cli.git
cd kotlinx.cli
./gradlew jvmJar
cp -R ./core/build/libs/kotlinx-cli-jvm-0.2.1-SNAPSHOT.jar ../../lib/kotlinx-cli.jar
cd ../..
rm -rf ./out/kotlinx.cli

kotlinc \
        -cp lib/kotlinx-cli.jar \
        src \
        -include-runtime \
        -d ./out/app.jar/