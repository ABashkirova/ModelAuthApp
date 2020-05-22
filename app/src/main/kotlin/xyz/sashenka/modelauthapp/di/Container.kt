package xyz.sashenka.modelauthapp.di

import com.google.inject.Injector
import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.loggerOf
import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.dao.UserDao
import xyz.sashenka.modelauthapp.dao.SessionDao
import xyz.sashenka.modelauthapp.dao.ResourceDao
import xyz.sashenka.modelauthapp.repository.ResourceRepository
import xyz.sashenka.modelauthapp.repository.ResourceRepositoryImpl
import xyz.sashenka.modelauthapp.repository.SessionRepository
import xyz.sashenka.modelauthapp.repository.UserRepository
import xyz.sashenka.modelauthapp.service.AuthenticationService
import xyz.sashenka.modelauthapp.service.AuthorizationService
import xyz.sashenka.modelauthapp.service.HelpService
import xyz.sashenka.modelauthapp.service.ValidatingService
import xyz.sashenka.modelauthapp.service.AccountingService

class Container(
    private val injector: Injector
) {
    private lateinit var validatingService: ValidatingService

    fun getLogger(ofClass: Class<*>): KotlinLogger = loggerOf(ofClass)

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

    fun getUserRepository(): UserRepository? = getUserDao()?.let { UserRepository(it) }

    fun getResourceRepository(): ResourceRepository? = getResourceDao()?.let { ResourceRepositoryImpl(it) }

    fun getSessionRepository(): SessionRepository? = getSessionDao()?.let { SessionRepository(it) }

    fun getUserDao(): UserDao? = injector.getInstance(UserDao::class.java)

    fun getResourceDao(): ResourceDao? = injector.getInstance(ResourceDao::class.java)

    fun getSessionDao(): SessionDao? = injector.getInstance(SessionDao::class.java)
}
