package xyz.sashenka.modelauthapp.repository

import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess

interface SessionRepository {
    fun addSession(access: DBAccess, session: UserSession)
}
