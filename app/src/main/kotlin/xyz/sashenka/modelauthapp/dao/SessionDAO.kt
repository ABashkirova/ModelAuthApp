package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.modelauthapp.model.dto.db.DBAccess
import xyz.sashenka.modelauthapp.model.dto.db.DBUserSession

interface SessionDao {
    fun save(access: DBAccess, session: UserSession)
    fun getAll(): List<DBUserSession>
    fun findById(id: Int): DBUserSession?
    fun findByAccessId(accessId: Int): List<DBUserSession>
}
