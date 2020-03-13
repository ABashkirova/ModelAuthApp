import kotlin.system.exitProcess

fun main(args: Array<String>) {
    when {
        helpIsNeeded(args) -> {
            printHelp()
            exitProcess(status = 1)
        }

        authenticationIsNeeded(args) -> {
            when {
                validateLogin(args[1]) -> exitProcess(status = 0)
                else -> exitProcess(status = 2)
            }
        }

        else -> {
            printHelp()
            exitProcess(status = 0)
        }
    }
}

fun authenticationIsNeeded(args: Array<String>): Boolean = when {
    argsAreNotEmpty(args) && args.size >= 4 -> args[0] == "-login" && args[2] == "-pass"
    else -> false
}

fun validateLogin(login: String): Boolean {
    return login.matches(regex = "[a-z]{1,10}".toRegex())
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