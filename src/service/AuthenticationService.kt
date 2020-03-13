package service

import model.User
import java.security.MessageDigest

class AuthenticationService {
    companion object {
        private const val SALT = "prt&MM"
    }

    private val users = listOf(
        User(
            login = "sasha",
            hash = "834d80053ffca546c11b486db79dbe31878b67e7cc9f6f74462f2347f28c767b"
        ), // password = "123"
        User(
            login = "admin",
            hash = "affb3cd6b447ff1630c9ebf933f9abfad257bc32a0e5a25c80341bbc8e3d3a9b"
        ), // password = "qwerty",
        User(
            login = "q",
            hash = "cd18eba9806a168bb471da898670708d172ff3ddb60a3ad697c4ee0c51f95986"
        ), //  password = "@#\$%^&*!",
        User(
            login = "aleksandra",
            hash = "91fdccfd2cdf63a743242d8f6a315a77dc498adf13db05e5e875d147a0f47cf2"
        ) // password = "abc",
    )

    fun validateLogin(login: String): Boolean {
        return login.matches(regex = "[a-z]{1,10}".toRegex())
    }

    fun getUser(login: String): User? = users.find { it.login == login }

    fun verifyPassForUser(pass: String, user: User): Boolean {
        return isEqualsHash(pass, user)
    }

    private fun isEqualsHash(pass: String, user: User): Boolean = getHashPassword(pass) == user.hash

    private fun getHashPassword(pass: String): String = generateHash(pass = pass, salt = SALT)

    private fun generateHash(pass: String, salt: String): String {
        return MessageDigest
            .getInstance("SHA-256")
            .digest((salt + pass).toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }
}