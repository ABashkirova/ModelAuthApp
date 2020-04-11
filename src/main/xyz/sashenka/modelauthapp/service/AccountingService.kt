package xyz.sashenka.modelauthapp.service

import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.DBAccess
import xyz.sashenka.modelauthapp.repository.SessionRepository

class AccountingService(private val sessionRepository: SessionRepository) {
    fun saveSession(access: DBAccess, session: UserSession) =
        sessionRepository.addSession(access, session)
}