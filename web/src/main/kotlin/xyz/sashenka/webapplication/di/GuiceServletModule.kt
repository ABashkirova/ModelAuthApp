package xyz.sashenka.webapplication.di

import com.google.inject.matcher.Matchers
import com.google.inject.servlet.ServletModule
import xyz.sashenka.webapplication.di.logger.Log4JTypeListener
import xyz.sashenka.webapplication.filters.GeneralFilter
import xyz.sashenka.webapplication.servlets.*

class GuiceServletModule : ServletModule() {
    override fun configureServlets() {
        super.configureServlets()
        bindListener(
            Matchers.any(),
            Log4JTypeListener()
        )
        filter("/*").through(GeneralFilter::class.java)
        serve("/hello").with(HelloServlet::class.java)
        serve("/echo/get").with(EchoServlet::class.java)
        serve("/echo/post").with(EchoServlet::class.java)
        serve("/ajax/user").with(UserServlet::class.java)
        serve("/ajax/authority").with(AuthorityServlet::class.java)
        serve("/ajax/activity").with(ActivityServlet::class.java)
    }
}
