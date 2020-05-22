package xyz.sashenka.modelauthapp.service

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.repository.UserRepository
import xyz.sashenka.modelauthapp.utils.SecureUtils

class AuthenticationServiceImpl(
    @Inject private val userRepository: UserRepository
) : AuthenticationService {
    override fun findUser(login: String): User? =
        userRepository.getUserByLogin(login)

    override fun verifyPass(user: User, pass: String) =
        user.hash == SecureUtils.generateHash(pass, user.salt)
}
