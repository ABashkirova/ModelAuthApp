import controller.ArgHandler
import controller.ArgKey
import model.User
import kotlin.system.exitProcess

val users = listOf(
    User(login = "sasha", password = "123"),
    User(login = "admin", password = "qwerty"),
    User(login = "q", password = "@#\$%^&*!"),
    User(login = "aleksandra", password = "abc")
)

fun main(args: Array<String>) {
    val argHandler = ArgHandler(args)

    val exitCode: Int
    when {
        argHandler.helpIsNeeded() -> {
            exitCode = 1
            printHelp()
        }

        argHandler.authenticationIsNeeded() -> {
            val login: String? = argHandler.getValue(ArgKey.LOGIN)
            exitCode = if (login != null && validateLogin(login = login)) {
                val user: User? = getUser(login = login)
                val pass: String? = argHandler.getValue(ArgKey.PASSWORD)
                if (user != null && pass != null) {
                    if (verifyPassForUser(pass = pass, user = user)) 0
                    else 4
                } else {
                    3
                }
            } else 2
        }
        else -> {
            exitCode = 0
            printHelp()
        }
    }

    exitProcess(status = exitCode)
}

fun validateLogin(login: String): Boolean {
    return login.matches(regex = "[a-z]{1,10}".toRegex())
}

fun getUser(login: String): User? = users.find { it.login == login }

fun verifyPassForUser(pass: String, user: User): Boolean {
    return user.password == pass
}

fun printHelp() {
    """
        Возможные аргументы программы:
        -h                              - Вызов справки
        
        Аутентифицироваться 
        -login  <string>                - Логин пользователя, строка, строчные буквы. Не более 10 символов
        -pass   <string>                - Пароль пользователя
        
        Авторизоваться
        -res    <PATH.TO.RESOURCE>      - Абсолютный путь до запрашиваемого ресурса
        -role   <READ|WRITE|EXECUTE>    - Уровень доступа к ресурсу
        
        Активность
        -ds     <yyyy-mm-dd>            - Дата начала сессии с ресурсом
        -de     <yyyy-mm-dd>            - Дата окончания сессии с ресурсом, формат
        -vol    <int>	                - Потребляемый объем, целое число
    """.trimIndent().also {
        println(it)
    }
}