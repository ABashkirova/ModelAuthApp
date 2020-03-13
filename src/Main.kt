import controller.ArgHandler
import controller.ArgKey
import model.User
import service.AuthenticationService
import service.HelpService
import kotlin.system.exitProcess

val users = listOf(
    User(login = "sasha", password = "123"),
    User(login = "admin", password = "qwerty"),
    User(login = "q", password = "@#\$%^&*!"),
    User(login = "aleksandra", password = "abc")
)

fun main(args: Array<String>) {
    val argHandler = ArgHandler(args)
    val helpService = HelpService()
    val exitCode: Int
    when {
        argHandler.helpIsNeeded() -> {
            exitCode = 1
            helpService.printHelp()
        }

        argHandler.authenticationIsNeeded() -> {
            val authService = AuthenticationService()
            val login: String? = argHandler.getValue(ArgKey.LOGIN)
            exitCode = if (login != null && authService.validateLogin(login = login)) {
                val user: User? = authService.getUser(login = login)
                val pass: String? = argHandler.getValue(ArgKey.PASSWORD)
                if (user != null && pass != null) {
                    if (authService.verifyPassForUser(pass = pass, user = user)) 0
                    else 4
                } else {
                    3
                }
            } else 2
        }
        else -> {
            exitCode = 0
            helpService.printHelp()
        }
    }

    exitProcess(status = exitCode)
}