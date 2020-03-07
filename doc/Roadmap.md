# **План разработки требований**

## Этап 0: Подготовительный

1. Создать репозиторий открытый в git 
2. Создать консольное приложение с функцией main в IDEA (R1.8)
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
    
    
    
## Этап 2: Работа с консольными параметрами 
1. Написать `main` c параметрами командной строки (R1.8)
2. Написать фукнцию, проверяющую наличие аргументов `argsIsNotEmpty(args: Array<String>)`
3. Создать метод печати справки `printHelp()` и `System.exit(1)` (R1.8.1, R1.8.2, R1.8.6)
4. Создать функцию, проверяющую необходима ли справка `helpIsNeeded(args: Array<String>) -> Bool` (`arg[0] equal "-h"`)
5. Печатать во всех остальных случаях справку и код 0 (`System.exit(0)`)
6. Запустить автотесты
7. Опубликовать изменения в git

## Этап 3: Аутентификация пользователя (R1.1)
1. Создать функцию, проверяющую надо ли аутентифицировать `authenticationIsNeeded(args: Array<String>) -> Bool`
(args[0] equal -login && args[2] equal -path)
2. Создать функцию, валидирующую надо логин `validate(login: String) `
(проверяем формат через regexp `[a-z]{1-10}`, код 2 ) - R1.9, R1.8.3
3. Создать функцию, проверяющую что логин существует `findUserBy(login: String)`
(проверяем, что login equal sasha)
4. Создать функцию, проверяющую валидность пароля `validate(pass: String, for login: String) -> Bool`
 (проверяем, что login equal sasha && pass equal 123, код 3, если не найден) - R1.9
5. Создать `data class User` с логином и паролем - R1.1
6. Создать коллекцию `Users`, заполненную тестовыми данными
7. Отефакторить методы на работу с коллекцией пользователей
8. Создать класс `ArgHandler` (поля-аргументы -h, -login, -pass) для разбора параметров
9. Перенести функции в `ArgHandler`:
     - `argsIsNotEmpty(args: Array<String>) -> Bool`, 
     - `helpIsNeeded(args: Array<String>) -> Bool`, 
     - `authenticationIsNeeded(args: Array<String>) -> Bool`
10. Отрефакторим код, чтобы использовать `ArgHandler`
11. Создать класс `AuthenticationService` для аутентификации пользователя по логину и паролю - R1.1
    1. Перенести коллекцию пользователей в класс
    2. Перенести функции
        - `User.validateUserLogin() -> Bool`
        - `findUser(user: User)`
        - `validate(pass: String, for login: String)`
12. Создать класс `HelpHandler`
    1. Перенести функцию печати справки в класс `HelpHandler`
    2. Отрефакторить код на работу с классом `HelpHandler`
14. Написать функции хеширования пароля в классе `AuthenticationService`
    1. Написать функцию получения хеша по паролю `generateHash(pass: String, salt: String)->String`
    2. Написать функцию генерации соли `generateSalt()->String`
    3. Написать функцию сравнение хешей в `validate(pass: String, usersHash: String) -> Bool` 
    (0 — успех, 4 — не успех (R1.9))
    4. Заменить поле в `User` c `pass` на `hash`
    5. Обновить тестовые данные с генерацией `hash`-й
15. Протестировать реализацию автотестами(R1.12)
16. Опубликовать изменения в git

## Этап 4: Авторизация (R1.3)
1. Создать функцию, проверяющую есть ли доступ у пользователя с такой ролью к ресурсу 
    `Users.haveAccess(res: String, role: String)` 
    (проверяем, что user.login equal sasha && role equal READ && res equal A), код 6 
2. Добавить функцию валидации роли validate(role: String), код 5 - R1.5, R1.8.4
3. Создать функцию, проверяющую существует ли такой ресурс в списке `resourceExist(res: String) -> Bool` 
(проверяем, что res equal A), код 6 — R1.9
4. Создать функцию, проверяющую доступ к потомку по родителю `haveParentAccess(res: String, role: String)`
5. Создать перечисление `enum Role` c READ, WRITE, EXECUTE R1.3, R1.5
6. Создать `data class UsersResources` (R1.3, R1.6) с полями `path: String`, `role: Role`, `user: User` 
7. Создать список ресурсов с тестовыми данными
8. Добавить функцию в `ArgHandler`, проверяющую необходима ли авторизация `authorizationIsNeeded()->Bool`
9. Добавить в `ArgHandler` поля `-res`, `-role`
10. Отрефакторить функции на работу с коллекцией 
11. Создать класс `AuthorizationService` (R1.3)
    1. Создать функцию поиск ресурса `findBy(res: String)`
    2. Перенести функции в класс:
        - haveAccess
        - resourceExist
        - haveParentAccess
    3. Перенести список тестовых данных
12. Создать класс `Application`
    1. Создать функцию `printHelp` 
    2. Перенести шаг аутентификации в функцию `startAuthentication(login: String, pass: String, completion: () )`
    3. Создать функцию для шага авторизации `startAuthorization(user: User, res: String, role: String, completion: ())`
    4. Написать функцию, выбирающую последовательность действий `run()` 
    (проверяем нужна ли справка, проверяем нужно ли аутентифицировать и/или авторизовывать)
13. Протестировать реализацию автотестами R1.12
14. Опубликовать изменения в git

## Этап 5: Аккаунтинг (R1.7)
1. Создать функцию, проверяющую валидность даты `validate(date: String)` 
(проверяем `date as Date`, иначе код 7) - R1.9
2. Создать функцию, проверяющую валидность объему `validate(vol: String)` 
(проверяем `vol as Int` и положительность результата приведения, иначе код 7) - R1.9
3. Создаем функцию, проверяющую последовательность дат `validateActivity(ds: Date, de: Date)` 
(проверяем ds.isBefore(de), иначе код 7) - R1.9
4. Написать шаг аккаунтинга в `Application` в функции 
`startAccounting(user: User, res: UsersResources, ds: String, de: String, vol: String)`
5. Создать класс сессии пользователя `UserSession(user:User,res: UsersResources, ds: Date, de: Date, vol: Int)`(R1.7)
6. Создать класс `AccountService` 
    1. Перенести функции в класс 
        - `validate(date: String)` 
        - `validate(vol: String)`
        - `validateActivity(ds: Date, de: Date)` 
    2. Создать коллекцию `UserSession`
    3. Создать метод сохранения активности `writeSession` 
    (записываем в коллекцию все данные аккаунтинга)
7. Отрефакторить код в `Application` на работу с `AccountService`
8. Протестировать реализацию автотестами R1.12
9. Опубликовать изменения в git

## Этап 6: Сборка требований
1. Написать процесс AAA (R1.11)
    1. Сначала аутентификацию (два параметра и более)
    2. Если успешно, переходим к авторизации (четыре параметра и более)
    3. Если успешно, переходим к аккаунтингу (семь параметров)
2. Запустить автотесты
3. Опубликовать изменения в git
4. Обновить readme 
