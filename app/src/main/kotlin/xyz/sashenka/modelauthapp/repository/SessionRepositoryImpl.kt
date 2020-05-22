package xyz.sashenka.modelauthapp.repository

import com.google.inject.Inject
import com.google.inject.persist.Transactional
import xyz.sashenka.modelauthapp.dao.SessionDao
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess

class SessionRepositoryImpl(@Inject private val sessionDao: SessionDao) : SessionRepository {
    @Transactional
    override fun addSession(access: DBAccess, session: UserSession) {
        sessionDao.save(access, session)
    }
}
