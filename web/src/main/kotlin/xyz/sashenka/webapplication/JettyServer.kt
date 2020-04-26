package xyz.sashenka.webapplication

import com.google.inject.servlet.GuiceFilter
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.Configuration
import org.eclipse.jetty.webapp.WebAppContext
import xyz.sashenka.webapplication.di.GuiceServletConfig
import java.util.EnumSet.allOf
import javax.servlet.DispatcherType

class JettyServer {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val webPort = System.getenv("PORT") ?: "8080"
            val server = Server(webPort.toInt())

            server.handler = getContextHandler()

            val classList: Configuration.ClassList = Configuration.ClassList
                .setServerDefault(server)
            classList.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration"
            )

            try {
                server.start()
                server.join()
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }

        fun getContextHandler(): WebAppContext {
            val webappDirLocation = "web/src/main/webapp/"

            val servletContext = WebAppContext()
            servletContext.contextPath = "/"
            servletContext.resourceBase = webappDirLocation
            servletContext.descriptor = "$webappDirLocation/WEB-INF/web.xml"

            val guiceServletConfig = GuiceServletConfig()
            servletContext.addEventListener(guiceServletConfig)
            servletContext.addFilter(GuiceFilter::class.java, "/*", allOf(DispatcherType::class.java))

            return servletContext
        }
    }
}
