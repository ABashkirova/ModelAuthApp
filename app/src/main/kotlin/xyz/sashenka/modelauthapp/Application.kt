package xyz.sashenka.modelauthapp

import com.google.inject.Inject
import org.apache.logging.log4j.kotlin.loggerOf
import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.model.ExitCode.HELP
import xyz.sashenka.modelauthapp.model.ExitCode.INVALID_ACTIVITY
import xyz.sashenka.modelauthapp.model.ExitCode.INVALID_LOGIN_FORMAT
import xyz.sashenka.modelauthapp.model.ExitCode.NO_ACCESS
import xyz.sashenka.modelauthapp.model.ExitCode.SUCCESS
import xyz.sashenka.modelauthapp.model.ExitCode.UNKNOWN_LOGIN
import xyz.sashenka.modelauthapp.model.ExitCode.UNKNOWN_ROLE
import xyz.sashenka.modelauthapp.model.ExitCode.WRONG_PASSWORD
import xyz.sashenka.modelauthapp.model.ResultCode
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

    fun run(args: Array<String>): ResultCode {
        logger.info { "Старт выполнения запроса" }
        val argHandler = ArgHandler()
        argHandler.parse(args)

        val authenticationData = argHandler.getAuthenticationData()
        logger.info { args.joinToString() }
        if (authenticationData == null) {
            logger.info { "Данных для аутентификации нет -> Печать справки" }
            helpService.printHelp()
            return ResultCode(HELP, "Print help", null, null)
        }

        if (!validatingService.isLoginValid(authenticationData.login)) {
            logger.error { "Неверный формат логина: ${authenticationData.login}" }
            return ResultCode(INVALID_LOGIN_FORMAT, "Invalid login", null, null)
        }

        // Authentication
        logger.info { "Попытка аутентификации" }
        var currentExitCode = startAuthentication(authenticationData.login, authenticationData.password)
        if (isExitNeeded(
                currentExitCode.exitCode != SUCCESS,
                "Результат аутентификации : ${currentExitCode.exitCode.name}"
            )
        ) return currentExitCode

        // Authorization
        val authorizationData = argHandler.getAuthorizationData()
        if (authorizationData == null) {
            logger.info { "Данных для авторизации нет. Завершаем шаг: ${currentExitCode.exitCode.name}" }
            return currentExitCode
        }
        logger.info { "Валидируем роль" }
        if (!validatingService.isRoleValid(authorizationData.role)) {
            logger.error {
                "Полученено неверное значение роли (${authorizationData.role}). " + "Завершаем шаг: $UNKNOWN_ROLE"
            }
            return ResultCode(UNKNOWN_ROLE, "Unknown role ${authorizationData.role}", null, null)
        }
        val usersResources = UsersResources(
            authorizationData.path,
            Role.valueOf(authorizationData.role),
            authorizationData.login
        )
        logger.info { "Попытка авторизации" }
        currentExitCode = startAuthorization(usersResources)
        if (isExitNeeded(
                currentExitCode.exitCode != SUCCESS,
                "Результат авторизации : ${currentExitCode.exitCode.name}"
            )
        ) return currentExitCode

        // Accounting
        val accountingData = argHandler.getAccountingData()
        if (accountingData == null) {
            logger.info { "Данных для аккаунтинга нет. Завершаем шаг: ${currentExitCode.exitCode.name}" }
            return currentExitCode
        }
        logger.info { "Попытка аккаунтинга" }
        currentExitCode = startAccounting(usersResources, accountingData)
        if (isExitNeeded(
                currentExitCode.exitCode != SUCCESS,
                "Результат аккаунтинга : ${currentExitCode.exitCode.name}"
            )
        ) return currentExitCode

        logger.info { "Завершаем шаг: ${currentExitCode.exitCode.name}" }
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

    private fun startAuthentication(login: String, password: String): ResultCode {

        val user = authenticationService.findUser(login)
        if (user == null) {
            logger.error { "Не найден пользователь с логином: $login" }
            return ResultCode(UNKNOWN_LOGIN, "Unknown login: $login", null, null)
        }

        if (!authenticationService.verifyPass(user.toPlain(), password)) {
            logger.error { "Неверный пароль" }
            return ResultCode(WRONG_PASSWORD, "Wrong password", null, null)
        }

        return ResultCode(SUCCESS, "UserId: ${user.id}", user.id,null)
    }

    private fun startAuthorization(usersResources: UsersResources): ResultCode {
        if (!authorizationService.checkAccess(usersResources)) {
            logger.error {
                "Нет доступа к ресурсу(${usersResources.path})" +
                    "c запрашиваемым доступом(${usersResources.role})"
            }
            return ResultCode(NO_ACCESS, "No access to resource ${usersResources.path}", null,null)
        }
        val access = authorizationService.getResourceAccess(usersResources)
        return ResultCode(SUCCESS, "AccessId: ${access?.id}",access?.user?.id, access?.id)
    }

    private fun startAccounting(usersResources: UsersResources, accountingData: AccountingData): ResultCode {
        val startDate = validatingService.parseDate(accountingData.startDate)
        if (startDate == null) {
            logger.error {
                "Неверная активность: " +
                    "дата начала сессии невалидна по формату ${accountingData.startDate}"
            }
            return ResultCode(INVALID_ACTIVITY, "Invalid start date ${accountingData.startDate}",null, null)
        }
        val endDate = validatingService.parseDate(accountingData.endDate)
        if (endDate == null) {
            logger.error {
                nonCorrectActivity + "дата окончании сессии невалидна по формату ${accountingData.endDate}"
            }
            return ResultCode(INVALID_ACTIVITY, "Invalid end date ${accountingData.endDate}",null, null)
        }
        val volume = validatingService.parseVolume(accountingData.volume)
        if (volume == null) {
            logger.error {
                nonCorrectActivity + "объем ресурса невалиден по формату ${accountingData.volume}"
            }
            return ResultCode(INVALID_ACTIVITY, "Invalid volume ${accountingData.volume}", null, null)
        }

        if (!(validatingService.areDatesValid(startDate, endDate) && validatingService.isVolumeValid(volume))) {
            logger.error {
                nonCorrectActivity + "дата  ${accountingData.startDate}"
            }
            return ResultCode(INVALID_ACTIVITY, "Invalid dates or volume", null, null)
        }

        val userAccess = authorizationService.getResourceAccess(usersResources)
        if (userAccess == null) {
            logger.error { "Нет доступа, на попытке аккаунтиться" }
            return ResultCode(NO_ACCESS, "No access on accounting :(", null, null)
        }

        val session = UserSession(
            accountingData.login,
            accountingData.resource,
            accountingData.startDate,
            accountingData.endDate,
            volume
        )
        accountingService.saveSession(userAccess, session)

        return ResultCode(SUCCESS, "AccessId: ${userAccess.id}", null, userAccess.id)
    }
}
