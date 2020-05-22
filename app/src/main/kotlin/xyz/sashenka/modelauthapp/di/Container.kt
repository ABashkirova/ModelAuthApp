package xyz.sashenka.modelauthapp.di

import com.google.inject.Injector
import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.loggerOf
import xyz.sashenka.modelauthapp.controller.ArgHandler
import xyz.sashenka.modelauthapp.service.*

class Container(
    private val injector: Injector
) {

    fun getLogger(ofClass: Class<*>): KotlinLogger = loggerOf(ofClass)

    fun getArgHandler(): ArgHandler = injector.getInstance(ArgHandler::class.java)

    fun getValidatingService(): ValidatingService = injector.getInstance(ValidatingService::class.java)

    fun getHelpService(): HelpService = injector.getInstance(HelpService::class.java)

    fun getAuthenticationService(): AuthenticationService? = injector.getInstance(AuthenticationServiceImpl::class.java)

    fun getAuthorizationService(): AuthorizationService? = injector.getInstance(AuthorizationServiceImpl::class.java)

    fun getAccountingService(): AccountingService? = injector.getInstance(AccountingServiceImpl::class.java)
}
