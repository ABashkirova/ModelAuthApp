import model.User
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val users = listOf(
        User(login = "sasha", password = "123"),
        User(login = "admin", password = "qwerty"),
        User(login = "q", password = "@#\$%^&*!"),
        User(login = "aleksandrа", password = "abc")
    )

    val exitCode: Int
    when {
        helpIsNeeded(args = args) -> {
            exitCode = 1
            printHelp()
        }

        authenticationIsNeeded(args = args) -> {
            exitCode = when {
                validateLogin(login = args[1]) -> {
                    when {
                        loginExists(login = args[1]) -> {
                            when {
                                verifyPassForLogin(pass = args[3], login = args[1]) -> 0
                                else -> 4
                            }
                        }
                        else -> 3
                    }
                }
                else -> 2
            }
        }
        else -> {
            exitCode = 0
            printHelp()
        }
    }

    exitProcess(status = exitCode)
}

fun authenticationIsNeeded(args: Array<String>): Boolean = when {
    argsAreNotEmpty(args) && args.size >= 4 -> args[0] == "-login" && args[2] == "-pass"
    else -> false
}

fun validateLogin(login: String): Boolean {
    return login.matches(regex = "[a-z]{1,10}".toRegex())
}

fun loginExists(login: String): Boolean {
    return login == "sasha"
}

fun verifyPassForLogin(pass: String, login: String): Boolean {
    return login == "sasha" && pass == "123"
}

fun argsAreNotEmpty(args: Array<String>): Boolean = args.isNotEmpty()

fun helpIsNeeded(args: Array<String>): Boolean {
    return if (argsAreNotEmpty(args)) {
        args[0] == "-h"
    } else true
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