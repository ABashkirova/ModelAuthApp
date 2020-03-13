import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val exitCode: Int
    when {
        helpIsNeeded(args) -> {
            exitCode = 1
            printHelp()
        }

        authenticationIsNeeded(args) -> {
            exitCode = when {
                validateLogin(args[1]) -> {
                    when {
                        loginExists(args[1]) -> 0
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