package service

import model.User
import java.security.MessageDigest

class AuthenticationService {
    private val users = listOf(
        User(
            login = "sasha",
            hash = "51b9ec587113b9e2f85bceb78fa9861557ee3067a9338c23f7b057ff0555cb45",
            salt = "V9Me2nx"
        ), // password = "123"
        User(
            login = "admin",
            hash = "da1c80f1005782d17d1696dc81d6bbc1b2c47d48cc3901d04d71e4d66cf68dae",
            salt = "6xInN0l"
        ), // password = "qwerty",
        User(
            login = "q",
            hash = "2911cb39357394f698d5b9afc266ac40d4af7e6ffc3effa212fb614598dacfb1",
            salt = "4bxdOU7"
        ), //  password = "@#\$%^&*!",
        User(
            login = "aleksandra",
            hash = "8a6d445578f6b2e38adf7b5f44b6705d416c1ec5484b1c11c08f10fae6291fac",
            salt = "TM36tOy"
        ) // password = "abc",
    )

    fun validateLogin(login: String): Boolean {
        return login.matches(regex = "[a-z]{1,10}".toRegex())
    }

    fun getUser(login: String): User? = users.find { it.login == login }

    fun verifyPassForUser(pass: String, user: User): Boolean {
        return isEqualsHash(pass, user)
    }

    private fun isEqualsHash(pass: String, user: User): Boolean = getHashPassword(pass, user.salt) == user.hash

    private fun getHashPassword(pass: String, salt: String): String {
        return generateHash(generateHash(input = pass) + salt)
    }

    private fun generateHash(input: String): String {
        return MessageDigest
            .getInstance("SHA-256")
            .digest(input.toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })
    }
}