import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.matcher.Matchers
import com.google.inject.name.Named
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.RequestScoped
import com.google.inject.servlet.ServletModule
import di.logger.Log4JTypeListener
import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.logger
import servlets.EchoServlet


class GuiceServletConfig : GuiceServletContextListener() {
    override fun getInjector(): Injector {
        return Guice.createInjector(object : ServletModule() {
            override fun configureServlets() {

                bindListener(Matchers.any(), Log4JTypeListener())
                serve("/echo/get").with(EchoServlet::class.java)
                serve("/echo/post").with(EchoServlet::class.java)

                bind(EchoServlet::class.java).`in`(Singleton::class.java)
            }
        })
    }
}
