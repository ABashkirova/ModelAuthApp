package xyz.sashenka.webapplication.servlets

import com.google.gson.Gson
import com.google.inject.Inject
import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import xyz.sashenka.modelauthapp.Application
import xyz.sashenka.modelauthapp.dao.SessionDao
import xyz.sashenka.modelauthapp.model.dto.db.DBUserSession
import xyz.sashenka.webapplication.di.logger.InjectLogger
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class
ActivityServlet : HttpServlet() {
    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var sessionDao: SessionDao

    @InjectLogger
    lateinit var logger: KotlinLogger

    private val ID = "id"
    private val ACCESS_ID = "authorityId"

    @Throws(ServletException::class, IOException::class)
    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Get activity info: ${request.requestURI} with ${request.queryString}")
        val query = request.queryString
        if (request.method == "GET") {
            when {
                query.isNullOrEmpty() -> handleRequestWithEmptyParameters(response)
                query.contains(ID) -> handleRequestWithIdParameter(request, response)
                query.contains(ACCESS_ID) -> handleRequestWithAccessIdParameter(request, response)
                else -> HandleError().sendErrorNotFound(response)
            }
        } else if (request.method == "POST") {
            onPost(request, response)
        }
    }

    @Throws(ServletException::class, IOException::class)
    fun onPost(request: HttpServletRequest, response: HttpServletResponse) {
        logger.debug(
            "Activity DoPost ->\n" +
                "RESPONCE: $response" + "REQUEST: $request  ${request.requestURI}"
        )

        if (request.requestURI.contains("/ajax/activity")) {
            val args = arrayOf(
                "-login", request.getParameter("login"),
                "-pass", request.getParameter("password"),
                "-res", request.getParameter("resource"),
                "-role", request.getParameter("role"),
                "-ds", request.getParameter("dateStart"),
                "-de", request.getParameter("dateEnd"),
                "-vol", request.getParameter("volume")
            )

            val result = request.reader.readLine()
            //val result = sessionDao.findById(1)
            //val result = Application(args).run()
            //println("result ----> $result")
            response.writer.write(result)
        }
    }

    private fun handleRequestWithEmptyParameters(response: HttpServletResponse) {
        logger.info("Handle case all activity")
        logger.info("Write activity info")
        response.writer.write(allSessionToJson())
    }

    private fun handleRequestWithAccessIdParameter(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handle case with id activity")
        val idParameter = request.getParameter(ACCESS_ID)
        if (!HandleError().sendErrorForIntegerParameterIfIsNeeded(idParameter, response)) {
            logger.info("Write activity info")
            writeActivityResponseWithAccessId(idParameter.toInt(), response)
        }
    }

    private fun writeActivityResponseWithAccessId(accessId: Int, response: HttpServletResponse) {
        val activitySessions = sessionDao.findByAccessId(accessId)
        response.writer.write(sessionsToJson(activitySessions))
    }

    private fun handleRequestWithIdParameter(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handle case with id")
        val idParameter = request.getParameter(ID)
        if (!HandleError().sendErrorForIntegerParameterIfIsNeeded(idParameter, response)) {
            writeActivityResponse(idParameter.toInt(), response)
        }
    }

    private fun writeActivityResponse(activityId: Int, response: HttpServletResponse) {
        val activitySession = sessionDao.findById(activityId)
        if (activitySession == null) {
            logger.error("Activity not found with id $activityId")
            HandleError().sendErrorNotFound(response)
        } else {
            response.writer.write(sessionToJson(activitySession))
        }
    }

    private fun sessionsToJson(sessions: List<DBUserSession>): String {
        return gson.toJson(sessions)
    }

    private fun sessionToJson(session: DBUserSession): String {
        return gson.toJson(session)
    }

    private fun allSessionToJson(): String {
        return gson.toJson(sessionDao.getAll())
    }
}
