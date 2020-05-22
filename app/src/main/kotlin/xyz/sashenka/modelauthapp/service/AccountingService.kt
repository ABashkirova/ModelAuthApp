package xyz.sashenka.modelauthapp.service

import com.google.inject.Inject
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.repository.SessionRepository

class AccountingService(
    @Inject private val sessionRepository: SessionRepository
) {
    fun saveSession(access: DBAccess, session: UserSession) =
        sessionRepository.addSession(access, session)
}
