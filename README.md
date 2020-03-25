# ModelAuthApp

Это учебный проект, моделирующий процесс [Аутентификации, Авторизации и Аккаунтинга пользователя](https://ru.wikipedia.org/wiki/AAA_(информационная_безопасность)).
Приложение представляет собой консольную утилиту, принимающую на вход аргументы. 

# Roadmap
В приложении последовательно реализованы [наборы требований](./doc/Requirements.md).

1. [План работ по набору требований #1](./doc/Roadmap1.md)
2. [План работ по набору требований #2](./doc/Roadmap2.md)

# Build & Run application
## Build jar
1. Склонировать репозиторий 
2. Запустить скрипт сборки — `BUILD.sh`
```bash
cd scripts
./BUILD.sh
```
или
1. Склонировать репозиторий 
2. Соберите jar библиотеки [kotlinx.cli](https://github.com/Kotlin/kotlinx.cli.git) с помощью `gradle`
3. Запустите команду
```bash
kotlinc \
        -cp %/PATH/TO/KOTLINX-CLI.jar% \
        src \
        -include-runtime \
        -d ./out/app.jar/
```

