import org.apache.logging.log4j.kotlin.loggerOf
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(
    name = "EchoServlet",
    value = ["/echo/post", "/echo/post/",
        "/echo/get", "/echo/get/",
        "/echo", "/echo/"
    ]
)
class EchoServlet : HttpServlet() {
    val logger = loggerOf(EchoServlet::class.java)

    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        logger.debug {
            "DoGet ->\n" +
                "RESPONCE: $response" + "REQUEST: $request"
        }

        if (request.requestURL.contains("/echo/get")) {
            val id = request.getParameter("id")
            request.setAttribute("id", id)
            request.getRequestDispatcher("response.jsp").forward(request, response)
        } else {
            logger.error { "Page not found error from get" }
            response.sendError(404, "Page not found error from get")
        }
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        logger.debug {
            "DoPost ->\n" +
                "RESPONCE: $response" + "REQUEST: $request"
        }

        if (request.requestURI.contains("/echo/post")) {
            val id = request.getParameter("id")
            response.sendRedirect("get?id=$id")
            println(response.toString())
        } else {
            logger.error { "Page not found error from post" }
            response.sendError(404, "Page not found error from post")
        }

    }
}
