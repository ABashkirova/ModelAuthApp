package xyz.sashenka.modelauthapp.di

import com.google.inject.AbstractModule
import com.google.inject.Singleton
import xyz.sashenka.modelauthapp.Application
import xyz.sashenka.modelauthapp.dao.*
import xyz.sashenka.modelauthapp.repository.*
import xyz.sashenka.modelauthapp.service.*

class ApplicationModule : AbstractModule() {

    override fun configure() {
        super.configure()

        bind(UserDao::class.java).to(UserDaoImpl::class.java).`in`(Singleton::class.java)
        bind(ResourceDao::class.java).to(ResourceDaoImpl::class.java).`in`(Singleton::class.java)
        bind(SessionDao::class.java).to(SessionDaoImpl::class.java).`in`(Singleton::class.java)

        bind(SessionRepository::class.java).to(SessionRepositoryImpl::class.java).`in`(Singleton::class.java)
        bind(UserRepository::class.java).to(UserRepositoryImpl::class.java).`in`(Singleton::class.java)
        bind(ResourceRepository::class.java).to(ResourceRepositoryImpl::class.java).`in`(Singleton::class.java)

        bind(AuthenticationService::class.java).to(AuthenticationServiceImpl::class.java).`in`(Singleton::class.java)
        bind(AuthorizationService::class.java).to(AuthorizationServiceImpl::class.java).`in`(Singleton::class.java)
        bind(AccountingService::class.java).to(AccountingServiceImpl::class.java).`in`(Singleton::class.java)
        bind(HelpService::class.java).`in`(Singleton::class.java)
        bind(ValidatingService::class.java).`in`(Singleton::class.java)

        bind(Application::class.java).`in`(Singleton::class.java)
    }
}
