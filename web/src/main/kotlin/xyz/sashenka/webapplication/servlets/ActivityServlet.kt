package xyz.sashenka.webapplication.servlets

import com.google.gson.Gson
import com.google.inject.Inject
import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import xyz.sashenka.modelauthapp.dao.SessionDAO
import xyz.sashenka.webapplication.di.logger.InjectLogger
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class ActivityServlet : HttpServlet() {
    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var sessionDAO: SessionDAO
    @InjectLogger
    lateinit var logger: KotlinLogger

    @Throws(ServletException::class, IOException::class)
    override fun service(request: HttpServletRequest, response: HttpServletResponse) {


        val query = request.queryString
        if(query.isNullOrEmpty()){
            val json = gson.toJson(sessionDAO.selectAll())
            response.writer.write(json)
        } else if (query.contains("id")) {
            try {
                val id = request.getParameter("id").toInt()
                val json = gson.toJson(sessionDAO.selectById(id))
                response.writer.write(json)
            } catch (e: NumberFormatException) {
                response.sendError(400, e.message)
            }
        } else {
            response.sendError(404)
        }

    }
}
