package xyz.sashenka.modelauthapp.di

import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.Provides
import com.google.inject.Singleton
import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.dao.SessionDAO
import xyz.sashenka.modelauthapp.dao.UserDAO
import xyz.sashenka.modelauthapp.service.DBService
import java.sql.Connection

class DatabaseModule : AbstractModule() {

    @Inject
    @Provides
    @Singleton
    fun createConnection(): Connection {
        val dbService = DBService()
        dbService.migrate()
        return dbService.getConnect()
    }

    @Inject
    @Provides
    @Singleton
    fun createUserDao(connection: Connection): UserDAO = UserDAO(connection)

    @Inject
    @Provides
    @Singleton
    fun createResourceDao(connection: Connection): ResourceDAO = ResourceDAO(connection)

    @Inject
    @Provides
    @Singleton
    fun createSessionDao(connection: Connection): SessionDAO = SessionDAO(connection)
}
