package xyz.sashenka.modelauthapp.service

import xyz.sashenka.modelauthapp.model.User
import xyz.sashenka.modelauthapp.repository.UserRepository
import xyz.sashenka.modelauthapp.utils.SecureUtils

class AuthenticationService(private val userRepository: UserRepository) {
    fun findUser(login: String): User? = userRepository.getUserByLogin(login)

    fun verifyPass(user: User, pass: String) = user.hash == SecureUtils.generateHash(pass, user.salt)
}