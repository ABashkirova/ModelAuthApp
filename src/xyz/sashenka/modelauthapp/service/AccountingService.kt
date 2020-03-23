package xyz.sashenka.modelauthapp.service

import xyz.sashenka.modelauthapp.model.domain.UserSession

class AccountingService {
    private val userSessions = arrayListOf<UserSession>()

    fun write(session: UserSession) = userSessions.add(session)
}