package xyz.sashenka.webapplication.di

import com.google.inject.servlet.GuiceFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletResponse

@WebFilter("/*")
class GuiceFilter : GuiceFilter() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val res = response as HttpServletResponse
        res.addHeader("Access-Control-Allow-Origin", "*")
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
        res.addHeader("Access-Control-Allow-Headers", "Content-Type")
        super.doFilter(request, response, chain)
    }
}
