# [ModelAuthApp](https://abashkirova.github.io/model-auth-app/)
[![Build Status](https://travis-ci.org/ABashkirova/model-auth-app.svg?branch=master)](https://travis-ci.org/ABashkirova/model-auth-app) 
![LINE](https://img.shields.io/badge/line--coverage-91%25-brightgreen.svg)
![BRANCH](https://img.shields.io/badge/branch--coverage-82%25-brightgreen.svg)
![COMPLEXITY](https://img.shields.io/badge/complexity-1,71-brightgreen.svg)

Это учебный проект, моделирующий процесс [Аутентификации, Авторизации и Аккаунтинга пользователя](https://ru.wikipedia.org/wiki/AAA_(информационная_безопасность)).
Приложение представляет собой консольную утилиту, принимающую на вход аргументы. 

#### Приложение работает со следующим списком аргументов

| Название параметра | Возможное значение |
|:---|:---|
|-h | вызов справки|
|-login | Логин пользователя, строка, строчные буквы. Не более 10 символов |
|-pass | Пароль |
|-res | Абсолютный путь до запрашиваемого ресурса, формат A.B.C |
|-role | Уровень доступа к ресурсу. Возможные варианты: READ, WRITE, EXECUTE |
|-ds| Дата начала сессии с ресурсом, формат YYYY-MM-DD |
|-de | Потребляемый объем, целое число |
|-vol | Потребляемый объем, целое число |

# Roadmap
В приложении последовательно реализованы [наборы требований](./docs/Requirements.md).

1. [План работ по набору требований #1](./docs/Roadmap1.md)
2. [План работ по набору требований #2](./docs/Roadmap2.md)
3. [План работ по набору требований #3](./docs/Roadmap3.md)
3. [План работ по набору требований #4](./docs/Roadmap4.md)

# Build & Run application
## Build 
```bash
./gradlew build
```

## Test
Запустить скрипт `TEST.sh`
```bash
./gradlew test
```
