package xyz.sashenka.modelauthapp.repository

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.dao.UserDao

class UserRepository(@Inject private val dao: UserDao) {
    fun getUserByLogin(login: String) = dao.findUser(login)?.toPlain()
}
