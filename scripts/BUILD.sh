#!/usr/bin/env bash
rm -rf ./build

./gradlew build
mv ./build/libs/*-fat-*.jar ./AAA.jar