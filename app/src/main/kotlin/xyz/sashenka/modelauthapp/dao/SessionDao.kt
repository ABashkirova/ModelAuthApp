package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.dto.db.DBUserSession

interface SessionDao {
    fun save(session: DBUserSession)
    fun getAll(): List<DBUserSession>
    fun findById(id: Int): DBUserSession?
    fun findByAccessId(accessId: Int): List<DBUserSession>
}
