package service

import model.User

class AuthenticationService {
    private val users = listOf(
        User(login = "sasha", password = "123"),
        User(login = "admin", password = "qwerty"),
        User(login = "q", password = "@#\$%^&*!"),
        User(login = "aleksandra", password = "abc")
    )

    fun validateLogin(login: String): Boolean {
        return login.matches(regex = "[a-z]{1,10}".toRegex())
    }

    fun getUser(login: String): User? = users.find { it.login == login }

    fun verifyPassForUser(pass: String, user: User): Boolean {
        return user.password == pass
    }
}