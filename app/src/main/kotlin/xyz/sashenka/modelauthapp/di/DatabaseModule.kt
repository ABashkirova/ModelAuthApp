package xyz.sashenka.modelauthapp.di

import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.persist.jpa.JpaPersistModule
import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.dao.SessionDAO
import xyz.sashenka.modelauthapp.dao.UserDao
import xyz.sashenka.modelauthapp.dao.UserDaoImpl
import xyz.sashenka.modelauthapp.service.DBService

class DatabaseModule : AbstractModule() {
    override fun configure() {
        super.configure()
        install(JpaPersistModule("AaaPersistenceUnit"))
        bind(UserDao::class.java).to(UserDaoImpl::class.java).`in`(Singleton::class.java)

        bind(DBService::class.java).toProvider(DatabaseProvider::class.java).`in`(Singleton::class.java)
    }

    @Inject
    @Provides
    @Singleton
    fun createResourceDao(dbService: DBService): ResourceDAO = ResourceDAO(dbService)

    @Inject
    @Provides
    @Singleton
    fun createSessionDao(dbService: DBService): SessionDAO = SessionDAO(dbService)
}
