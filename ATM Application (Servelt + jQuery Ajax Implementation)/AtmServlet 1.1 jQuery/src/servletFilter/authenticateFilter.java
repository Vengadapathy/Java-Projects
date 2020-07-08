package servletFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Objects.AtmCard;

/**
 * Servlet Filter implementation class authenticateFilter
 */
@WebFilter({ "/Transaction","/Other" })
public class authenticateFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		System.out.println("Servlet filter process started");
		AtmCard card = (AtmCard) ((HttpServletRequest) request).getSession().getAttribute("atmcard");
		if(card == null || card.getAccount() == null ) {
			System.out.println("Session invalid");
			((HttpServletRequest) request).getSession().invalidate();
			((HttpServletResponse) response).sendRedirect("LoginPage.html");
		}else {
			System.out.println("Authentication True");
			chain.doFilter(request, response);
		}
		System.out.println("Servlet filter process ended");
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
