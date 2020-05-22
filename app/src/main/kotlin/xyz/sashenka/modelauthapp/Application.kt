package xyz.sashenka.modelauthapp

import com.google.inject.Inject
import org.apache.logging.log4j.kotlin.loggerOf
import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.model.ExitCode
import xyz.sashenka.modelauthapp.model.ExitCode.HELP
import xyz.sashenka.modelauthapp.model.ExitCode.INVALID_ACTIVITY
import xyz.sashenka.modelauthapp.model.ExitCode.INVALID_LOGIN_FORMAT
import xyz.sashenka.modelauthapp.model.ExitCode.NO_ACCESS
import xyz.sashenka.modelauthapp.model.ExitCode.SUCCESS
import xyz.sashenka.modelauthapp.model.ExitCode.UNKNOWN_LOGIN
import xyz.sashenka.modelauthapp.model.ExitCode.UNKNOWN_ROLE
import xyz.sashenka.modelauthapp.model.ExitCode.WRONG_PASSWORD
import xyz.sashenka.modelauthapp.model.domain.Role
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.args.AccountingData
import xyz.sashenka.modelauthapp.service.*

class Application {
    @Inject lateinit var helpService: HelpService
    @Inject lateinit var validatingService: ValidatingService
    @Inject lateinit var authenticationService: AuthenticationService
    @Inject lateinit var authorizationService: AuthorizationService
    @Inject lateinit var accountingService: AccountingService

    private val logger = loggerOf(Application::class.java)
    private val nonCorrectActivity = "Неверная активность: "

    fun run(args: Array<String>): ExitCode {
        logger.info { "Старт выполнения запроса" }
        val argHandler = ArgHandler()
        argHandler.parse(args)

        val authenticationData = argHandler.getAuthenticationData()
        logger.info { args.joinToString() }
        if (authenticationData == null) {
            logger.info { "Данных для аутентификации нет -> Печать справки" }
            helpService.printHelp()
            return HELP
        }

        if (!validatingService.isLoginValid(authenticationData.login)) {
            logger.error { "Неверный формат логина: ${authenticationData.login}" }
            return INVALID_LOGIN_FORMAT
        }

        // Authentication
        logger.info { "Попытка аутентификации" }
        var currentExitCode = startAuthentication(authenticationData.login, authenticationData.password)
        if (isExitNeeded(
                currentExitCode != SUCCESS,
                "Результат аутентификации : ${currentExitCode.name}"
            )
        ) return currentExitCode

        // Authorization
        val authorizationData = argHandler.getAuthorizationData()
        if (authorizationData == null) {
            logger.info { "Данных для авторизации нет. Завершаем шаг: ${currentExitCode.name}" }
            return currentExitCode
        }
        logger.info { "Валидируем роль" }
        if (!validatingService.isRoleValid(authorizationData.role)) {
            logger.error {
                "Полученено неверное значение роли (${authorizationData.role}). " + "Завершаем шаг: $UNKNOWN_ROLE"
            }
            return UNKNOWN_ROLE
        }
        val usersResources = UsersResources(
            authorizationData.path,
            Role.valueOf(authorizationData.role),
            authorizationData.login
        )
        logger.info { "Попытка авторизации" }
        currentExitCode = startAuthorization(usersResources)
        if (isExitNeeded(
                currentExitCode != SUCCESS,
                "Результат авторизации : ${currentExitCode.name}"
            )
        ) return currentExitCode

        // Accounting
        val accountingData = argHandler.getAccountingData()
        if (accountingData == null) {
            logger.info { "Данных для аккаунтинга нет. Завершаем шаг: ${currentExitCode.name}" }
            return currentExitCode
        }
        logger.info { "Попытка аккаунтинга" }
        currentExitCode = startAccounting(usersResources, accountingData)
        if (isExitNeeded(
                currentExitCode != SUCCESS,
                "Результат аккаунтинга : ${currentExitCode.name}"
            )
        ) return currentExitCode

        logger.info { "Завершаем шаг: ${currentExitCode.name}" }
        return currentExitCode
    }

    private fun isExitNeeded(expression: Boolean, message: String): Boolean {
        return if (!expression) {
            false
        } else {
            logger.error { message }
            true
        }
    }

    private fun startAuthentication(login: String, password: String): ExitCode {

        val user = authenticationService.findUser(login)
        if (user == null) {
            logger.error { "Не найден пользователь с логином: $login" }
            return UNKNOWN_LOGIN
        }

        if (!authenticationService.verifyPass(user, password)) {
            logger.error { "Неверный пароль" }
            return WRONG_PASSWORD
        }

        return SUCCESS
    }

    private fun startAuthorization(usersResources: UsersResources): ExitCode {

        if (!authorizationService.checkAccess(usersResources)) {
            logger.error {
                "Нет доступа к ресурсу(${usersResources.path})" +
                    "c запрашиваемым доступом(${usersResources.role})"
            }
            return NO_ACCESS
        }

        return SUCCESS
    }

    private fun startAccounting(usersResources: UsersResources, accountingData: AccountingData): ExitCode {
        val startDate = validatingService.parseDate(accountingData.startDate)
        if (startDate == null) {
            logger.error {
                "Неверная активность: " +
                    "дата начала сессии невалидна по формату ${accountingData.startDate}"
            }
            return INVALID_ACTIVITY
        }
        val endDate = validatingService.parseDate(accountingData.endDate)
        if (endDate == null) {
            logger.error {
                nonCorrectActivity + "дата окончании сессии невалидна по формату ${accountingData.endDate}"
            }
            return INVALID_ACTIVITY
        }
        val volume = validatingService.parseVolume(accountingData.volume)
        if (volume == null) {
            logger.error {
                nonCorrectActivity + "объем ресурса невалиден по формату ${accountingData.volume}"
            }
            return INVALID_ACTIVITY
        }

        if (!(validatingService.areDatesValid(startDate, endDate) && validatingService.isVolumeValid(volume))) {
            logger.error {
                nonCorrectActivity + "дата начала сессии невалидна ${accountingData.startDate}"
            }
            return INVALID_ACTIVITY
        }

        val userAccess = authorizationService.getResourceAccess(usersResources)
        if (userAccess == null) {
            logger.error { "Нет доступа, на попытке аккаунтиться" }
            return NO_ACCESS
        }

        val session = UserSession(
            accountingData.login,
            accountingData.resource,
            accountingData.startDate,
            accountingData.endDate,
            volume
        )
        accountingService.saveSession(userAccess, session)

        return SUCCESS
    }
}
