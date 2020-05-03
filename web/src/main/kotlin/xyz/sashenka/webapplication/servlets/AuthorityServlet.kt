package xyz.sashenka.webapplication.servlets

import com.google.gson.Gson
import com.google.inject.Inject
import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import xyz.sashenka.modelauthapp.dao.ResourceDAO
import xyz.sashenka.modelauthapp.model.dto.DBAccess
import xyz.sashenka.webapplication.di.logger.InjectLogger
import xyz.sashenka.webapplication.servlets.HandleError.Companion.sendErrorNotFound
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
    private val USER_ID = "USER_ID"

    @Throws(ServletException::class, IOException::class)
    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        val query = request.queryString
        when {
            query.isNullOrEmpty() -> {
                response.writer.write(allAccessesToJson())
            }
            query.contains(ID) -> {
                handleRequestWithIdParameter(request, response)
            }
            query.contains(USER_ID) -> {
                handleRequestWithUserIdParameter(request, response)
            }
            else -> {
                sendErrorNotFound(response)
            }
        }
    }

    private fun handleRequestWithIdParameter(request: HttpServletRequest, response: HttpServletResponse) {
        val idParameter = request.getParameter(ID)
        if (!HandleError.sendErrorForIntegerParameterIfIsNeeded(idParameter, response)) {
            writeAccessResponse(idParameter.toInt(), response)
        }
    }

    private fun writeAccessResponse(accessId: Int, response: HttpServletResponse) {
        val access = resourceDAO.requestAccessById(accessId)
        if (access == null) {
            sendErrorNotFound(response)
        } else {
            response.writer.write(accessToJson(access))
        }
    }

    private fun handleRequestWithUserIdParameter(request: HttpServletRequest, response: HttpServletResponse) {
        val idParameter = request.getParameter(USER_ID)
        if (!HandleError.sendErrorForIntegerParameterIfIsNeeded(idParameter, response)) {
            val accessUserId = idParameter.toInt()
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
