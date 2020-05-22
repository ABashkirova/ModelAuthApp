package xyz.sashenka.modelauthapp.repository

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.dao.UserDao

class UserRepositoryImpl(@Inject private val dao: UserDao) : UserRepository {
    override fun getUserByLogin(login: String) = dao.findUser(login)?.toPlain()
}
