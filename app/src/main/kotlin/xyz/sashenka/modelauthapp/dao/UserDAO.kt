package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.utils.setValues
import java.sql.Connection

class UserDAO(private val dbConnection: Connection) {
    private val userByLoginSql: String
        get() = "SELECT * FROM USER WHERE LOGIN=?;"

    fun requestUserByLogin(login: String): User? {
        val statement = dbConnection.prepareStatement(userByLoginSql)
        return statement.use {
            it.setValues(login)
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

    private fun createUser(value: ResultSet): DBUser {
        return DBUser(
            value.getInt(id),
            value.getString(login),
            value.getString(hashPassword),
            value.getString(salt)
        )
    }

    fun requestUserByLogin(login: String): User? {
        return requestUser(userByLoginSql, login)?.toPlain()
    }

    fun requestUserById(id: Int): DBUser? {
        return requestUser(userByIdSql, id)
    }
}
