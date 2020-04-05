package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.service.DBService

class UserDAO(private val db: DBService) {
    private val userByLoginSql = "SELECT * FROM USER WHERE LOGIN=?;"

    fun getUserByLogin(login: String): User? {
        return db.inConnect {

            val statement = it.prepareStatement(userByLoginSql)
            statement.setString(1, login)

            val value = statement.executeQuery()

            when {
                value.next() -> User(
                    value.getString("LOGIN"),
                    value.getString("HASH_PASSWORD"),
                    value.getString("SALT")
                )
                else -> null
            }
        }
    }
}