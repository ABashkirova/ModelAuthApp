package servlets

import com.google.inject.Singleton
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class UserServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        response.writer.write("UserServlet. Method: ${request.method}")
    }
}
