## Requirements #6
| Требование | Комментарий |
|:---|:---| 
|R6.1 Пути должны конфигурироваться через guice| [Guice serblets](https://github.com/google/guice/wiki/ServletModule)|
|R6.2 Логгеры в серверной части должны инжектиться в сервлеты через guice| [Инджекция логгера](https://github.com/google/guice/wiki/CustomInjections)|
|R6.3 В GuiceServletConfig должен быть прописан UserServlet который будет работать с User /ajax/user| Для этих сервлетов переопределяйте метод service(request, responce) |
|R6.4 В GuiceServletConfig должен быть прописан AuthorityServlet который будет работать с Authority /ajax/authority| |
|R6.5 В GuiceServletConfig должен быть прописан ActivityServlet который будет работать с Activity /ajax/activity| |

# План R5
1. Написать MyGuiceServletConfig - R6.1 
    - создать новый класс MyGuiceServletConfig
    - написать функцию configureServlets с указанием путей
2. Добавить инджекцию логгера - R6.2
    - добавить классы Log4JTypeListener, Log4JMembersInjector
    - добавить аннатацию Ingect в класс сервлета echo
3. Написать новый сервлет UserServlet - К6.3
    - добавить слушание configureServlets /ajax/user
    - написать форму User login/pass с кнопкой login 
    - переопредлить поведение service с принтом полученных данных
3. Написать новый сервлет AuthorityServlet - К6.4
    - добавить слушание configureServlets /ajax/authority
    - написать форму авторизации с ролью и ресурсом 
    - переопредлить поведение service с принтом полученных данных
3. Написать новый сервлет Activity - К6.4
    - добавить слушание configureServlets /ajax/activity
    - написать форму аккаунтинга с временем доступа и объемом
    - переопредлить поведение service с принтом полученных данных

| Пункт | Оценка времени(мин) | Фактическое время(мин)|
| ---| ---  |---|
| 1  |  15  |   |