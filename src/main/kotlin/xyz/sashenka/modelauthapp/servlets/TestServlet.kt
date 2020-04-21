package xyz.sashenka.modelauthapp.servlets

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "Hello", value = ["/echo"])
class TestServlet : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val id = req.getParameter("id")
        req.setAttribute("id", id)
        req.getRequestDispatcher("response.jsp").forward(req, resp)
        // resp.writer.write(id)
    }

    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val id = req.getParameter("id")
        // req.setAttribute("user", name)
        resp.sendRedirect("echo?id=$id")
        // req.getRequestDispatcher("response.jsp").forward(req, resp)
    }
}
