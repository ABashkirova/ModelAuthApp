package servlets

import com.google.inject.Singleton
import di.logger.InjectLogger
import org.apache.logging.log4j.kotlin.KotlinLogger
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class EchoServlet : HttpServlet() {
    @InjectLogger
    lateinit var logger: KotlinLogger

    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        logger.debug(
            "DoGet ->\n" +
                "RESPONCE: $response" + "REQUEST: $request"
        )

        if (request.requestURL.contains("/echo/get")) {
            val id = request.getParameter("id")
            request.setAttribute("id", id)
            request.getRequestDispatcher("response.jsp").forward(request, response)
        } else {
            logger.error("Page not found error from get")
            response.sendError(404, "Page not found error from get")
        }
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        logger.debug(
            "DoPost ->\n" +
                "RESPONCE: $response" + "REQUEST: $request"
        )

        if (request.requestURI.contains("/echo/post")) {
            val id = request.getParameter("id")
            response.sendRedirect("get?id=$id")
            println(response.toString())
        } else {
            logger.error("Page not found error from post")
            response.sendError(404, "Page not found error from post")
        }
    }
}
