package xyz.sashenka.webapplication

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext

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

            server.handler = root

            server.start()
            server.join()
        }
    }
}
