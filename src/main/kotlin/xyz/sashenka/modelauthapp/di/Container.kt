package xyz.sashenka.modelauthapp.di

import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.loggerOf
import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.dao.SessionDAO
import xyz.sashenka.modelauthapp.dao.UserDAO
import xyz.sashenka.modelauthapp.repository.ResourceRepository
import xyz.sashenka.modelauthapp.repository.SessionRepository
import xyz.sashenka.modelauthapp.repository.UserRepository
import xyz.sashenka.modelauthapp.service.AccountingService
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.AuthorizationService
import xyz.sashenka.modelauthapp.service.DBService
import xyz.sashenka.modelauthapp.service.HelpService
import xyz.sashenka.modelauthapp.service.ValidatingService

class Container {
    private lateinit var dbService: DBService
    private lateinit var validatingService: ValidatingService

    fun getLogger(ofClass: Class<*>): KotlinLogger = loggerOf(ofClass)

    fun getDBService(): DBService {
        if (!::dbService.isInitialized) {
            dbService = DBService(getLogger(DBService::class.java))
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

    fun getUserDAO(): UserDAO? = getDBService().connection?.let { UserDAO(it) }

    fun getResourceDao(): ResourceDAO? = getDBService().connection?.let { ResourceDAO(it) }

    fun getSessionDAO(): SessionDAO? = getDBService().connection?.let { SessionDAO(it) }
}
