package xyz.sashenka.modelauthapp.service

class HelpService {
    fun printHelp() {
        """
            Usage: app.jar options_list
            Options: 
                -login -> Логин пользователя, строка, строчные буквы. Не более 10 символов { String }
                -pass -> Пароль { String }
                -res -> Абсолютный путь до запрашиваемого ресурса, формат A.B.C { String }
                -role -> Уровень доступа к ресурсу. Возможные варианты: READ, WRITE, EXECUTE { String }
                -ds -> Дата начала сессии с ресурсом, формат YYYY-MM-DD  { String }
                -de -> Дата окончания сессии с ресурсом, формат YYYY-MM-DD  { String }
                -vol -> Потребляемый объем, целое число { String }
                -h -> Usage info 
        """.trimIndent().also {
            println(it)
        }
    }
}