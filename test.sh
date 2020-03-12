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

## Авторизация
#
##T3.1
testcase "-login sasha -pass 123 -role READ -res A" 0 "T3.1: R1.3, R1.8, R1.9 Успешный доступ"
##T3.2
testcase "-login sasha -pass 123 -role DELETE -res A" 5 "T3.2: R1.8, R1.9 Неизвестная роль"
##T3.3
testcase "-login sasha -pass 123 -role WRITE -res A" 6 "T3.3: R1.8, R1.9 Нет доступа (ресурс есть)"
##T3.4
testcase "-login sasha -pass 123 -role WRITE -res a.b.c" 6 "T3.4: R1.8, R1.9 Нет доступа (ресур не найден)"
##T3.5
testcase "-login sasha -pass 123 -role READ -res A.B" 0 "T3.5: R1.6 Доступ к потомкам"
##T3.6
testcase "-login sasha -pass 123 -role READ -res A.B.C.D" 0 "T3.6: R1.6, R1.3, R1.8, R1.9 Доступ к потомкам"
##T3.7
testcase "-login admin -pass qwerty -role EXECUTE -res A.AA" 0 "T3.7: R1.3, R1.8, R1.9 Успешный доступ"
##T3.8
testcase "-login admin -pass qwerty -role EXECUTE -res A.B" 6 "T3.8: R1.3, R1.8, R1.9 Доступ к брату"
##T3.9
testcase "-login q -pass @#$%^&*! -role READ" 0 "T3.9: R1.1, R1.8, R1.9 Успешная аутентификация"
##T3.10
testcase "-login q -pass 1234 -role DELETE -res A.B" 4 "T3.10: R1.1, R1.8, R1.9 Неверный пароль"
##T3.11
testcase "-login q -pass @#$%^&*! -role READ -res A.AA.AAA" 0 "T3.11: R1.3 Успешный доступ"
##T3.12
testcase "-login q -pass @#$%^&*! -role READ -res A.AA" 6 "T3.12: R1.8, R1.9 Нет доступа (выше узла)"
##T3.13
testcase "-role -res -login sasha -pass 123" 0 "T3.13: R1.3, R1.10 - Успешный доступ, порядок аргументов"