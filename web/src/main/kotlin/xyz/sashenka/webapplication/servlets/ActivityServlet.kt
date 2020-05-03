package xyz.sashenka.webapplication.servlets

import com.google.gson.Gson
import com.google.inject.Inject
import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import xyz.sashenka.modelauthapp.dao.SessionDAO
import xyz.sashenka.modelauthapp.model.domain.UserSession
import xyz.sashenka.webapplication.di.logger.InjectLogger
import xyz.sashenka.webapplication.servlets.HandleError.Companion.sendErrorNotFound
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

    private val ID = "id"
    private val ACCESS_ID = "accessId"

    @Throws(ServletException::class, IOException::class)
    override fun service(request: HttpServletRequest, response: HttpServletResponse) {

        val query = request.queryString
        when {
            query.isNullOrEmpty() -> response.writer.write(allSessionToJson())
            query.contains(ID) -> handleRequestWithIdParameter(request, response)
            query.contains(ACCESS_ID) -> handleRequestWithAccessIdParameter(request, response)
            else -> sendErrorNotFound(response)
        }
    }

    private fun handleRequestWithAccessIdParameter(request: HttpServletRequest, response: HttpServletResponse) {
        val idParameter = request.getParameter(ACCESS_ID)
        if (!HandleError.sendErrorForIntegerParameterIfIsNeeded(idParameter, response)) {
            writeActivityResponseWithAccessId(idParameter.toInt(), response)
        }
    }

    private fun writeActivityResponseWithAccessId(accessId: Int, response: HttpServletResponse) {
        val activitySession = sessionDAO.selectByAccessId(accessId)
        if (activitySession == null) {
            sendErrorNotFound(response)
        } else {
            response.writer.write(sessionToJson(activitySession))
        }
    }

    private fun handleRequestWithIdParameter(request: HttpServletRequest, response: HttpServletResponse) {
        val idParameter = request.getParameter(ID)
        if (!HandleError.sendErrorForIntegerParameterIfIsNeeded(idParameter, response)) {
            writeActivityResponse(idParameter.toInt(), response)
        }
    }

    private fun writeActivityResponse(activityId: Int, response: HttpServletResponse) {
        val activitySession = sessionDAO.selectById(activityId)
        if (activitySession == null) {
            sendErrorNotFound(response)
        } else {
            response.writer.write(sessionToJson(activitySession))
        }
    }

    private fun sessionToJson(session: UserSession): String {
        return gson.toJson(session)
    }

    private fun allSessionToJson(): String {
        return gson.toJson(sessionDAO.selectAll())
    }
}
