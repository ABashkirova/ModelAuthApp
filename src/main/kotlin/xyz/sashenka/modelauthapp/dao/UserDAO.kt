package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.domain.User
import java.sql.Connection

class UserDAO(private val dbConnection: Connection) {
    private val userByLoginSql: String
        get() = "SELECT * FROM USER WHERE LOGIN=?;"

    fun requestUserByLogin(login: String): User? {
        val statement = dbConnection.prepareStatement(userByLoginSql)
        return statement.use {
            it.setString(1, login)
            return@use it.executeQuery().use { value ->
                return@use when {
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
}
