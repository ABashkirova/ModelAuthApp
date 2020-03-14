import ExitCode.*
import controller.ArgHandler
import controller.ArgKey.*
import service.AuthenticationService
import service.HelpService
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val argHandler = ArgHandler(args)
    val helpService = HelpService()

    if (argHandler.helpIsNeeded()) {
        helpService.printHelp()
        exitProcess(ExitCode.HELP.value)
    }

    if (!argHandler.authenticationIsNeeded()) {
        helpService.printHelp()
        exitProcess(SUCCESS.value)
    }

    var exitCode: ExitCode = SUCCESS
    val authService = AuthenticationService()
    val login: String? = argHandler.getValue(LOGIN)

    if (login != null && authService.validateLogin(login)) {
        val user = authService.getUser(login)
        val pass = argHandler.getValue(PASSWORD)

        if (user == null || pass == null) {
            exitProcess(UNKNOWN_LOGIN.value)
        } else {
            val passwordMatches = authService.verifyPassForUser(pass, user)
            if (!passwordMatches) {
                exitProcess(INVALID_PASSWORD.value)
            }
        }
    } else {
        exitProcess(INVALID_LOGIN_FORMAT.value)
    }
}