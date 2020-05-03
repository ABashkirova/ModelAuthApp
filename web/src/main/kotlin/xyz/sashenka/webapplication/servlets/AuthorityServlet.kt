package xyz.sashenka.webapplication.servlets

import com.google.gson.Gson
import com.google.inject.Inject
import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.model.dto.DBAccess
import xyz.sashenka.webapplication.di.logger.InjectLogger
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class AuthorityServlet : HttpServlet() {
    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var resourceDAO: ResourceDAO

    @InjectLogger
    lateinit var logger: KotlinLogger

    private val ID = "id"
    private val USER_ID = "userId"

    @Throws(ServletException::class, IOException::class)
    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Get authority info: ${request.requestURI} with ${request.queryString}")
        val query = request.queryString
        when {
            query.isNullOrEmpty() -> handleRequestEmptyParameter(response)
            query.contains(ID) -> handleRequestWithIdParameter(request, response)
            query.contains(USER_ID) -> handleRequestWithUserIdParameter(request, response)
            else -> HandleError().sendErrorNotFound(response)
        }
    }

    private fun handleRequestEmptyParameter(response: HttpServletResponse) {
        logger.info("Handle case all authority")
        logger.info("Write access info")
        response.writer.write(allAccessesToJson())
    }

    private fun handleRequestWithIdParameter(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handle case with id authority")
        val idParameter = request.getParameter(ID)
        if (!HandleError().sendErrorForIntegerParameterIfIsNeeded(idParameter, response)) {
            logger.info("Write access info")
            writeAccessResponse(idParameter.toInt(), response)
        }
    }

    private fun writeAccessResponse(accessId: Int, response: HttpServletResponse) {
        val access = resourceDAO.requestAccessById(accessId)
        if (access == null) {
            logger.error("Access not found with id $accessId")
            HandleError().sendErrorNotFound(response)
        } else {
            response.writer.write(accessToJson(access))
        }
    }

    private fun handleRequestWithUserIdParameter(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handle case with id user id")
        val idParameter = request.getParameter(USER_ID)
        if (!HandleError().sendErrorForIntegerParameterIfIsNeeded(idParameter, response)) {
            val accessUserId = idParameter.toInt()
            logger.info("Write accesses for user id $accessUserId")
            val access = resourceDAO.requestAccessByUserId(accessUserId)
            response.writer.write(gson.toJson(access))
        }
    }

    private fun accessToJson(access: DBAccess): String {
        return gson.toJson(access)
    }

    private fun allAccessesToJson(): String {
        return gson.toJson(resourceDAO.requestAllAccesses())
    }

}
