package xyz.sashenka.modelauthapp.service

import xyz.sashenka.modelauthapp.model.domain.User

interface AuthenticationService {
    fun findUser(login: String): User?
    fun verifyPass(user: User, pass: String): Boolean
}
