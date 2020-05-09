# План R4
1. Добавить gradle к проекту R4.1 - 1h
2. Добавить таску для сборки проекта - ./gradlew build R4.2 — 30m
2. Добавить dependency и repository в build.gradle R4.3 — 30m
    - flyway
    - h2
    - kotlinx-cli
    - log4j
4. Добавить сборку fatjar R4.4  - 5m
5. Добавить новые зависимости и плагины к проекту  - 1h
    1. mockito R4.5
    2. spek R4.5
    3. ktlint R4.7
    4. detect R4.8
    5. jacoco R4.6
6. Написать таски для тестирования кода - 1h
    1. запуск unit тестов R4.5
    2. запуск линтера R4.7
    3. генерация отчета jacoco R4.6
    4. генерация отчета статического анализа R4.8 
7. Написать unit тесты на сервисы R4.5
    1. Написать mock для dao и репозиториев объектов - 1h
    2. Покрыть тестами ArgHandler - 20m
    3. Покрыть тестами AuthenticationService.verifyPass - 10m
    4. Покрыть тестами AuthorizationService.checkAccess - 10m
    5. Покрыть тестами ValidatingService - 40m
    6. Покрыть тестами Application - 1h
8. Исправить travis - 15m
9. Обновить Readme с новыми инструкциями и новыми бейджами - 30m 
