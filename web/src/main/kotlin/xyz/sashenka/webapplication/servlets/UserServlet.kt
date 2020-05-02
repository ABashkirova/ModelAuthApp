package xyz.sashenka.webapplication.servlets

import com.google.gson.Gson
import com.google.inject.Inject
import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import xyz.sashenka.modelauthapp.dao.UserDAO
import xyz.sashenka.webapplication.di.logger.InjectLogger
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class UserServlet : HttpServlet() {
    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var userDAO: UserDAO
    @InjectLogger
    lateinit var logger: KotlinLogger

    @Throws(ServletException::class, IOException::class)
    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        val json: String
        if (request.queryString == null) {
            json = gson.toJson(listOf(1, 2, 3, 4))
            response.writer.write(json)
        } else {
            val id = request.getParameter("id")
            if (id != null) {
                json = gson.toJson(userDAO.requestUserById(id.toInt()))
                response.writer.write(json)
            } else {
                response.sendError(404)
            }
        }
    }
}
