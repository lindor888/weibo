<%@page import="com.ctvit.weibo.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>添加应用</TITLE>
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

	function checkApp() {
		
		var error = $("#appNameError").html();
		if(!isBlank(error)) {
			$("#appName").focus();
			return;
		}
		
		var appName = $.trim($("#appName").val());
		if (isBlank(appName)) {
			alert("请填写应用名称");
			return;
		}
		var appKey = $.trim($("#appKey").val());
		if (isBlank(appKey)) {
			alert("请填写App Key");
			return;
		}

		var appSecret = $.trim($("#appSecret").val());
		if (isBlank(appSecret)) {
			alert("请填写App Secret");
			return;
		}

		var appRedirectUri = $.trim($("#appRedirectUri").val());
		if (isBlank(appRedirectUri)) {
			alert("请填写回调页");
			return;
		}
		var appLevel = $('input[name="appLevel"]:checked').val();
		if (appLevel == null) {
			alert("请选择权限");
			return;
		}

		var selectText = $("#userSelect").val();
		if (selectText == "" || selectText == "0" || selectText == 0) {
			alert("请选择用户");
			return;
		}
		$("#appLevelFlag").val($('input:radio:checked').val());
		addForm();
	}

	//名称检查
	function checkName(appName) {
		var param = {
			"bean.appName" : appName
		};
		var url = '/weibo-studio/searchByNameApp';
		$.ajax({
			type : "POST",
			url : url,
			data : param,
			success : function(res) {
				if (res.msg == "1") {
					//应用信息没有修改，修改按钮禁用。
					$("#appNameError").html('该应用已添加');
				} else {
					$("#appNameError").html('');
					$("#tj").removeAttr("disabled");
				}
			}
		});
	}

	function addForm() {
		$("#appForm").submit();
	}

	function selfWindowClose() {
		parent.$.fn.colorbox.close();
	}

	$(function() {
		$("#appName").blur(function() {
			var appName = $.trim($("#appName").val());
			if (isBlank(appName)) {
				alert("请填写应用名称");
				return;
			} else {
				checkName(appName);
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
			<form id="appForm" action="/weibo-studio/addApp.action"
				method="post">
				<center>
					<span><h3>添加应用</h3></span>
				</center>
				<span>&nbsp;</span>
				<dd>
					<span><em>应用名称:</em><b>*</b></span> <input class="text" type="text"
						name="bean.appName" id="appName" value="" maxlength="128" />&nbsp;
						<font id="appNameError" color=red></font>
				</dd>
				<dd>
					<span><em>App Key:</em><b>*</b></span> <input class="text"
						type="text" name="bean.appKey" id="appKey" value=""
						maxlength="128" />
				</dd>
				<dd>
					<span><em>App Secret:</em><b>*</b></span> <input class="text"
						type="text" name="bean.appSecret" id="appSecret" maxlength="128"
						value="" />
				</dd>
				<dd>
					<span><em>回调页：</em><b>*</b></span> <input class="text" type="text"
						name="bean.appRedirectUri" id="appRedirectUri" value=""
						maxlength="128" />
				</dd>
				<div id="levelDiv">
					<span><em>权限设置：</em></span> <span style="margin-left: 10px;"><em>基础权限：</em></span>
					<input class="text" type="radio" name="appLevel" id="appLevel_0"
						value="0" checked="checked" /> <span style="margin-left: 10px;"><em>高级权限：</em></span>
					<input class="text" type="radio" name="appLevel" id="appLevel_1"
						value="1" /> <input type="hidden" value="${bean.appLevel}"
						id="appLevelFlag" name="bean.appLevel" /> <input type="hidden"
						id="userId" name="bean.userId" value="${session.user.userId}" />
				</div>
				</dd>
				<span>&nbsp;</span>
				<dd>
					<center>
						<font id="error" color=red></font>
					</center>
				</dd>
				<dd>
					<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg);" 
						id="tj" type="button" onclick="checkApp();" class="submit" value="添加" /> 
					<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)" 
						type="button" class="button2" onclick="selfWindowClose();" value="关闭" />
				</dd>
			</form>
		</dl>
	</div>
</BODY>
</HTML>
