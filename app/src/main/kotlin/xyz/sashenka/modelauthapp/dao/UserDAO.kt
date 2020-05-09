package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.modelauthapp.model.dto.db.DBUser
import xyz.sashenka.modelauthapp.service.db.DBService
import xyz.sashenka.modelauthapp.utils.setValues
import java.sql.ResultSet

class UserDAO(
    @Inject
    var dbService: DBService
) {

    private val userByLoginSql: String
        get() = "SELECT * FROM USER WHERE LOGIN=?;"
    private val userByIdSql: String
        get() = "SELECT * FROM USER WHERE ID=?;"
    private val allUsersSql: String
        get() = "SELECT * FROM USER"

    private val id = "ID"
    private val login = "LOGIN"
    private val hashPassword = "HASH_PASSWORD"
    private val salt = "SALT"

    fun requestAllUsers(): List<DBUser> {
        dbService.provideConnect().use { connect ->
            val statement = connect.createStatement()
            val result: MutableList<DBUser> = mutableListOf()
            statement.use {
                val resultSet = statement.executeQuery(allUsersSql)
                while (resultSet.next()) {
                    result.add(createUser(resultSet))
                }
            }
            return result.toList()
        }
    }

    private fun requestUser(sql: String, parameter: Any): DBUser? {
        dbService.provideConnect().use { connect ->
            val statement = connect.prepareStatement(sql)
            return statement.use {
                it.setValues(parameter)
                return@use it.executeQuery().use { value ->
                    return@use when {
                        value.next() -> createUser(value)
                        else -> null
                    }
                }
            }
            return null
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
