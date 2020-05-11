package xyz.sashenka.webapplication.di

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.servlet.GuiceServletContextListener
import xyz.sashenka.modelauthapp.di.DatabaseModule
import javax.servlet.annotation.WebListener

@WebListener
class GuiceServletConfig : GuiceServletContextListener() {
    override fun getInjector(): Injector {
        return Guice.createInjector(DatabaseModule(), GuiceServletModule())
    }
}
