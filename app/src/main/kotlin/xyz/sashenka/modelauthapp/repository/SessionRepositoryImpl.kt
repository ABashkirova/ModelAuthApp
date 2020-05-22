package xyz.sashenka.modelauthapp.repository

import com.google.inject.Inject
import com.google.inject.Singleton
import xyz.sashenka.modelauthapp.dao.SessionDao
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.model.dto.db.DBUserSession
import java.sql.Date

@Singleton
class SessionRepositoryImpl : SessionRepository {
    @Inject
    lateinit var sessionDao: SessionDao

    override fun addSession(access: DBAccess, session: UserSession) {
        val dbSession = DBUserSession()
        dbSession.access = access
        dbSession.dateStart = Date.valueOf(session.dateStart)
        dbSession.dateEnd = Date.valueOf(session.dateEnd)
        dbSession.volume = session.volume

        sessionDao.save(dbSession)
    }
}
