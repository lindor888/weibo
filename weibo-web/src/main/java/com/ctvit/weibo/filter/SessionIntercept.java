package com.ctvit.weibo.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.ctvit.weibo.entity.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionIntercept extends AbstractInterceptor
{

	/**
	 * Action拦截器
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		User user = (User) ActionContext.getContext().getSession().get("user");
		HttpServletResponse resp = ServletActionContext.getResponse();
		HttpServletRequest req = ServletActionContext.getRequest();
		
		System.out.println(invocation.getAction().toString());
		
		String method = invocation.getProxy().getMethod();
		if("login".equals(method) || "loginPre".equals(method) || "register".equals(method) || "selectByName".equals(method) 
				|| "getc".equals(method) || "getw".equals(method)) {
			return invocation.invoke();
		}
		if(user == null) {
			// 跳转到登录页面
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("Cache-Control", "no-cache");
			resp.getWriter().write(
					"<script>window.parent.location.href='"	+ req.getContextPath() + "/login.jsp';</script>");
			return null;
		}
		return invocation.invoke();
	}

}
