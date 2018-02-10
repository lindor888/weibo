<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>添加微博</TITLE>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<link href="/weibo-studio/style/webcss/web_register.css" rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></SCRIPT>
<script language="JavaScript" src="/weibo-studio/style/js/checkValue.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	function isBlank(content) {
		return content == null || content == "";
	}
	
	function checkWeibo(){
		
		var weiboUserName = $.trim($("#weiboUserName").val());
		if(isBlank(weiboUserName)) {
			alert("请填写微博登录名");
			return;
		}
		
		var weiboPassword = $.trim($("#weiboPassword").val());
		if(isBlank(weiboPassword)) {
			alert("请填写微博密码");
			return;
		}
		
		var weiboUid = $.trim($("#weiboUid").val());
		if(isBlank(weiboUid)) {
			alert("请填写微博uid");
			return;
		}
		
		var appId = $.trim($("#appId").val());
		if(isBlank(appId) || appId == '0') {
			alert("请选择所属应用");
			return;
		}
		$("#error").html('');
		var weiboUserName = $("#weiboUserName").val();
		var weiboPassword = $("#weiboPassword").val();
		var appId = $("#appId").val();
		var appKey = $("#"+appId+"appKey").val();
		var appSecret = $("#"+appId+"appSecret").val();
		var appRedirectUri = $("#"+appId+"appRedirectUri").val();
		var url = '/weibo-studio/searchByUserNameWeibo';
		var param = {"bean.weiboUserName":weiboUserName,"bean.weiboPassword":weiboPassword,"bean.appKey":appKey,"bean.appSecret":appSecret,"bean.appRedirectUri":appRedirectUri};
		$.ajax({
			type: "POST",
			url: url,
			data: param,
			success: function(res){
				  if(res.msg == 1){
					  $("#login").focus();
					  $("#error").html('该微博已添加');
				  } else if(res.msg == 2){
					  $("#login").focus();
					  $("#error").html('该微博验证失败');
				  } else {
					  $("#yz").hide();
					  $("#tj").show();
					  $("#weiboToken").val(res.msg);//成功时返回token，直接设置到隐藏文本框中
					  $("#error").html('验证成功');
				  }
			}
		});
	}
	
	//验证成功后，当用户在修改时，隐藏提交按钮，必须重新验证
	function hide() {
		$("#tj").hide();
		$("#yz").show();
	}
	
	function addForm() {
		$("#weiboForm").submit();
	}
	
	function selfWindowClose(){
		parent.$.fn.colorbox.close();
	}
</SCRIPT>
</HEAD>
<BODY id="tjdyyh">
<div class="tjdyyh">
    <dl>
        <form id="weiboForm" action="/weibo-studio/addWeibo.action" method="post">
        	<center><span><h3>添加微博</h3></span></center>
        	<span>&nbsp;</span>
			<input type="hidden" id="weiboToken" name="bean.weiboToken" value=""/>
            <dd><span><em>微博登录名:</em><b>*</b></span>
            	<input class="text" type="text" name="bean.weiboUserName" oldvalue="" id="weiboUserName" maxlength="128" onkeydown="hide();"/>
            </dd>
            <dd><span><em>微博密码:</em><b>*</b></span>
            	<input class="text" type="password" name="bean.weiboPassword" oldvalue="" id="weiboPassword" maxlength="128" onkeydown="hide();"/></dd>
            <dd><span><em>微博uid:</em><b>*</b></span>
            	<input class="text" type="text" name="bean.weiboUid" oldvalue="" id="weiboUid" maxlength="128" onkeydown="hide();"/></dd>
            <dd><span><em>所属应用：</em><b>*</b></span>
            	<select id="appId" name="bean.appId" onchange="hide();">
            		<option value="0">-请选择-</option>
            		<s:if test="apps != null">
            			<s:iterator value="apps" var="app">
            				<option value="${app.appId}">${app.appName}</option>
            			</s:iterator>
            		</s:if>
            	</select>
            	<s:if test="apps != null">
           			<s:iterator value="apps" var="app">
           				<input type="hidden" id="${app.appId}appKey" value="${app.appKey}"/>
           				<input type="hidden" id="${app.appId}appSecret" value="${app.appSecret}"/>
           				<input type="hidden" id="${app.appId}appRedirectUri" value="${app.appRedirectUri}"/>
           			</s:iterator>
           		</s:if>
            </dd>
            <span>&nbsp;</span>
            <dd><center>
            	<font id="error" color=red></font>
            </center></dd>
            <dd>
            	<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)" type="button" id="yz" onclick="checkWeibo();" class="submit" value="验证微博" />
            	<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg);display:none" id="tj"  type="button" onclick="addForm();" class="submit" value="添加" />
            	<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)" type="button" class="button2" style="" onclick="selfWindowClose();" value="关闭" />
            </dd>
        </form>
    </dl>
</div>
</BODY>
</HTML>
