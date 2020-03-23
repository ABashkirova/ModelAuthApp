package xyz.sashenka.modelauthapp

import xyz.sashenka.modelauthapp.model.ExitCode.*
import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.model.ExitCode
import xyz.sashenka.modelauthapp.model.Role
import xyz.sashenka.modelauthapp.model.UserSession
import xyz.sashenka.modelauthapp.model.UsersResources
import xyz.sashenka.modelauthapp.repository.ResourceRepository
import xyz.sashenka.modelauthapp.repository.UserRepository
import xyz.sashenka.modelauthapp.service.*

class Application(args: Array<String>) {
    private val argHandler: ArgHandler = ArgHandler(args)
    private val userRepository = UserRepository()
    private val helpService = HelpService()
    private val authenticationService = AuthenticationService(userRepository)
    private val validatingService = ValidatingService()
    private lateinit var resourceRepository: ResourceRepository
    private lateinit var authorizationService: AuthorizationService
    private val accountingService = AccountingService()

    fun run(): ExitCode {
        if (argHandler.shouldPrintHelp()) {
            helpService.printHelp()
            return HELP
        }

        if (!validatingService.isLoginValid(argHandler.login))
            return INVALID_LOGIN_FORMAT

        val user = authenticationService.findUser(argHandler.login!!) ?: return UNKNOWN_LOGIN

        if (!authenticationService.verifyPass(user, argHandler.password!!))
            return WRONG_PASSWORD

        if (!argHandler.canAuthorise())
            return SUCCESS

        if (!validatingService.isRoleValid(argHandler.role))
            return UNKNOWN_ROLE

        resourceRepository = ResourceRepository()
        authorizationService = AuthorizationService(
            UsersResources(
                argHandler.resource!!,
                Role.valueOf(argHandler.role!!),
                user.login
            ),
            resourceRepository
        )

        if (!authorizationService.haveAccess())
            return NO_ACCESS

        if (!argHandler.canAccount())
            return SUCCESS

        val dateStart = validatingService.parseDate(argHandler.dateStart!!)
        val dateEnd = validatingService.parseDate(argHandler.dateEnd!!)
        val volume = validatingService.parseVolume(argHandler.volume!!)

        if (!validatingService.areDatesValid(dateStart, dateEnd) || !validatingService.isVolumeValid(volume))
            return INVALID_ACTIVITY

        accountingService.write(
            UserSession(
                user.login,
                authorizationService.usersResource.path,
                dateStart!!,
                dateEnd!!,
                volume!!
            )
        )

        return SUCCESS
    }
}