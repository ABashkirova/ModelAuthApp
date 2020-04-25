package xyz.sashenka.webapplication

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.webapp.Configuration
import org.eclipse.jetty.webapp.WebAppContext
import xyz.sashenka.webapplication.di.GuiceServletConfig
import xyz.sashenka.webapplication.servlets.HelloServlet


class JettyServer {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val webappDirLocation = "src/main/webapp/"
            val webPort = System.getenv("PORT") ?: "8080"

            val server = Server()

            val connector = ServerConnector(server)
            connector.port = Integer.valueOf(webPort)
            server.addConnector(connector)
            val context = WebAppContext()

            context.contextPath = "/"
            context.descriptor = "$webappDirLocation/WEB-INF/web.xml"
            context.resourceBase = webappDirLocation
            context.isParentLoaderPriority = true
            context.addServlet(ServletHolder(HelloServlet()), "/hello")
            context.addServlet(DefaultServlet::class.java, "/")
            context.addEventListener(GuiceServletConfig())
            context.isParentLoaderPriority = true
            server.handler = context

            val classList: Configuration.ClassList = Configuration.ClassList
                .setServerDefault(server)
            classList.addBefore(
                "org.eclipse.jetty.webapp.JettyWebXmlConfiguration",
                "org.eclipse.jetty.annotations.AnnotationConfiguration"
            )

            println("${server.uri}")
            server.start()
            server.join()
        }
    }
}
