package xyz.sashenka.modelauthapp.dao

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.model.dto.db.DBUserSession
import xyz.sashenka.modelauthapp.service.db.DBService
import xyz.sashenka.modelauthapp.utils.setValues
import java.sql.Date
import java.sql.ResultSet

class SessionDAO(
    @Inject
    var dbService: DBService
) {
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

    private val id = "ID"
    private val startDate = "START_DATE"
    private val endDate = "END_DATE"
    private val volume = "VOLUME"
    private val accessId = "ACCESS_ID"

    fun insert(access: DBAccess, session: UserSession): Int {
        dbService.provideConnect().use { connect ->
            val statement = connect.prepareStatement(sessionInsertSql)
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
    }

    fun requestAll(): List<DBUserSession> {
        dbService.provideConnect().use { connect ->
            val listUserSessions: MutableList<DBUserSession> = mutableListOf()
            val statement = connect.createStatement()
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
    }

    fun requestById(id: Int): DBUserSession? {
        dbService.provideConnect().use { connect ->
            var session: DBUserSession? = null
            val statement = connect.prepareStatement(selectByIdSql)
            statement.use {
                it.setValues(id)
                val resultSet = it.executeQuery()
                if (resultSet.next()) {
                    session = createUserSession(resultSet)
                }
            }
            return session
        }
    }

    fun requestByAccessId(accessId: Int): List<DBUserSession> {
        dbService.provideConnect().use { connect ->
            val listUserSessions: MutableList<DBUserSession> = mutableListOf()
            val statement = connect.prepareStatement(selectByAccessIdSql)
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
