<%@page import="com.ctvit.weibo.util.LoginCode"%><%@page contentType="image/jpeg" %><%@page language="java" pageEncoding="utf-8"%><%
 //String str = login.getCertPic(0,0,response.getOutputStream());
 //将验证码存入session中
 LoginCode loginCode = new LoginCode();
 loginCode.createImage(request, response);
 //session.setAttribute("certCode",str);
%>