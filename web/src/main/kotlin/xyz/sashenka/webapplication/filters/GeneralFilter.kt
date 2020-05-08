package xyz.sashenka.webapplication.filters

import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import xyz.sashenka.webapplication.di.logger.InjectLogger
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest

@Singleton
class GeneralFilter : Filter {
    @InjectLogger
    lateinit var logger: KotlinLogger

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        logger.debug("doFilter method")

        request.characterEncoding = "UTF-8"
        response.characterEncoding = "UTF-8"
        response.contentType = "text/html"

        if (request is HttpServletRequest) {
            val url = request.requestURL.toString()
            if (url.contains("ajax")) {
                response.contentType = "application/json"
            }
        }

        chain.doFilter(request,response)
    }

    override fun destroy() {}
}
