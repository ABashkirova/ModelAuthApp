package xyz.sashenka.modelauthapp.dao

import xyz.sashenka.modelauthapp.model.dto.db.DBAccess

interface ResourceDao {
    fun save(user: DBAccess)
    fun getAll(): List<DBAccess>
    fun findById(id: Int): DBAccess?
    fun findByUserId(userId: Int): List<DBAccess>
    fun find(login: String, resource: String, role: String): DBAccess?
}
