package xyz.sashenka.modelauthapp.repository

import xyz.sashenka.modelauthapp.model.dto.db.DBUser

interface UserRepository {
    fun getUserByLogin(login: String): DBUser?
}
