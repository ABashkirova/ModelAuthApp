package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.utils.setValues
import java.sql.Connection

class UserDAO(private val dbConnection: Connection) {
    private val userByLoginSql: String
        get() = "SELECT * FROM USER WHERE LOGIN=?;"
    private val userByIdSql: String
        get() = "SELECT * FROM USER WHERE ID=?;"

    fun requestUserByLogin(login: String): User? {
        val statement = dbConnection.prepareStatement(userByLoginSql)
        return statement.use {
            it.setValues(login)
            return@use it.executeQuery().use { value ->
                return@use when {
                    value.next() -> User(
                        value.getInt("ID"),
                        value.getString("LOGIN"),
                        value.getString("HASH_PASSWORD"),
                        value.getString("SALT")
                    )
                    else -> null
                }
            }
        }
    }
    
    //TODO: подумать над своим поведением и дублированием кода
    fun requestUserById(id: Int): User? {
        val statement = dbConnection.prepareStatement(userByIdSql)
        return statement.use {
            it.setValues(id)
            return@use it.executeQuery().use { value ->
                return@use when {
                    value.next() -> User(
                        value.getInt("ID"),
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
