package xyz.sashenka.modelauthapp.service

import com.google.inject.Inject
import com.google.inject.Singleton
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.repository.SessionRepository

@Singleton
class AccountingServiceImpl : AccountingService {
    @Inject
    lateinit var sessionRepository: SessionRepository

    override fun saveSession(access: DBAccess, session: UserSession) =
        sessionRepository.addSession(access, session)
}
