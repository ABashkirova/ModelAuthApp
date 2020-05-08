# Requirements
К приложению были предложены требования. Требования необходимо распланировать.
Нумерация R-Requirements, далее номер занятия, потом номер требования

## Requirements #1
| Требование | Комментарий |
|:---|:---|
| R1.1 Приложение должно иметь возможность аутентифицировать пользователя по логину и паролю | [AAA](https://ru.wikipedia.org/wiki/AAA_(информационная_безопасность)). Логин `[a-z]{1-10}`, пароль любой. |
| R1.2 Пароль должен храниться безопасно | Это значит, что храним хеш и соль. Хорошо еще перец |
| R1.3 Приложение должно иметь возможность авторизовать пользователя к определенному ресурсу с определенной ролью | Ресурс `[A-Z]+(\.[A-Z]+)+` Слова из заглавных букв, разделнные точками|
| R1.4 Ресурсы должны представляться в виде дерева | Ресурс представляет собой строку "A.B.C", A,B,C — узлы дерева. Все возможные ресурсы существуют. Мы не храним все возможные комбинации ресурсов|
| R1.5 Допустимые роли READ, WRITE, EXECUTE | |
| R1.6 Доступ к ресурсу с определенной ролью автоматически предоставляет доступ ко всем потомкам | |
| R1.7 Приложение должно иметь возможность учесть время доступа и потребленный объем | |
| R1.8 Приложение — это консольная утилита, принимающей на вход параметры | |
| R1.8.1 Пусто | вывод справки |
| R1.8.2 `-h` | вывод справки |
| R1.8.3 `-login <str>` `-pass <str>` | |
| R1.8.4 `-login <str>` `-pass <str>` `-res <str>` `-role <str>` | |
| R1.8.5 `-login <str>` `-pass <str>` `-res <str>` `-role <str>` `-ds <YYYY-MM-DD>` `-de <YYYY-MM-DD>` `-vol <int>` | |
| R1.8.6 Неизвестные параметры | вывод справки |
| R1.9 Приложение должно возвращать следующие exit-коды: 0 – успех; 1 – справка; 2 – неверный формат логина; 3 – неизвестный логин; 4 - неверный пароль; 5 - неизвестная роль; 6 - нет доступа; 7 - некорректная активность (невалидная дата или объем) | |
| R1.10 Приложение на вход принимает параметры в любом порядке | |
| R1.11 Приложение сначала аутентифицирует (коды 0,1,2,3,4), потом авторизовывает (коды 0,5,6), потом аккаунтить (коды 0,7)| |
| R1.12 Создайте скрипты, которые компилириуют и собирают jar; выполлняют программу; прогоняют тесты | Можно попробовать расскрасить вывод в тестах |

## Requirements #2
| Требование | Комментарий 
|:---|:---| 
| R2.1 Создать файл `Roadmap2.md` и описать план работы над вторым набором требований	| |
| R2.2 Каждый проект должен содержать `README.md` с описанием проекта. Также должны быть ссылки на два роадмапа: по первому и второму набору требований. | Описание формата markdown https://help.github.com/articles/basic-writing-and-formatting-syntax/ |
| R2.3 В README.md должна присутствовать инструкция по сборке проекта из консоли | |
| R2.4 В README.md должна присутствовать инструкция по запуску собранного проекта из консоли | |
| R2.5 В README.md должна присутствовать инструкция по тестированию собранного проекта | |
| R2.6 В репозитории должен содержаться скрипт сборки проекта `BUILD.sh` | Помните, что `classpath` на Windows разделяется через `;` а на linux через `:`. Используйте ```"$(expr substr $(uname -s) 1 10)" == "MINGW64_NT" или "$(expr substr $(uname -s) 1 10)" == "Linux"``` http://stackoverflow.com/questions/394230/detect-the-os-from-a-bash-script Научитесь компилировать код руками [1](http://stackoverflow.com/questions/5194926/compiling-java-files-in-all-subfolders)[2](http://stackoverflow.com/questions/6623161/javac-option-to-compile-all-java-files-under-a-given-directory-recursively) [Компилятор](http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javac.html) [Упаковщик в jar архив](https://docs.oracle.com/javase/tutorial/deployment/jar/basicsindex.html)|
| R2.7 В репозитории должен содержаться скрипт запуска проекта `RUN.sh`| Научитесь запускать jar файл с указанием [зависимостей](http://docs.oracle.com/javase/7/docs/technotes/tools/solaris/java.html) |
| R2.8 В репозитории должен содержаться скрипт тестирования системы `TEST.sh`, покрывающий все варианты использования (которые вытекают из требований)| Напишите скрипт выполняющий указанные тест-кейсы с выводом результатов отдельных тестов и итогового результата. Тестирование считается неуспешным если хоть один тест не прошел.|
| R2.9 Код должен быть отформатирован согласно требованиям [kotlin coding conventions](https://kotlinlang.org/docs/reference/coding-conventions.html )||
| R2.10 В репозитории должен содержаться файл `.gitignore` в котором будут отфильтрованы бинарные артефакты и файлы IDE| Например, скомпилированный код *.class, папка .idea и файл проекта .iml и.т.д.|
| R2.11 Скрипт тестирования должен возвращать 0 если все тесты прошли и 1 если хоть один тест не прошел | |
| R2.12 Приложение должно автоматически собираться и тестироваться на `travis-ci.org`|При коммите с Windows, не забудьте пометить sh-файлы исполняемым флагом git update-index --chmod=+x *.sh |
| R2.13 Включите `github pages` в вашем репозитории и выберите какой-нибудь стиль, чтобы сайт сгенерился из вашего `README.md` файла |https://pages.github.com|

## Requirements #3
| Требование | Комментарий 
|:---|:---| 
| R3.1 Приложение должно логировать в stdout все введенные параметры, процесс принятия решения с пояснением причины, возникающие исключения со стректрейсом | Можно использовать [библиотеку](http://logging.apache.org/log4j/2.x/manual/configuration.html) Вывод вида "Пароль ABC для пользователя XYZ неверный" |
| R3.2 Приложение должно логировать в файл aaa.log в директории запуска все введенные параметры, процесс принятия решения с пояснением причины, возникающие исключения со стректрейсом |  |
| R3.3 Приложение должно хранить данные во встраиваемой СУБД | Вы можете выбрать любую встраиваемую СУБД, т.е. такую которая включается внутрь jar-файла, не требует установки и настройки [В качестве БД можно использовать это](http://www.h2database.com/html/main.html) |
| R3.4 Схема данных должна включать по крайней мере 3 таблицы, 3 внешних ключа, один уникальный ключ (логин), один индекс (пользователь, роль) |  |
| R3.5 При старте приложение должно файл с БД aaa.db (в зависимости от выбранной СУБД расширение может отличаться) в директории запуска и инициализировать схему если БД не найдена |  Для инициализации схемы используйте [flywaydb](http://flywaydb.org/getstarted/firststeps/api.html) |
| R3.6 При старте приложение должно заполнять тестовыми данными через миграцию, если БД не найдена в папке с приложением |  Данные должны заполняться в отдельной миграции |
| R3.7 Выборка данных о пользователе и ролях из базы должна исключать SQLI  | Используйте для выборки данных [PreparedStatement](http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html) |
| R3.8 Вставка данных о доступе должна исключать SQLI |  |
| R3.9 Вся работа с данными из базы должна осуществляться через DAL и доменные объекты (Anemic Data Model) |  |
| R3.10 Архитектура приложения должна удовлетворять принципам SOLID | [Принципы SOLID](https://ru.wikipedia.org/wiki/SOLID_(объектно-ориентированное_программирование)) |
| R3.11 Доменные классы должны находиться в отдельном пакете| DAO-класс если более одного тоже должны находиться в отдельном пакете  |
| R3.12 Настройки БД (url, login, pass) должны передаваться через переменные окружения System.getenv() |  |

## Requirements #4
| Требование | Комментарий |
|:---|:---| 
| R4.1 Структура проекта должна соответствовать проекту gradle |
| R4.2 Проект должен собираться при помощи gradle |  |
| R4.3 Все зависимые библиотеки должны подставляться из репозитория | |
| R4.4 При сборке все зависимости должны упаковываться в один исполняемый файл |  |
| R4.5 Приложение должно тестироваться через Spek | https://www.spekframework.org/ http://habrahabr.ru/post/120101/ http://site.mockito.org  подмена реализации класса|
| R4.6 Gradle должен генерировать отчет по покрытию тестами JaCoCo | |
| R4.7 Стиль кода должен проверяться при помощи ktlint | https://github.com/pinterest/ktlint |
| R4.8 Gradle должен генерировать отчет по статическому анализу кода | https://arturbosch.github.io/detekt/ |
| R4.9 Документация должна быть обновлена для соответствия новой системе сборки |  |

## Requirements #5
| Требование | Комментарий |
|:---|:---| 
| R5.1 Проект должен быть преобразован в web-приложение | [gradle](https://guides.gradle.org/building-java-web-applications/) [maven](http://www.mkyong.com/maven/how-to-create-a-web-application-project-with-maven/)|
| 5.2 Проект должен запускаться через сервлет контейнер | Не забудьте добавить [jetty-servlet](http://www.eclipse.org/jetty/documentation/current/jetty-maven-plugin.html) в зависимости Запускать нужно через mvn org.eclipse.jetty:jetty-maven-plugin:run |
| R5.3 Создайте один сервлет слушающий /echo/* и переопределите методы doGet и doPost.| Проверяйте URL из request: если запрос не /echo/get или /echo/post возвращайте ответ 404 |
| R5.4 Проект должен содержать сервлет слушающий по адресу /echo/get который принимает GET запрос вида ?id=X и выводящий в ответ значение X  | [Пример](http://www.mkyong.com/servlet/a-simple-servlet-example-write-deploy-run/) |
| R5.5 Проект должен содержать сервлет слушающий по адресу /echo/post который принимает POST запрос с текстом и делает редирект на /echo/get?id=X где X поле введенное в форму | |
| R5.6 Проект должен содержать страницу index.html с формой, одним полем и кнопкой submit, форма отправляет post запрос на /echo/post сервлет. Сделать ссылку на GET-сервлет с каким-нибудь параметром. | |
| R5.7 Сделать иерархический проект | [Pom-проект](https://maven.apache.org/plugins/maven-eclipse-plugin/reactor.html) В родительском pov-файле должны быть прописаны два дочерних модуля. А в каждом дочернем модуле должна быть ссылка на родителя.|
| R5.8 Задеплоить war проект на heroku | [Getting Started on Heroku with Java](https://devcenter.heroku.com/articles/getting-started-with-java#introduction) [Deploying Java Web Applications](https://devcenter.heroku.com/articles/deploy-a-java-web-application-that-launches-with-jetty-runner)|
| R5.9 Настроить автоматический деплой war проекта на heroku | [github-integration](https://devcenter.heroku.com/articles/github-integration)|
| 5.10 Для генерации результата get страницы из шаблона используйте JSP | Запрос приходит на get-сервлет, он заполняет переменную и вызывает getRequestDispatcher("...gsp").forward(...) http://java-course.ru/student/book1/jsp/ |

## Requirements #6
| Требование | Комментарий |
|:---|:---| 
|R6.1 Пути должны конфигурироваться через guice| [Guice serblets](https://github.com/google/guice/wiki/ServletModule)|
|R6.2 Логгеры в серверной части должны инжектиться в сервлеты через guice| [Инджекция логгера](https://github.com/google/guice/wiki/CustomInjections)|
|R6.3 В GuiceServletConfig должен быть прописан UserServlet который будет работать с User /ajax/user| Для этих сервлетов переопределяйте метод service(request, responce) |
|R6.4 В GuiceServletConfig должен быть прописан AuthorityServlet который будет работать с Authority /ajax/authority| |
|R6.5 В GuiceServletConfig должен быть прописан ActivityServlet который будет работать с Activity /ajax/activity| |

## Requirements #7
| Требование | Комментарий |
|:---|:---| 
|R7.1 В сервлетах объекты должны сериализоваться при помощи GSON|[gson](https://github.com/google/gson/blob/master/UserGuide.md)| 
|R7.2 Для получения сериализатора должен использоваться провайдер|[inject providers](https://github.com/google/guice/wiki/InjectingProviders)| 
|R7.3 В UserServlet должен инжектиться провайдер GSON|| 
|R7.4 В AuthorityServlet должен инжектиться провайдер GSON|| 
|R7.5 В ActivityServlet должен инжектиться провайдер GSON|| 
|R7.6 Выполнение http get по адресу /ajax/user должен возвращать json список пользователей|Для получения списка пользователей нужно использовать соответствующий Dao из проекта app| 
|R7.7 Выполнение http get по адресу /ajax/user?id=xxx должен возвращать json пользователя с указанным идентификатором|| 
|R7.8 Выполнение http get по адресу /ajax/authority должен возвращать json список прав доступа|| 
|R7.9 Выполнение http get по адресу /ajax/authority?id=xxx должен возвращать json право пользователя с указанным идентификатором|| 
|R7.10 Выполнение http get по адресу /ajax/authority?userId=xxx должен возвращать json права указанного пользователя|| 
|R7.11 Выполнение http get по адресу /ajax/activity должен возвращать json список действий|Для получения списка действий нужно использовать соответствующи Dao из проекта client| 
|R7.12 Выполнение http get по адресу /ajax/activity?id=xxx должен возвращать json действие с указанным идентификатором|| 
|R7.13 Выполнение http get по адресу /ajax/activity?authorityId=xxx должен возвращать json действия с указанными правами доступа|| 
|R7.14 Сериализоваться должны только поля помеченные @Expose | [gson expose](https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/annotations/Expose.html)| 
|R7.15 В объекте User не должны сериализоваться пароль и хеш|| 
|R7.16 В объекте Authority не должны сериализоваться User || 
|R7.17 В объекте Activity не должны сериализваться Authority|| 
|R7.18 Коннект к базе данных данных должен инжектиться в Dao классы при помощи DI|Миграцию бд можно провести в DI конфиге| 


## Requirements #8
| Требование | Комментарий |
|:---|:---|
|R8.1 Для работы с базой должен использоваться ORM| [GuicePersist](https://github.com/google/guice/wiki/GuicePersist)|
|R8.2 Доменные объекты должны быть помечены как JPA сущности при помощи аннотаций|[entity-mapping](https://docs.jboss.org/hibernate/annotations/3.5/reference/en/html/entity.html#entity-mapping)|
|R8.3 Схема должна быть приведена к совместимой с ORM через миграции|[java-json](http://www.studytrails.com/java/json/java-google-json-parse-json-to-java.jsp)|
|R8.4 Каждый объект должен содержать версию для оптимистической блокировки|[Field version](http://www.objectdb.com/java/jpa/entity/fields#Version_Field_)|
|R8.5 ID поля должны быть автогенерируемыми|[entity id](http://www.objectdb.com/java/jpa/entity/id)|
|R8.6 Работа с сущностью User должна вестись через UserDao||
|R8.7 В UserDao должен инжектиться EntityManager|[JPA](https://github.com/google/guice/wiki/JPA)|
|R8.8 В UserServler должен инжектиться UserDao||
|R8.9 Работа с сущностью User должна вестись через AuthorityDao||
|R8.10 В AuthorityDao должен инжектиться EntityManager|[JPA](https://github.com/google/guice/wiki/JPA)|
|R8.11 В AuthorityServler должен инжектиться AuthorityDao||
|R8.12 Работа с сущностью User должна вестись через ActivityDao||
|R8.13 В ActivityDao должен инжектиться EntityManager|[JPA](https://github.com/google/guice/wiki/JPA)|
|R8.14 В ActivityServlet должен инжектиться ActivityDao||
|R8.15 Приложение должно получать параметры коннекта к БД через переменные окружения| при этом тестовое окружение должно продолжать использовать H2. |
|R8.16 Адаптируйте миграции к PostgreSQL| переведите H2 в [режим совместимости с PostgreSQL](http://www.h2database.com/html/features.html#compatibility)|
|R8.17 Используйте PostgreSQL предоставляемую сервисом Heroku|[Heroku postgres](https://www.heroku.com/postgres)|
|R8.18 Используйте пулл коннектов c3p0| [c3p0](https://www.baeldung.com/hibernate-c3p0) |
