package xyz.sashenka.webapplication.servlets

import org.apache.logging.log4j.kotlin.KotlinLogger
import org.apache.logging.log4j.kotlin.loggerOf
import xyz.sashenka.webapplication.isInteger
import javax.servlet.http.HttpServletResponse

class HandleError {
    var logger: KotlinLogger = loggerOf(HandleError::class.java)

    fun sendErrorNotFound(response: HttpServletResponse) {
        logger.error("sendErrorNotFound")
        response.sendError(404)
    }

    fun sendErrorForIntegerParameterIfIsNeeded(parameter: String?, response: HttpServletResponse): Boolean {
        if (parameter.isNullOrEmpty()) {
            sendErrorParameterIsEmpty(response)
            return true
        } else if (!parameter.isInteger()) {
            sendErrorParameterIsNotInteger(response)
            return true
        }
        return false
    }

    private fun sendErrorParameterIsEmpty(response: HttpServletResponse) {
        logger.error("sendErrorParameterIsEmpty")
        response.sendError(400, "Parameter is empty")
    }

    private fun sendErrorParameterIsNotInteger(response: HttpServletResponse) {
        logger.error("sendErrorParameterIsNotInteger")
        response.sendError(400, "Parameter is not integer")
    }
}
