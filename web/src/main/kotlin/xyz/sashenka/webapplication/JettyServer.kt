package xyz.sashenka.webapplication

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.webapp.Configuration
import org.eclipse.jetty.webapp.WebAppContext
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
            val root = WebAppContext()

            root.contextPath = "/"
            root.descriptor = "$webappDirLocation/WEB-INF/web.xml"
            root.resourceBase = webappDirLocation
            root.isParentLoaderPriority = true
            root.addServlet(ServletHolder(HelloServlet()), "/hello")
            root.isParentLoaderPriority = true
            server.handler = root

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
