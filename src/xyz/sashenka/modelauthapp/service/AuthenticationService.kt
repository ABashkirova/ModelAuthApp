package xyz.sashenka.modelauthapp.service

import xyz.sashenka.modelauthapp.model.User
import xyz.sashenka.modelauthapp.repository.UserRepository
import java.security.MessageDigest

class AuthenticationService(private val userRepository: UserRepository) {

    fun findUser(login: String): User? = userRepository.getUserByLogin(login)

    fun verifyPass(user: User, pass: String) = user.hash == generateHash(pass, user.salt)

    private fun generateHash(plaintext: String, salt: String) =
        MessageDigest.getInstance("SHA-256")
            .digest((plaintext + salt).toByteArray())
            .fold("", { str, it -> str + "%02x".format(it) })

}