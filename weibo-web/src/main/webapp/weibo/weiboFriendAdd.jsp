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
		var friendWeiboUid = $.trim($("#friendWeiboUid").val());
		if(isBlank(friendWeiboUid)) {
			alert("请填写微博uid");
			return;
		}
		addForm();
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
        <form id="weiboForm" action="/weibo-studio/addFriend.action" method="post">
        	<center><span><h3>添加关注微博</h3></span></center>
        	<span>&nbsp;</span>
			<input type="hidden" id="weiboId" name="bean.weiboId" value="${weibo.weiboId }"/>
			<input type="hidden" id="weiboToken" name="bean.weiboToken" value="${weibo.weiboToken }"/>
			<input type="hidden" id="weiboUserName" name="bean.weiboUserName" value="${weibo.weiboUserName }"/>
			<input type="hidden" id="weiboPassword" name="bean.weiboPassword" value="${weibo.weiboPassword }"/>
            <dd><span><em>微博uid:</em><b>*</b></span>
            	<input class="text" type="text" name="bean.friendWeiboUid" oldvalue="" id="friendWeiboUid" maxlength="64"/></dd>
            <dd>
            	<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)" type="button" onclick="checkWeibo();" class="submit" value="添加" />
            	<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)" type="button" class="button2" style="" onclick="selfWindowClose();" value="关闭" />
            </dd>
        </form>
    </dl>
</div>
</BODY>
</HTML>
