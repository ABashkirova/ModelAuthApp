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

# Подготовка
1.

| План | Оценка времени(мин) | Фактическое время(мин)|
| ---| ---  |---|


# План R8
1.

| План | Оценка времени(мин) | Фактическое время(мин)|
| ---| ---  |---|