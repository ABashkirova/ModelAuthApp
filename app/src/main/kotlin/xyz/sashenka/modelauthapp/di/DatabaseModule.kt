package xyz.sashenka.modelauthapp.di

import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.Provides
import com.google.inject.Singleton
import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.dao.SessionDAO
import xyz.sashenka.modelauthapp.dao.UserDAO
import xyz.sashenka.modelauthapp.service.db.DBService

class DatabaseModule : AbstractModule() {
    override fun configure() {
        super.configure()
        bind(DBService::class.java).toProvider(DatabaseProvider::class.java).`in`(Singleton::class.java)
    }

    @Inject
    @Provides
    @Singleton
    fun createUserDao(dbService: DBService): UserDAO = UserDAO(dbService)

    @Inject
    @Provides
    @Singleton
    fun createResourceDao(dbService: DBService): ResourceDAO = ResourceDAO(dbService)

    @Inject
    @Provides
    @Singleton
    fun createSessionDao(dbService: DBService): SessionDAO = SessionDAO(dbService)
}
