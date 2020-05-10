package xyz.sashenka.modelauthapp.repository

import xyz.sashenka.modelauthapp.dao.UserDaoImpl

class UserRepository(private val daoImpl: UserDaoImpl) {
    fun getUserByLogin(login: String) = daoImpl.requestUserByLogin(login)
}
