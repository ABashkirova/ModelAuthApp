package xyz.sashenka.modelauthapp.di

import com.google.inject.Guice
import com.google.inject.Injector
import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.loggerOf
import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.dao.SessionDAO
import xyz.sashenka.modelauthapp.dao.UserDAO
import xyz.sashenka.modelauthapp.repository.ResourceRepository
import xyz.sashenka.modelauthapp.repository.SessionRepository
import xyz.sashenka.modelauthapp.repository.UserRepository
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.AuthorizationService
import xyz.sashenka.modelauthapp.service.HelpService
import xyz.sashenka.modelauthapp.service.ValidatingService
import xyz.sashenka.modelauthapp.service.AccountingService
import xyz.sashenka.modelauthapp.service.db.DBService

class Container {
    private val injector: Injector = Guice.createInjector(DatabaseModule())
    private lateinit var dbService: DBService
    private lateinit var validatingService: ValidatingService

    fun getLogger(ofClass: Class<*>): KotlinLogger = loggerOf(ofClass)

    fun getDBService(): DBService {
        if (!::dbService.isInitialized) {
            dbService = injector.getInstance(DBService::class.java)
        }
        return dbService
    }

    fun getArgHandler(args: Array<String>): ArgHandler = ArgHandler(args)

    fun getValidatingService(): ValidatingService {
        if (!::validatingService.isInitialized) {
            validatingService = ValidatingService()
        }
        return validatingService
    }

    fun getHelpService(): HelpService = HelpService()

    fun getAuthenticationService(): AuthenticationService? = getUserRepository()?.let { AuthenticationService(it) }

    fun getAuthorizationService(): AuthorizationService? = getResourceRepository()?.let { AuthorizationService(it) }

    fun getAccountingService(): AccountingService? = getSessionRepository()?.let { AccountingService(it) }

    fun getUserRepository(): UserRepository? = getUserDAO()?.let { UserRepository(it) }

    fun getResourceRepository(): ResourceRepository? = getResourceDao()?.let { ResourceRepository(it) }

    fun getSessionRepository(): SessionRepository? = getSessionDAO()?.let { SessionRepository(it) }

    fun getUserDAO(): UserDAO? = injector.getInstance(UserDAO::class.java)

    fun getResourceDao(): ResourceDAO? = injector.getInstance(ResourceDAO::class.java)

    fun getSessionDAO(): SessionDAO? = injector.getInstance(SessionDAO::class.java)
}
