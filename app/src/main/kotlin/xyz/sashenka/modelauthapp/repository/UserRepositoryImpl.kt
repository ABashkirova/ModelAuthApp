package xyz.sashenka.modelauthapp.repository

import com.google.inject.Inject
import com.google.inject.Singleton
import xyz.sashenka.modelauthapp.dao.UserDao

@Singleton
class UserRepositoryImpl : UserRepository {
    @Inject
    lateinit var dao: UserDao

    override fun getUserByLogin(login: String) = dao.findUser(login)
}
