<%@page import="com.ctvit.weibo.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微博管理</title>
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<script language="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></script>
<style type="text/css">
.cur {
	color: #ff0000;
}
</style>
<script language="javascript">
	$(function(){
		
	});
	
</script>
</head>
<body>
<% 
	User user = (User)session.getAttribute("user");
	String userName = "超级管理员";
	String userId = user.getUserId();
	if(user != null)
	{
		userName = user.getUserName();
	}
%>
<div class="page_head">
  <div class="yonghu"> 微博管理系统 </div>
  <div class="esc" style="width:auto;margin-right:40px;"> 
  	当前登录用户:<%= userName %>
  	<%if(userId != null && userId.startsWith("User")) {%>
  	<a href="/weibo-studio/user/logoutLoginUser.action" target="_top">退出</a>
  	<%} %>
  </div>
</div>
</body>
</html>
