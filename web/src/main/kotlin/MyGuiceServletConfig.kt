import com.google.inject.Guice
import com.google.inject.Injector
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.ServletModule

import servlets.EchoServlet


class MyGuiceServletConfig : GuiceServletContextListener() {

    override fun getInjector(): Injector {
            return Guice.createInjector(object: ServletModule() {
                override fun configureServlets() {
                    serve("/*").with(EchoServlet::class.java)
                }
            })
    }

}