# **План разработки требований**

## Этап 0: Подготовительный

1. Погуглить работу с регулярными выражениями для понимания (R1.1, R1.2)
2. Погуглить про безопасное хранение паролей
    - хеш
    - соль
    - типы шифрования
3. Погуглить структуру дерева в kotlin (R1.4)
4. Создать репозиторий открытый в git 
5. Создать консольное приложение проект в IDEA (R1.8)
6. Написать bash скрипт сборки приложения `build.sh` (R1.12)
7. Написать bash скрипт запуск приложения `run.sh` (R1.12)

## Этап 1: Тестирование (R1.12)
1. Описать тестовые входные данные
    1. Описать пользователей
        - u1: login: sasha, pass: 123
        - u2: login: admin, pass: qwerty
        - u3: login: q, pass: @#$%^&*!
        - u4: login: aleksandra, pass: abc (без ролей)
    2. Описать ресурсы:
        - r1: res: A, role: read, user: sasha
        - r2: res: A.AA, role: write, user: sasha
        - r3: res: A.AA.AAA, role: execute, user: sasha
        - r4: res: B, role: execute, user: admin
        - r5: res: A.B, role: write, user: admin
        - r6: res: A.B, role: write, user: sasha
        - r7: res: A.B.C, role: read, user: admin
        - r8: res: A.B.C, role: write, user: q
        - r9: res: A.B, role: execute, user: q
        - r10: res: B, role: read, user: q
        - r11: res: A.AA.AAA, role: read, user: q
        - r12: res: A, role: execute, user: q
        - r13: res: A, role: write, user: admin
        - r14: res: A.AA, role: execute, user: admin
        - r15: res: B, role: write, user: sasha
        - r16: res: A.B, role: execute, user: sasha
        - r17: res: A.B.C, role: execute, user: sasha
2. Записать тесты в `test.sh`
    1. Справка (R1.8):
    
    | # | Act | Result | Purpose |
    |:---|---|:---:|:---|
    |T1.1 |`app.jar`|1, печать справки| R1.8 |
    |T1.2 |`app.jar -h`|1, печать справки| R1.8 |
    |T1.3 |`app.jar -bla` | 0, печать справки| особенности реализации библиотеки |
    
    2. Аунтентификация (R1.1)
        
    | # | Act | Result | Purpose |
    |:---|---|:---:|:---|
    |T2.1 |`app.jar -login sasha -pass 123` | 0 | R1.9, R1.8 | 
    |T2.2 |`app.jar -pass 123 -login sasha` | 0 | R1.9, R1.10 |     
    |T2.3 |`app.jar -login SASHA -pass 123` | 2 | R1.9 Неверный формат, логин прописными |
    |T2.4 |`app.jar -login SASHA -pass 123` | 2 | R1.9 Неверный формат, логин прописными |
    |T2.5 |`app.jar -login "" -pass pass` | 2 | R1.9 Неверный формат, логин пустой |
    |T2.6 |`app.jar -login abashkirova -pass pass` | 2 | R1.9 Неверный формат, логин больше 10 символов |
    |T2.7 |`app.jar -login vasya -pass 123` | 3 | R1.9 Невеизвестный логин | 
    |T2.8 |`app.jar -login admin -pass 1234` | 4 | R1.9 Неверный пароль | 
    |T2.9 |`app.jar -login admin -pass ""` | 4 | R1.9 Неверный пароль, пустой | 
    |T2.10 |`app.jar -login admin -pass qwerty` | 0 | R1.9 | 
    |T2.11 |`app.jar -login q -pass @#$%^&*!` | 0 | R1.9 | 
    |T2.12 |`app.jar -login aleksandra -pass abc` | 0 | R1.9 |
    |T2.13 |`app.jar -h -login aleksandra -pass abc` | 0 | R1.9 Аунтентификация |
    
    3. Авторизация (R1.3)
    
    | # | Act | Result | Purpose |
    |:---|---|:---:|:---|
    |T3.1 |`app.jar -login sasha -pass 123 -role READ -res A` | 0 | R1.3, R1.8, R1.9 Успешный доступ|
    |T3.2 |`app.jar -login sasha -pass 123 -role DELETE -res A` | 5 | R1.8, R1.9 Неизвестная роль | 
    |T3.3 |`app.jar -login sasha -pass 123 -role WRITE -res A` | 6 | R1.8, R1.9 Нет доступа (ресурс есть) | 
    |T3.4 |`app.jar -login sasha -pass 123 -role WRITE -res a.b.c` | 6 | R1.8, R1.9 Нет доступа (ресур не найден) | 
    |T3.5 |`app.jar -login sasha -pass 123 -role READ -res A.B` | 0 | R1.6 Доступ к потомкам |
    |T3.6 |`app.jar -login sasha -pass 123 -role READ -res A.B.C.D` | 0 |R1.6, R1.3, R1.8, R1.9 Доступ к потомкам | 
    |T3.7 |`app.jar -login admin -pass qwerty -role EXECUTE -res A.AA` | 0 | R1.3, R1.8, R1.9 Успешный доступ |
    |T3.8 |`app.jar -login admin -pass qwerty -role EXECUTE -res A.B` | 6 | R1.3, R1.8, R1.9 Доступ к брату |
    |T3.9 |`app.jar -login q -pass @#$%^&*! -role READ` | 0 | R1.1, R1.8, R1.9 Успешная аутентификация |
    |T3.10 |`app.jar -login q -pass 1234 -role DELETE -res A.B` | 4 | R1.1, R1.8, R1.9 Неверный пароль| 
    |T3.11 |`app.jar -login q -pass @#$%^&*! -role READ -res A.AA.AAA` | 0 | R1.3 Успешный доступ |
    |T3.12 |`app.jar -login q -pass @#$%^&*! -role READ -res A.AA` | 6 | R1.8, R1.9 Нет доступа (выше узла) |
    |T3.13 |`app.jar -role -res -login sasha -pass 123` | 0 | R1.3, R1.10 |
    
    4. Аккаунтинг (R1.7)
    
    | # | Act | Result | Purpose |
    |:---|---|:---:|:---|
    |T4.1 |`app.jar -login sasha -pass 123 -role READ -res A -ds 2000-01-15 -de 2000-02-15 -vol 10` | 0 |R1.7,R1.8,R1.9|
    |T4.2 |`app.jar -login sasha -pass 123 -role READ -res A -ds 2000-01-15 -de 2000-02-15 -vol ten` | 7 |R1.9 — Некорректная активность, не приводится vol|
    |T4.3 |`app.jar -login sasha -pass 123 -role READ -res A -ds 2000-01-15 -de 2000-02-15 -vol -1` | 7 |R1.9 — Некорректная активность, не приводится vol<0|
    |T4.4 |`app.jar -login sasha -pass 123 -role READ -res A -ds 2000-01-00 -de 2000-02-15 -vol 10` | 7 |R1.9 — Некорректная активность, не приводится дата ds|
    |T4.5 |`app.jar -login sasha -pass 123 -role READ -res A -ds 2000-01-15 -de 2000-02-32 -vol 10` | 7 |R1.9 — Некорректная активность, не приводится дата de|
    |T4.6 |`app.jar -login sasha -pass 123 -role READ -res A -ds 2000-02-15 -de 2000-01-15 -vol 10` | 7 |R1.9 — Некорректная активность, ds > ds|
    |T4.7 |`app.jar -login q -pass @#$%^&*! -role WRITE -res A.B.C -ds 2000-01-15 -de 2000-02-15 -vol 10` | 0 |R1.7,R1.8,R1.9|
    |T4.8 |`app.jar -login admin -pass qwerty -role execute -res A.AA -ds 2000-01-15 -de 2000-02-15 -vol 10` | 0 |R1.7,R1.8,R1.9|
    |T4.9 |`app.jar -login q -pass @#$%^&*! -role WRITE -res A.B.C -ds 2000-01-15 -vol 10` | 0 | R1.3 Успешная авторизация |
    |T4.10 |`app.jar -login q -pass @#$%^&*! -role WRITE -res A.B.C -de 2000-02-15 -vol 10` | 0 | R1.3 Успешная авторизация |
    |T4.11 |`app.jar -login q -pass @#$%^&*! -role WRITE -res A.B.C -ds 2000-01-15 -de 2000-02-15` | 0 | R1.3 Успешная авторизация |
    |T4.12 |`app.jar -login q -pass @#$%^&*! -role DELETE -res A.B.C -ds 2000-01-15 -de 2000-02-15` | 0 | R1.1 Успешная аутентификация |
    |T4.13 |`app.jar -login q -pass !@#$% -role WRITE -res A.B.C -ds 2000-01-15 -de 2000-02-15` | 2 | R1.1 Неверный пароль |
    |T4.14 |`app.jar -res A.B.C -ds 2000-01-15 -vol 10 -login q -pass @#$%^&*! -role WRITE ` | 0 | R1.10 Порядок параметров |  
    
## Этап 1: Работа с консольными параметрами 
1. Написать тесты на падачу параметров командной строки (R1.12)
    1. Взять тестовые входные данные.
        1. Печать справки: 
            - передан параметр справки `app.jar -h` — 1
            - запуск без параметров: `app.jar` -  1
            - передан неизвестный параметр: `-photo` — 1
        2. Аутентификация: 
        ```app.jar -login sasha -pass qwery``` — 0 
        3. Авторизация: 
        ```app.jar -login sasha -pass qwery -res A.B -role READ — 0```
        4. Аккаунтинг: 
        ```app.jar -login sasha -pass qwery -res A.B -role READ -ds  2000-01-01 -de 2000-01-01 -vol 19``` — 0 
        5. Параметры в разном порядке: 
        ```app.jar -vol 19 -de 2000-01-01 -res A.B -ds 2000-01-01 -role READ -pass qwery -login sasha``` — 0 
    2. Написать `bash` скрипт `test.sh`, запускающий программу с тестовыми данными и проверяющий код работы программы с ожидаемым.
    3. Опубликовать изменения в git 
2. Написать `main`, считывающий параметры (R1.8):
    - печать справки (R1.8.1, R1.8.2, R1.8.6)
    - обработка в любом порядке (R1.10)
    - возвращаение статуса 0 при правильных параметрах
    - возвращение статуса 1 при вызове справки
3. Протестировать реализацию автотестами п.1 (R1.12)
4. Обновить автотесты, если требуется (R1.12)
5. Опубликовать изменения в git

## Этап 2: Аутентификация пользователя
1. Написать тесты на авторизацию (R1.12)
    1. Взять тестовые данные
        - Успешная авторизация: 
        ```app.jar -login user -pass mypassword``` — 0 
        - Неверный формат логина(цывры):
        ```app.jar -login 666user666  -pass newpassword``` — 2
        - Неверный формат логина(цифры, прописные): 
        ```app.jar -login uSeR2000 -pass pass500p``` — 2
        - Неверный формат логина(количество >10): 
        ```app.jar -login useranduser -pass pass500p``` — 2
        - Неверный формат логина(пустой): 
        ```app.jar -login -pass pass500p``` — 2
        - Невеизвестный логин: 
        ```app.jar -login useradmin password``` — 3
        - Неверный пароль: 
        ```app.jar -loginuser -pass password``` — 4
        - Неверный пароль(пустой): 
        ```app.jar -login -pass ``` — 4
    2. Дописать bash скрипт `test.sh` с тестами 
    3. Опубликовать изменения в git 
2. Создать `data class User` с логином и паролем (R1.1)
3. Свалидировать логина на формат `[a-z]{1-10}` (код ошибки 2 R1.9, R1.8.3)
4. Создать класс `Autentification` для аутентификации пользователя по логину и паролю (R1.1)
    1. Поиск по логину пользователя (код 3, если не найден (R1.9))
    2. Получение хеша по паролю
    3. Сравнение хешей (0 — успех, 4 — не успех (R1.9))
5. Протестировать реализацию автотестами п.1 (R1.12)
6. Обновить автотесты, если требуется (R1.12)
7. Опубликовать изменения в git

## Этап 3: Авторизация
1. Написать тесты на вторизацию (R1.12)
    1. Взять тестовые данные
        1. Авторизация к ресурсу по роли:
            - Успешная авторизация по логину, паролю и роли `READ`: 
            ```app.jar -login user -pass mypassword -role read -res A.B.D``` — 0
            - Успешная авторизация по логину, паролю и роли `WRITE`: 
            ```app.jar -login author -pass mypass -role write -res A.B.C``` — 0
            - Успешная авторизация по логину, паролю и роли `EXECUTE`: 
            ```app.jar -login admin -pass admin -role execute -res A.B.C``` — 0
            - Повторить неагитивные тесты на коды 2-3-4 для роли read, write и execute соответственно
        2. Негативные тесты
            - Неизвестная роль: 
            ```app.jar -login admin -pass admin -role admin -res A.B.C``` — 5
            - Неизвестная роль - пустая: 
            ```app.jar -login admin -pass admin -role -res A.B.C``` — 5
            - Нет доступа: 
            ```app.jar -login user mypassword execute A.B.C``` — 6
            - Нет доступа(ресурс пустой): 
            ```app.jar -login user -pass mypassword -role execute -res``` — 6
    2. Дописать `bash` скрипт `test.sh` с тестами 
    3. Опубликовать изменения в git
2. Создать `data class Resource` (R1.3, R1.6) с полем `path: String`
3. Создать перечисление `enum Role` (R1.3,R1.5)
4. Добавить структуру со связью между ресурсом, пользователем и ролью `data class UsersResources`. Поля: 
    - `user: User`
    - `resource: Resource`
    - `role: Role`
5. Создать класс `Authorization` (R1.3)
    - поиск ресурса
    - проверка доступа к ресурсу (R1.3, R1.4, R1.6), определяя уровень доступа по дереву, возврат кода 6, если доступа нет (R1.9)
6. Дописать чтение параметрами `res`, `role` (R1.8.4)
    - Проверка формата ресурса (R1.3)
    - Проверку роли по списку (R1.5, R1.8.4)
7. Протестировать реализацию автотестами п.1 (R1.12)
8. Обновить автотесты, если требуется (R1.12)
9. Опубликовать изменения в git

## Этап 4: Аккаунтинг
1. Написать тесты на вторизацию (R1.12)
    1. Взять тестовые данные
        - Успешно, верные даты и объем: 
        ```app.jar -login user -pass mypassword -role read -res A.B.D -ds 2000-12-11 -de 2000-12-12 -vol 7``` — 0
        - Некорректная активность, не приводятся ds: 
        ```app.jar -login user -pass mypassword  -role read -res A.B.D -ds 2000-20-101 -de 2000-12-12 -vol 7``` — 7
        - Некорректная активность, не приводятся de: 
        ```app.jar -login user -pass mypassword  -role read -res A.B.D -ds 2000-12-10 -de 2000-100-102 -vol 7``` — 7
        - Некорректная активность, не приводятся vol: 
        ```app.jar -login user -pass mypassword  -role read -res A.B.D -de 2000-12-10 -ds 2000-10-12 -vol seven``` — 7
        - Некорректная активность, не приводятся vol(пустое значение): 
        ```app.jar -login user -pass mypassword  -role read -res A.B.D -de 2000-12-10 -ds 2000-10-12  -vol``` — 7
        - Некорректная активность, не приводятся de, ds vol(пустое значение): 
        ```app.jar -login user -pass mypassword  -role read -res A.B.D -ds -de -vol ``` — 7
    2. Дописать bash скрипт `test.sh` с тестами 
    3. Опубликовать изменения в git
2. Создать хранилку сессии пользователя (R1.7)
    1. Записывать подключение (`ds de`)
    2. Сохранять заправшиваемый ресурс
    3. Сохранять запрашиваемый объем
3. Дописать чтение параметрами `ds`, `de`, `vol` (R1.8.5)
    - Проверка формата дат
    - Проверка последовательности дат `ds` раньше `de`
    - Проверка формата объема `int`
    - возврат кода 7, в случае ошибки (R1.9)
4. Протестировать реализацию автотестами (R1.12)
5. Обновить автотесты, если требуется (R1.12)
6. Опубликовать изменения в git

## Этап 5: Сборка требований
1. Написать процесс AAA (R1.11)
    1. Сначала аутентификацию (два параметра и более)
    2. Если успешно, переходим к авторизации (четыре параметра и более)
    3. Если успешно, переходим к аккаунтингу (семь параметров)
2. Запустить автотесты
3. Опубликовать изменения в git
4. Обновить readme 
