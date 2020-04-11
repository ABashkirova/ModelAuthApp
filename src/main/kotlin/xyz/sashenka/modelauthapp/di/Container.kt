package xyz.sashenka.modelauthapp.di

import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.logger
import org.apache.logging.log4j.kotlin.loggerOf
import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.dao.SessionDAO
import xyz.sashenka.modelauthapp.dao.UserDAO
import xyz.sashenka.modelauthapp.repository.ResourceRepository
import xyz.sashenka.modelauthapp.repository.SessionRepository
import xyz.sashenka.modelauthapp.repository.UserRepository
import xyz.sashenka.modelauthapp.service.*

class Container {
    private lateinit var dbService: DBService
    private lateinit var validatingService: ValidatingService

    fun getLogger(ofClass: Class<*>): KotlinLogger {
        return loggerOf(ofClass)
    }

    fun getLogger(): KotlinLogger {
        return logger()
    }

    fun getDBService(): DBService {
        if (!::dbService.isInitialized) {
            dbService = DBService(getLogger(DBService::class.java))
        }
        return dbService
    }

    fun getArgHandler(args: Array<String>): ArgHandler {
        return ArgHandler(args)
    }

    fun getValidatingService(): ValidatingService {
        if (!::validatingService.isInitialized) {
            validatingService = ValidatingService()
        }
        return validatingService
    }

    fun getHelpService(): HelpService = HelpService()

    fun getAuthenticationService(): AuthenticationService? {
        return getUserRepository()?.let { AuthenticationService(it) }
    }

    fun getAuthorizationService(): AuthorizationService? {
        return getResourceRepository()?.let { AuthorizationService(it) }
    }

    fun getAccountingService(): AccountingService? {
        return getSessionRepository()?.let { AccountingService(it) }
    }

    fun getUserRepository(): UserRepository? {
        return getUserDAO()?.let { UserRepository(it) }
    }

    fun getResourceRepository(): ResourceRepository? {
        return getResourceDao()?.let { ResourceRepository(it) }
    }

    fun getSessionRepository(): SessionRepository? {
        return getSessionDAO()?.let { SessionRepository(it) }
    }

    fun getUserDAO(): UserDAO? {
        return getDBService().connection?.let { UserDAO(it) }
    }

    fun getResourceDao(): ResourceDAO? {
        return getDBService().connection?.let { ResourceDAO(it) }
    }

    fun getSessionDAO(): SessionDAO? {
        return getDBService().connection?.let { SessionDAO(it) }
    }
}