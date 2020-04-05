package xyz.sashenka.modelauthapp.repository

import xyz.sashenka.modelauthapp.dao.UserDAO

class UserRepository(private val dao: UserDAO) {
    fun getUserByLogin(login: String) = dao.getUserByLogin(login)
}