#!/usr/bin/env bash
sep=":"

if [[ "$OSTYPE" == "cygwin" ]]; then
        sep=";"
elif [[ "$OSTYPE" == "msys" ]]; then
        sep=";"
elif [[ "$OSTYPE" == "win32" ]]; then
        sep=";"
fi

java -classpath "lib/kotlinx-cli.jar${sep}out/app.jar" xyz.sashenka.modelauthapp.MainKt "$@"
