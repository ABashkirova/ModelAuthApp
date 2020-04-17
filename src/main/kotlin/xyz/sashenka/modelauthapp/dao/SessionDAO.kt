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

    fun insert(access: DBAccess, session: UserSession): Int {
        val statement = dbConnection.prepareStatement(sessionInsertSql)
        return statement.use {
            it.setValues(
                Date.valueOf(session.dateStart),
                Date.valueOf(session.dateEnd),
                session.volume,
                access.id
            )
            val result = statement.executeUpdate()
            statement.close()
            return result
        }
    }
}
