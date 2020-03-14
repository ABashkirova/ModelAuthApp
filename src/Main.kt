import ExitCode.*
import controller.ArgHandler
import controller.ArgKey.LOGIN
import controller.ArgKey.PASSWORD
import service.AuthenticationService
import service.HelpService
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val argHandler = ArgHandler(args)
    val helpService = HelpService()

    if (argHandler.helpIsNeeded()) {
        helpService.printHelp()
        exitProgram(HELP_CODE)
    }

    if (!argHandler.authenticationIsNeeded()) {
        helpService.printHelp()
        exitProgram(SUCCESS_CODE)
    }

    val authService = AuthenticationService()
    val login: String? = argHandler.getValue(LOGIN)

    if (login == null || !authService.validateLogin(login)) {
        exitProgram(INVALID_LOGIN_FORMAT)
    } else {
        val user = authService.getUser(login)
        val pass = argHandler.getValue(PASSWORD)

        if (user == null || pass == null) {
            exitProgram(UNKNOWN_LOGIN)
        } else {
            val passwordMatches = authService.verifyPassForUser(pass, user)
            if (!passwordMatches) {
                exitProgram(INVALID_PASSWORD)
            } else {
                exitProgram(SUCCESS_CODE)
            }
        }
    }
}

fun exitProgram(exitCode: ExitCode) {
    exitProcess(exitCode.value)
}