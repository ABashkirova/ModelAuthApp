package xyz.sashenka.modelauthapp.repository

import xyz.sashenka.modelauthapp.dao.SessionDAO
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.DBAccess

class SessionRepository(private val sessionDAO: SessionDAO) {
    fun addSession(access: DBAccess, session: UserSession) {
        sessionDAO.insert(access, session)
    }
}