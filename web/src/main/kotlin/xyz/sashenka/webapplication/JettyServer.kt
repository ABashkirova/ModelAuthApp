package xyz.sashenka.webapplication

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletHolder
import org.eclipse.jetty.webapp.WebAppContext
import xyz.sashenka.webapplication.servlets.HelloServlet

class JettyServer {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val webappDirLocation = "src/main/webapp/"
            val webPort = System.getenv("PORT") ?: "8080"

            val server = Server(Integer.valueOf(webPort))
            val root = WebAppContext()

            root.contextPath = "/"
            root.descriptor = "$webappDirLocation/WEB-INF/web.xml"
            root.resourceBase = webappDirLocation
            root.isParentLoaderPriority = true
            root.addServlet(ServletHolder(HelloServlet()), "/hello")
            server.handler = root

            server.start()
            server.join()
        }
    }
}
