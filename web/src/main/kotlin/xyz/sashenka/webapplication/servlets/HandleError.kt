package xyz.sashenka.webapplication.servlets

import xyz.sashenka.webapplication.isInteger
import javax.servlet.http.HttpServletResponse

class HandleError {
    companion object {
        fun sendErrorParameterIsEmpty(response: HttpServletResponse) {
            response.sendError(400, "Parameter is empty")
        }

        fun sendErrorParameterIsNotInteger(response: HttpServletResponse) {
            response.sendError(400, "Parameter is not integer")
        }

        fun sendErrorNotFound(response: HttpServletResponse) {
            response.sendError(404)
        }

        fun sendErrorForIntegerParameterIfIsNeeded(parameter: String, response: HttpServletResponse): Boolean {
            if (parameter.isNullOrEmpty()) {
                sendErrorParameterIsEmpty(response)
                return true
            } else if (!parameter.isInteger()) {
                sendErrorParameterIsNotInteger(response)
                return true
            }
            return false
        }
    }
}
