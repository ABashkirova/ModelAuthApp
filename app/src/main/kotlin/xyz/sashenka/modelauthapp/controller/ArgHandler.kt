package xyz.sashenka.modelauthapp.controller

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import org.apache.logging.log4j.kotlin.loggerOf
import xyz.sashenka.modelauthapp.model.dto.args.AccountingData
import xyz.sashenka.modelauthapp.model.dto.args.AuthenticationData
import xyz.sashenka.modelauthapp.model.dto.args.AuthorizationData

class ArgHandler {
    private val parser = ArgParser("aaa.jar", true)

    val login: String? by parser.option(
        ArgType.String,
        shortName = "login",
        description = "Логин пользователя, строка, строчные буквы. Не более 10 символов"
    )
    val password: String? by parser.option(
        ArgType.String,
        shortName = "pass",
        description = "Пароль"
    )
    val resource: String? by parser.option(
        ArgType.String,
        shortName = "res",
        description = "Абсолютный путь до запрашиваемого ресурса, формат A.B.C"
    )
    val role: String? by parser.option(
        ArgType.String,
        shortName = "role",
        description = "Уровень доступа к ресурсу. Возможные варианты: READ, WRITE, EXECUTE"
    )
    val dateStart: String? by parser.option(
        ArgType.String,
        shortName = "ds",
        description = "Дата начала сессии с ресурсом, формат YYYY-MM-DD "
    )
    val dateEnd: String? by parser.option(
        ArgType.String,
        shortName = "de",
        description = "Дата окончания сессии с ресурсом, формат YYYY-MM-DD "
    )
    val volume: String? by parser.option(
        ArgType.String,
        shortName = "vol",
        description = "Потребляемый объем, целое число"
    )

    private val logger = loggerOf(ArgHandler::class.java)

    fun parse(args: Array<String>) {
        try {
            parser.parse(args)
            logger.debug("""
            login $login
            password $password
            resource $resource
            role $role
            dateStart $dateStart
            dateEnd $dateEnd
            volume $volume
        """.trimIndent())
        } catch (ex: IllegalStateException) {
            println(ex.localizedMessage)
        }
    }

    fun getAuthenticationData(): AuthenticationData? {
        val login = login
        val password = password
        return if (login == null || password == null) {
            null
        } else {
            AuthenticationData(login, password)
        }
    }

    fun getAuthorizationData(): AuthorizationData? {
        val resource = resource
        val role = role
        val login = login
        return if (login == null || role == null || resource == null) {
            null
        } else {
            AuthorizationData(resource, role, login)
        }
    }

    fun getAccountingData(): AccountingData? {
        val resource = resource
        val login = login
        val dateStart = dateStart
        val dateEnd = dateEnd
        val volume = volume
        return if (login == null || resource == null || dateStart == null || dateEnd == null || volume == null) {
            null
        } else {
            AccountingData(
                login,
                resource,
                dateStart,
                dateEnd,
                volume
            )
        }
    }
}
