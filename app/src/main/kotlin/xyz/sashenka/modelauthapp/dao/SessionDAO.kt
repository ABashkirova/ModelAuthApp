package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.model.dto.db.DBUserSession
import xyz.sashenka.modelauthapp.utils.setValues
import java.sql.Connection
import java.sql.Date
import java.sql.ResultSet

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
    private val selectByAccessIdSql: String
        get() = "SELECT * FROM USER_SESSION WHERE ACCESS_ID=?"

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

    private val id = "ID"
    private val startDate = "START_DATE"
    private val endDate = "END_DATE"
    private val volume = "VOLUME"
    private val accessId = "ACCESS_ID"

    fun requestAll(): List<DBUserSession> {
        val listUserSessions: MutableList<DBUserSession> = mutableListOf()
        val statement = dbConnection.createStatement()
        statement.use {
            val resultSet = it.executeQuery(sessionSelectAllSql)
            while (resultSet.next()) {
                listUserSessions.add(
                    createUserSession(resultSet)
                )
            }
        }
        return listUserSessions.toList()
    }

    fun requestById(id: Int): DBUserSession? {
        var session: DBUserSession? = null
        val statement = dbConnection.prepareStatement(selectByIdSql)
        statement.use {
            it.setValues(id)
            val resultSet = it.executeQuery()
            if (resultSet.next()) {
                session = createUserSession(resultSet)
            }
        }
        return session
    }

    fun requestByAccessId(accessId: Int): List<DBUserSession> {
        val listUserSessions: MutableList<DBUserSession> = mutableListOf()
        val statement = dbConnection.prepareStatement(selectByAccessIdSql)
        statement.use {
            it.setValues(accessId)
            val resultSet = it.executeQuery()
            while (resultSet.next()) {
                listUserSessions.add(
                    createUserSession(resultSet)
                )
            }
        }
        return listUserSessions.toList()
    }

    private fun createUserSession(resultSet: ResultSet): DBUserSession {
        return DBUserSession(
            resultSet.getInt(id),
            resultSet.getInt(accessId),
            resultSet.getDate(startDate),
            resultSet.getDate(endDate),
            resultSet.getInt(volume)
        )
    }
}
