package xyz.sashenka.webapplication.filters

import com.google.inject.Singleton
import org.apache.logging.log4j.kotlin.KotlinLogger
import xyz.sashenka.webapplication.di.logger.InjectLogger
import java.io.IOException

import javax.servlet.*
import javax.servlet.http.HttpServletResponse

@Singleton
class MyFilter : Filter {
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
        //val httpResponse = response as HttpServletResponse
        //httpResponse.setHeader("Content-Type", "text/html;charset=utf-8")
        //request.characterEncoding = "UTF-8"
        //response.characterEncoding = "UTF-8"
        chain.doFilter(request,response)
    }

    override fun destroy() {}
}
