package xyz.sashenka.modelauthapp.service

import com.google.inject.Inject
import com.google.inject.Singleton
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.repository.UserRepository
import xyz.sashenka.modelauthapp.utils.SecureUtils

@Singleton
class AuthenticationServiceImpl : AuthenticationService {
    @Inject
    lateinit var userRepository: UserRepository

    override fun findUser(login: String): User? =
        userRepository.getUserByLogin(login)

    override fun verifyPass(user: User, pass: String) =
        user.hash == SecureUtils.generateHash(pass, user.salt)
}
