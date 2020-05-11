package xyz.sashenka.modelauthapp.di

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.google.inject.persist.jpa.JpaPersistModule
import xyz.sashenka.modelauthapp.dao.*

class DatabaseModule : AbstractModule() {
    override fun configure() {
        super.configure()
        install(JpaPersistModule("AaaPersistenceUnit"))
        bind(PersistenceInitializer::class.java).asEagerSingleton()
        bind(UserDao::class.java).to(UserDaoImpl::class.java).`in`(Singleton::class.java)
        bind(ResourceDao::class.java).to(ResourceDaoImpl::class.java).`in`(Singleton::class.java)
        bind(SessionDao::class.java).to(SessionDaoImpl::class.java).`in`(Singleton::class.java)
    }
}
