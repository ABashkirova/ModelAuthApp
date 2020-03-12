#!/usr/bin/env bash

function testcase {
    ACT=$1;
    EXPECTED_CODE=$2;
    PURPOSE=$3

    echo -e "$PURPOSE"
    echo "java -jar app.jar $ACT"

    ./run.sh $ACT
    RES=$?
    if [ $RES -eq $EXPECTED_CODE ]; then
        echo -e "Test passed"
    else
        echo -e "Test failed. Expected $EXPECTED_CODE. Actual $RES"
    fi
    echo
}

# Справка
#T1.1
testcase "" 1 "#T1.1: R1.8 Печать справки"
