package xyz.sashenka.webapplication.servlets

import com.google.gson.Gson
import com.google.inject.Inject
import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import xyz.sashenka.modelauthapp.dao.UserDAO
import xyz.sashenka.modelauthapp.model.domain.User
import xyz.sashenka.webapplication.di.logger.InjectLogger
import xyz.sashenka.webapplication.isInteger
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
        if (request.queryString == null) {
            response.writer.write(allUsersToJson())
        } else {
            handleRequestWithUserId(request, response)
        }
    }

    fun handleRequestWithUserId(request: HttpServletRequest, response: HttpServletResponse) {
        val idParameter = request.getParameter("id")
        if (idParameter.isNullOrEmpty()) {
            sendErrorParameterIsEmpty(response)
        } else if (!idParameter.isInteger()) {
            sendErrorParameterIsNotInteger(response)
        } else {
            val id = idParameter.toInt()
            writeUserResponse(id, response)
        }
    }

    private fun writeUserResponse(userId: Int, response: HttpServletResponse) {
        val user = userDAO.requestUserById(userId)
        if (user == null) {
            sendErrorNotFoundUserWithId(response)
        } else {
            response.writer.write(useToJson(user))
        }
    }

    private fun sendErrorParameterIsEmpty(response: HttpServletResponse) {
        response.sendError(400, "Parameter is empty")
    }

    private fun sendErrorParameterIsNotInteger(response: HttpServletResponse) {
        response.sendError(400, "Parameter is not integer")
    }

    private fun sendErrorNotFoundUserWithId(response: HttpServletResponse) {
        response.sendError(404)
    }

    private fun allUsersToJson(): String {
        return gson.toJson(userDAO.requestAllUsers())
    }

    private fun useToJson(user: User): String {
        return gson.toJson(user)
    }
}
