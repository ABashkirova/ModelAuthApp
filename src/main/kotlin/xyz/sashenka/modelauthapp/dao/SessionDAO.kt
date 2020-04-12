package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.DBAccess
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
        statement.setDate(1, Date.valueOf(session.dateStart))
        statement.setDate(2, Date.valueOf(session.dateEnd))
        statement.setInt(3, session.volume)
        statement.setInt(4, access.id)

        val result = statement.executeUpdate()
        statement.close()
        return result
    }
}
