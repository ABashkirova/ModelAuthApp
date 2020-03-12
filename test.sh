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
#T1.2
testcase "-h" 1 "#T1.2: R1.8 Печать справки"
#T1.3
testcase "-bla" 0 "#T1.3: R1.8 Печать справки"

## Аунтентификация
##T2.1
testcase "-login sasha -pass 123" 0 "T2.1: R1.9, R1.8 Успешная Аунтентификация U1"
##T2.2
testcase "-pass 123 -login sasha" 0 "T2.2: R1.9, R1.10 Успешная Аунтентификация U1"
##T2.3
testcase "-login SASHA -pass 123" 2 "T2.3: R1.9 Неверный формат, логин прописными"
##T2.4
testcase "-login SA12 -pass 123" 2 "T2.4: R1.9 Неверный формат, логин c цифрами"
##T2.5
testcase "-login   -pass pass" 2 "T2.5: R1.9 Неверный формат, логин пустой"
##T2.6
testcase "-login abashkirova -pass pass" 2 "T2.6: R1.9 Неверный формат, логин больше 10 символов"
##T2.7
testcase "-login vasya -pass 123" 3 "T2.7: R1.9 Невеизвестный логин"
##T2.8
testcase "-login admin -pass 1234" 4 "T2.8: R1.9 Неверный пароль"
##T2.9
testcase "-login admin -pass  " 4 "T2.9: R1.9 Неверный пароль, пустой"
##T2.10
testcase "-login admin -pass qwerty" 0 "T2.10: R1.9 Успешная Аунтентификация U2"
##T2.11
testcase "-login q -pass @#$%^&*!" 0 "T2.11: R1.9 Успешная Аунтентификация U3"
##T2.12
testcase "-login aleksandra -pass abc" 0 "T2.12: R1.9 Успешная Аунтентификация U4"
##T2.13
testcase "-h -login aleksandra -pass abc" 0 "T2.13: R1.9 R1.9 Аунтентификация при дополнительном вызове справки"
