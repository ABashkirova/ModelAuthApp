package xyz.sashenka.webapplication.servlets
import com.google.inject.Singleton
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Singleton
class HelloServlet : HttpServlet() {
    @Throws(ServletException::class, IOException::class)
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val out = resp.outputStream
        out.write("Hello Heroku".toByteArray())
        out.flush()
        out.close()
    }
}
