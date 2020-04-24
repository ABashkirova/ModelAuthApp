import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.ServletModule
import servlets.ActivityServlet
import servlets.EchoServlet
import servlets.AuthorityServlet
import servlets.UserServlet


class MyGuiceServletConfig : GuiceServletContextListener() {

    override fun getInjector(): Injector {
            return Guice.createInjector(object: ServletModule() {
                override fun configureServlets() {
                    serve("/echo/*").with(EchoServlet::class.java)
                    serve("/ajax/user").with(UserServlet::class.java)
                    serve("/ajax/authority").with(AuthorityServlet::class.java)
                    serve("/ajax/activity").with(ActivityServlet::class.java)
                }
            })
    }

}
