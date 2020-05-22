package xyz.sashenka.modelauthapp.repository

import com.google.inject.Inject
import com.google.inject.Singleton
import xyz.sashenka.modelauthapp.dao.SessionDao
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess

@Singleton
class SessionRepositoryImpl : SessionRepository {
    @Inject
    lateinit var sessionDao: SessionDao

    override fun addSession(access: DBAccess, session: UserSession) {
        sessionDao.save(access, session)
    }
}
