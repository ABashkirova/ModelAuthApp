package xyz.sashenka.modelauthapp.di

import com.google.inject.Provider
import xyz.sashenka.modelauthapp.service.db.DBService

class DatabaseProvider : Provider<DBService?> {
    override fun get(): DBService? {
        val service = DBService()
        service.migrate()
        return service
    }
}
