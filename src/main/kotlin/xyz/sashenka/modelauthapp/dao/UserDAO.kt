package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.domain.User
import java.sql.Connection

class UserDAO(private val dbConnection: Connection) {
    private val userByLoginSql: String
        get() = "SELECT * FROM USER WHERE LOGIN=?;"

    fun requestUserByLogin(login: String): User? {

        val statement = dbConnection.prepareStatement(userByLoginSql)
        statement.setString(1, login)
        val value = statement.executeQuery()
        val user = when {
            value.next() -> User(
                value.getString("LOGIN"),
                value.getString("HASH_PASSWORD"),
                value.getString("SALT")
            )
            else -> null
        }
        statement.close()
        value.close()
        return user
    }
}
