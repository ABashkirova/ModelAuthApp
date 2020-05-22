package xyz.sashenka.modelauthapp.repository

import xyz.sashenka.modelauthapp.model.domain.User

interface UserRepository {
    fun getUserByLogin(login: String): User?
}
