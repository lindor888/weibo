<%@page import="com.ctvit.weibo.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>添加敏感词</TITLE>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<link href="/weibo-studio/style/webcss/web_register.css" rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></SCRIPT>
<script language="JavaScript" src="/weibo-studio/style/js/checkValue.js"></script>
<style type="text/css">
#levelDiv {
	width: 300px;
	height: 20px;
	margin-top: 10px;
	line-height: 20px;
	padding-left: 15px;
}
;
</style>
<SCRIPT LANGUAGE="JavaScript">
	function isBlank(content) {
		return content == null || content == "";
	}

	function checkWords() {
		
		var error = $("#contentError").html();
		if(!isBlank(error)) {
			$("#content").focus();
			return;
		}
		
		var content = $.trim($("#content").val());
		if (isBlank(content)) {
			alert("请填写敏感词");
			return;
		}
		$.ajax({
			url : '/weibo-studio/words/addWords.action',
			type : 'post',
			async: false,
			data : {'bean.content':content},
			success : function(data){
				if(data.msg=='success'){
					alert('添加成功');
					parent.refreshTableData();
				}else if(data.msg=='limit'){
					alert('该敏感词已存在');
				}else{
					alert('添加失败');
				}
			}
			
			
		});
	}

	function selfWindowClose() {
		parent.$.fn.colorbox.close();
	}

	$(function() {
		$("#content").blur(function() {
			var content = $.trim($("#content").val());
			if (isBlank(content)) {
				alert("请填写敏感词");
				return;
			} 
		});
	});
</SCRIPT>
</HEAD>
<%
	String userId = "";
	User user = (User) session.getAttribute("user");
	if (user != null)
	{
		userId = user.getUserId();
	}
%>
<BODY id="tjdyyh">
	<div class="tjdyyh">
		<dl>
			<form id="appForm" action="/weibo-studio/words/addWords.action"
				method="post">
				<center>
					<span><h3>添加敏感词</h3></span>
				</center>
				<span>&nbsp;</span>
				<dd>
					<span><em>敏感词:</em><b>*</b></span> <input class="text" type="text"
						name="bean.content" id="content" value="" maxlength="128" />&nbsp;
						<font id="contentError" color=red></font>
				</dd>
				<span>&nbsp;</span>
				<dd>
					<center>
						<font id="error" color=red></font>
					</center>
				</dd>
				<dd>
					<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg);" 
						id="tj" type="button" onclick="checkWords();" class="submit" value="添加" /> 
					<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)" 
						type="button" class="button2" onclick="selfWindowClose();" value="关闭" />
				</dd>
			</form>
		</dl>
	</div>
</BODY>
</HTML>
