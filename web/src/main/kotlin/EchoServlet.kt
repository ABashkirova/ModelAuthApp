import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "EchoServlet", value = ["/echo"])
class EchoServlet: HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val id = request.getParameter("id")
        request.setAttribute("id", id)
        request.getRequestDispatcher("response.jsp").forward(request, response)
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val id = request.getParameter("id")
        response.sendRedirect("echo?id=$id")
    }
}
