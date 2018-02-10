package com.ctvit.weibo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctvit.weibo.entity.User;

/**
 * Servlet Filter implementation class UserFilter
 */
public class SessionFilter implements Filter {

	public SessionFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String url = req.getServletPath();
		// 如果是登录页面、注册页面、错误页面、验证码页面、无此权限页面、都不进行拦截。
		if (url.equals("/loginPre.jsp") || url.equals("/login.jsp") || url.equals("/image.jsp")
				|| url.equals("/style/error/404.jsp")
				|| url.equals("/style/error/error.jsp")
				|| url.equals("/register.jsp")
				|| url.equals("/style/error/forbidden.jsp")) {
			if(url.equals("/login.jsp")) {
				User user = (User) req.getSession().getAttribute("user");
				if (user != null && !user.getUserName().equals("")) {
					resp.getWriter().write(
							"<script>window.location.href='" + req.getContextPath() + "/index.jsp';</script>");
				} else {
					chain.doFilter(request, response);
				}
			} else {
				chain.doFilter(request, response);
			}
		} else {
			if (url.equals("/user/modifyPass.jsp")) {
				resp.setContentType("text/html;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				resp.setHeader("Cache-Control", "no-cache");
				resp.getWriter().write(
						"<script>window.location.href='" + req.getContextPath()	+ "/style/error/forbidden.jsp';</script>");
			}
			User user = (User) req.getSession().getAttribute("user");
			if (user != null && !user.getUserName().equals("")) {
				chain.doFilter(request, response);
			} else {
				// 跳转到登录页面
				resp.setContentType("text/html;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				resp.setHeader("Cache-Control", "no-cache");
				resp.getWriter().write(
						"<script>window.parent.location.href='"	+ req.getContextPath() + "/user/loginPreUser';</script>");
			}
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
