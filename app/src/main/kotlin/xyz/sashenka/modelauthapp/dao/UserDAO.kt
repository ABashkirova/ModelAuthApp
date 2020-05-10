package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.dto.db.DBUser

interface UserDao {
    fun save(user: DBUser)
    fun getAll(): List<DBUser>
    fun findUserById(id: Int): DBUser?
    fun findUser(login: String): DBUser?
}
