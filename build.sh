#!/usr/bin/env bash
kotlinc ./src/controller/ArgHandler.kt ./src/model/User.kt ./src/Main.kt -include-runtime -d app.jar