package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.utils.setValues
import java.sql.Connection

class UserDAO(private val dbConnection: Connection) {
    private val userByLoginSql: String
        get() = "SELECT * FROM USER WHERE LOGIN=?;"
    private val userByIdSql: String
        get() = "SELECT * FROM USER WHERE ID=?;"
    private val allUsersSql: String
        get() = "SELECT * FROM USER"

    fun requestAllUsers(): List<User> {
        val statement = dbConnection.createStatement()
        val result: MutableList<User> = mutableListOf()
        statement.use {
            val resultSet = statement.executeQuery(allUsersSql)
            while (resultSet.next()) {
                result.add(
                    User(
                        resultSet.getInt("ID"),
                        resultSet.getString("LOGIN"),
                        resultSet.getString("HASH_PASSWORD"),
                        resultSet.getString("SALT")
                    )
                )
            }
        }
        return result.toList()
    }

    private fun requestUser(sql: String, parameter: Any): User? {
        val statement = dbConnection.prepareStatement(sql)
        return statement.use {
            it.setValues(parameter)
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

    fun requestUserByLogin(login: String): User? {
        return requestUser(userByLoginSql, login)
    }

    fun requestUserById(id: Int): User? {
        return requestUser(userByIdSql, id)
    }

}
