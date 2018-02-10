<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>修改应用</TITLE>
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
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<link href="/weibo-studio/style/webcss/web_register.css" rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></SCRIPT>
<script language="JavaScript" src="/weibo-studio/style/js/checkValue.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	$(document).ready(function() {
		var oldAppName = "${bean.appName}";
		$("#appName").blur(function() {
			var appName = $.trim($("#appName").val());
			if (isBlank(appName)) {
				alert("请填写应用名称");
				return;
			}
			if(oldAppName == appName) return;
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
						//$("#tj").attr({"disabled":"disabled"});
					} else if (res.msg == "2") {
						$("#appName").focus();
					} else {
						$("#tj").removeAttr("disabled");
					}
				}
			});
		});
		
		$("#appName").focus(function(){
			$("#appNameError").html('');
		});
	});
	
	function isBlank(content) {
		return content == null || content == "";
	}
	function checkApp() {
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
		//提交之前修改下单选按钮的值
		
		$("#appLevelFlag").val($('input:radio:checked').val());
		$("#appForm").submit();
	}
	function selfWindowClose() {
		parent.$.fn.colorbox.close();
	}
</SCRIPT>
</HEAD>
<BODY id="tjdyyh">
	<div class="tjdyyh">
		<dl>
			<form id="appForm" action="/weibo-studio/updateApp.action" method="post">
				<center>
					<span><h3>修改应用</h3></span>
				</center>
				<span>&nbsp;</span> <input type="hidden" id="appId" name="bean.appId"
					value="${bean.appId}" /> <input type="hidden" id="userId"
					name="bean.userId" value="${bean.userId}" />
				<dd>
					<span><em>应用名称:</em><b>*</b></span> <input class="text" type="text"
						name="bean.appName" id="appName" value="${bean.appName}"
						maxlength="128"/> <font id="appNameError"
						color=red></font>
				</dd>
				<dd>
					<span><em>App Key:</em><b>*</b></span> <input class="text"
						type="text" name="bean.appKey" id="appKey" value="${bean.appKey }"
						maxlength="128" />
				</dd>
				<dd>
					<span><em>App Secret:</em><b>*</b></span> <input class="text"
						type="text" name="bean.appSecret" id="appSecret" maxlength="128"
						value="${bean.appSecret }" />
				</dd>
				<dd>
					<span><em>回调页：</em><b>*</b></span> <input class="text" type="text"
						name="bean.appRedirectUri" id="appRedirectUri"
						value="${bean.appRedirectUri }" maxlength="128"
						/>
				</dd>
				<div id="levelDiv">
					<span><em>权限设置：</em></span> 
					<span style="margin-left: 10px;"><em>基础权限：</em>	</span>
					<input class="text" type="radio" name="appLevel" id="appLevel_0" value="0" /> 
					<span style="margin-left: 10px;"><em>高级权限：</em></span>
					<input class="text" type="radio" name="appLevel" id="appLevel_1" value="1"/>
				</div>
				<input type="hidden" value="${bean.appLevel}" id="appLevelFlag" name="bean.appLevel" />
				<script type="text/javascript">
					var value = $("#appLevelFlag").val();
					if (value == "0") {
						document.getElementById("appLevel_0").checked = true;
					}
					if (value == "1") {
						document.getElementById("appLevel_1").checked = true;
					}
				</script>
				</dd>
				<span>&nbsp;</span>
				<dd>
					<center>
						<font id="error" color=red></font>
					</center>
				</dd>
				<dd>
					<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg);"
						id="tj" type="button" onclick="checkApp();" class="submit" value="修改" /> 
					<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)" 
						type="button" class="button2" onclick="selfWindowClose();" value="关闭" />
				</dd>
			</form>
		</dl>
	</div>
</BODY>
</HTML>
