package xyz.sashenka.webapplication.servlets

import com.google.gson.Gson
import com.google.inject.Inject
import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import xyz.sashenka.modelauthapp.dao.UserDAO
import xyz.sashenka.modelauthapp.model.domain.User
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

    private val ID = "id"

    @Throws(ServletException::class, IOException::class)
    override fun service(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Get user info: ${request.requestURI} with ${request.queryString}")
        val query = request.queryString
        when {
            query.isNullOrEmpty() -> handleRequestEmptyParameter(response)
            query.contains(ID) -> handleRequestWithUserId(request, response)
            else -> HandleError().sendErrorNotFound(response)
        }
    }

    private fun handleRequestEmptyParameter(response: HttpServletResponse) {
        logger.info("Handle case all users")
        logger.info("Write users info")
        response.writer.write(allUsersToJson())
    }

    private fun handleRequestWithUserId(request: HttpServletRequest, response: HttpServletResponse) {
        logger.info("Handle case with id user")
        val idParameter = request.getParameter(ID)
        if (!HandleError().sendErrorForIntegerParameterIfIsNeeded(idParameter, response)) {
            logger.info("Write user info")
            writeUserResponse(idParameter.toInt(), response)
        }
    }

    private fun writeUserResponse(userId: Int, response: HttpServletResponse) {
        val user = userDAO.requestUserById(userId)
        if (user == null) {
            logger.error("User not found with id $userId")
            HandleError().sendErrorNotFound(response)
        } else {
            response.writer.write(useToJson(user))
        }
    }

    private fun allUsersToJson(): String {
        return gson.toJson(userDAO.requestAllUsers())
    }

    private fun useToJson(user: User): String {
        return gson.toJson(user)
    }
}
