package xyz.sashenka.modelauthapp

import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.model.ExitCode
import xyz.sashenka.modelauthapp.model.ExitCode.*
import xyz.sashenka.modelauthapp.model.domain.Role
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.domain.UsersResources
import xyz.sashenka.modelauthapp.model.dto.AccountingData
import xyz.sashenka.modelauthapp.model.dto.AuthenticationData
import xyz.sashenka.modelauthapp.model.dto.AuthorizationData
import xyz.sashenka.modelauthapp.repository.ResourceRepository
import xyz.sashenka.modelauthapp.repository.UserRepository
import xyz.sashenka.modelauthapp.service.*

class Application(args: Array<String>) {
    private val argHandler: ArgHandler = ArgHandler(args)
    private val userRepository = UserRepository()
    private val helpService = HelpService()
    private val authenticationService = AuthenticationService(userRepository)
    private val validatingService = ValidatingService()
    private val accountingService = AccountingService()

    fun run(): ExitCode {
        val authenticationData = argHandler.getAuthenticationData()

        if (authenticationData == null) {
            helpService.printHelp()
            return HELP
        }

        var currentExitCode = startAuthentication(authenticationData)
        if (currentExitCode != SUCCESS) return currentExitCode

        val authorizationData = argHandler.getAuthorizationData() ?: return currentExitCode

        currentExitCode = startAuthorization(authorizationData)
        if (currentExitCode != SUCCESS) return currentExitCode

        val accountingData = argHandler.getAccountingData() ?: return currentExitCode

        currentExitCode = startAccounting(accountingData)

        return currentExitCode
    }

    private fun startAuthentication(authenticationData: AuthenticationData): ExitCode {
        if (!validatingService.isLoginValid(authenticationData.login))
            return INVALID_LOGIN_FORMAT

        val user = authenticationService.findUser(authenticationData.login) ?: return UNKNOWN_LOGIN

        if (!authenticationService.verifyPass(user, authenticationData.password))
            return WRONG_PASSWORD

        return SUCCESS
    }

    private fun startAuthorization(authorizationData: AuthorizationData): ExitCode {
        if (!validatingService.isRoleValid(authorizationData.role))
            return UNKNOWN_ROLE

        val resourceRepository = ResourceRepository()
        val authorizationService = AuthorizationService(
            UsersResources(
                authorizationData.path,
                Role.valueOf(authorizationData.role),
                authorizationData.login
            ),
            resourceRepository
        )

        if (!authorizationService.haveAccess())
            return NO_ACCESS
        return SUCCESS
    }

    private fun startAccounting(accountingData: AccountingData): ExitCode {
        val startDate = validatingService.parseDate(accountingData.startDate) ?: return INVALID_ACTIVITY
        val endDate = validatingService.parseDate(accountingData.endDate) ?: return INVALID_ACTIVITY
        val volume = validatingService.parseVolume(accountingData.volume) ?: return INVALID_ACTIVITY

        if (!(validatingService.areDatesValid(startDate, endDate) && validatingService.isVolumeValid(volume)))
            return INVALID_ACTIVITY

        accountingService.write(
            UserSession(
                accountingData.login,
                accountingData.resource,
                startDate,
                endDate,
                volume
            )
        )

        return SUCCESS
    }
}