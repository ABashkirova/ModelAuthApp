package xyz.sashenka.modelauthapp.service

import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess

interface AccountingService {
    fun saveSession(access: DBAccess, session: UserSession)
}
