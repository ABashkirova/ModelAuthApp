package di

import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Singleton
import com.google.inject.matcher.Matchers
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.ServletModule
import di.logger.Log4JTypeListener
import servlets.ActivityServlet
import servlets.AuthorityServlet
import servlets.EchoServlet
import servlets.UserServlet

class GuiceServletConfig : GuiceServletContextListener() {
    override fun getInjector(): Injector {
        return Guice.createInjector(object : ServletModule() {
            override fun configureServlets() {
                bindListener(Matchers.any(), Log4JTypeListener())
                serve("/echo/get").with(EchoServlet::class.java)
                serve("/echo/post").with(EchoServlet::class.java)
                serve("/ajax/user").with(UserServlet::class.java)
                serve("/ajax/authority").with(AuthorityServlet::class.java)
                serve("/ajax/activity").with(ActivityServlet::class.java)
            }
        })
    }
}
