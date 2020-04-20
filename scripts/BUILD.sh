#!/usr/bin/env bash
./gradlew clean build
mv ./build/libs/*-fat-*.jar ./AAA.jar