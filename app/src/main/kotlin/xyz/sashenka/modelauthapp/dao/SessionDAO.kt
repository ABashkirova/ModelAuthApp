package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.DBAccess
import xyz.sashenka.modelauthapp.utils.setValues
import java.sql.Connection
import java.sql.Date

class SessionDAO(private val dbConnection: Connection) {
    private val sessionInsertSql: String
        get() = """
            INSERT INTO USER_SESSION(ID, START_DATE, END_DATE, VOLUME, ACCESS_ID)
            VALUES(default, ?, ?, ?, ?)
            """
    private val sessionSelectAllSql: String
        get() = "SELECT * FROM USER_SESSION"
    private val selectByIdSql: String
        get() = "SELECT * FROM USER_SESSION WHERE ID=?"

    fun insert(access: DBAccess, session: UserSession): Int {
        val statement = dbConnection.prepareStatement(sessionInsertSql)
        return statement.use {
            it.setValues(
                Date.valueOf(session.dateStart),
                Date.valueOf(session.dateEnd),
                session.volume,
                access.id
            )
            return@use it.executeUpdate()
        }
    }

    fun selectAll(): List<UserSession> {
        val result: MutableList<UserSession> = mutableListOf()
        var statement = dbConnection.createStatement()
        statement.use {
            val resultSet = it.executeQuery(sessionSelectAllSql)
            while (resultSet.next()) {
                val accessId = resultSet.getInt("ACCESS_ID")
                lateinit var resource: String
                var userId: Int? = null
                var stmt = dbConnection.createStatement()
                stmt.use {
                    val access = it.executeQuery("SELECT USER_ID, RESOURCE FROM ACCESS WHERE ID=$accessId")
                    access.next()
                    resource = access.getString("RESOURCE")
                    userId = access.getInt("USER_ID")
                }
                lateinit var userLogin: String
                stmt = dbConnection.createStatement()
                stmt.use {
                    val user = it.executeQuery("SELECT LOGIN FROM USER WHERE ID=$userId")
                    user.next()
                    userLogin = user.getString("LOGIN")
                }
                result.add(
                    UserSession(
                        userLogin,
                        resource.substring(0, resource.length - 1),
                        resultSet.getDate("START_DATE").toString(),
                        resultSet.getDate("END_DATE").toString(),
                        resultSet.getInt("VOLUME")
                    )
                )
            }
        }
        return result.toList()
    }

    fun selectById(id: Int): UserSession? {
        var result: UserSession? = null
        val statement = dbConnection.prepareStatement(selectByIdSql)
        statement.use {
            it.setValues(id)
            val resultSet = it.executeQuery()
            while (resultSet.next()) {
                val accessId = resultSet.getInt("ACCESS_ID")
                lateinit var resource: String
                var userId: Int? = null
                var stmt = dbConnection.createStatement()
                stmt.use {
                    val access = it.executeQuery("SELECT USER_ID, RESOURCE FROM ACCESS WHERE ID=$accessId")
                    access.next()
                    resource = access.getString("RESOURCE")
                    userId = access.getInt("USER_ID")
                }
                lateinit var userLogin: String
                stmt = dbConnection.createStatement()
                stmt.use {
                    val user = it.executeQuery("SELECT LOGIN FROM USER WHERE ID=$userId")
                    user.next()
                    userLogin = user.getString("LOGIN")
                }
                result = UserSession(
                    userLogin,
                    resource.substring(0, resource.length - 1),
                    resultSet.getDate("START_DATE").toString(),
                    resultSet.getDate("END_DATE").toString(),
                    resultSet.getInt("VOLUME")
                )
            }
        }
        return result
    }
}
