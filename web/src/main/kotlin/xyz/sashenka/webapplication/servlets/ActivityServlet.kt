package xyz.sashenka.webapplication.servlets

import com.google.gson.Gson
import com.google.inject.Inject
import com.google.inject.Singleton
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class ActivityServlet : HttpServlet() {
    @Inject
    lateinit var gson: Gson

    @Throws(ServletException::class, IOException::class)
    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        val json = gson.toJson("ActivityServlet. Method: ${request.method}".split(" "))
        response.writer.write(json)
    }
}
