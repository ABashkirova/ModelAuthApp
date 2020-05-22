package xyz.sashenka.modelauthapp.service

import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.model.dto.db.DBUser

interface AuthenticationService {
    fun findUser(login: String): DBUser?
    fun verifyPass(user: User, pass: String): Boolean
}
