<%@page import="com.ctvit.weibo.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>添加评论</TITLE>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<link href="/weibo-studio/style/webcss/web_register.css" rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<SCRIPT LANGUAGE="JavaScript" src="/weibo-studio/style/js/jquery-1.7.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></SCRIPT>
<script language="JavaScript" src="/weibo-studio/style/js/checkValue.js"></script>
<script src="/weibo-studio/style/js/ajaxfileupload.js" type="text/javascript"></script>
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
	function getParameter(args){
		var url = window.location.href;
		var paraString = url.substring(url.indexOf('?') + 1, url.length).split('&');//截取出url?后面的字符以&的字符
		var paraObj = {};
		for (var i = 0; j = paraString[i]; i++) {
			paraObj[j.substring(0, j.indexOf('=')).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
		}
		var returnValue = paraObj[args.toLowerCase()];
		if (typeof(returnValue) == 'undefined') {
			return "";
		}
		else {
			return returnValue;
		}
	}
	
	var weiboId = getParameter("weiboId");
	var taskWeiboId = getParameter("userId");
	
	
	$(function() {
		$(document).on("change", "#spLogo", function(){
            ajaxFileUpload('spLogo', 'spImage');
		});
		initUserList();
	});


	function selfWindowClose() {
		parent.$.fn.colorbox.close();
	}
	
	function initUserList(){
		$.ajax({
			url : '/weibo-studio/selectBtvUserWeibo',
			type : 'post',
			async : 'false',
			success : function(data){
				var data = data.rows;
				var html = '<option value="">请挑选</option>';
				for(var i=0;i<data.length;i++){
					html += '<option value="'+data[i].userid+'">'+data[i].nickname + '</option>';
				}
				
				$("#userList").html(html);
			}
		});
	}
	
	function getUser(id){
		if(id!=''){
			$.ajax({
				url : '/weibo-studio/selectBtvUserByKeyWeibo',
				type : 'post',
				async : false,
				data : {'btvUser.userid':id},
				success : function(data){
					var data = data.rows;
					var nickname = data.nickname;
					var location = data.location;
					var face = data.face;
					
					$("#nickname").val(nickname);
					$("#location").val(location);
					$("#spImage").attr('src',face);
					$("#userid").val(id);
				}
			});
		}else{
			$("#nickname").val('');
			$("#location").val('');
			$("#spImage").attr('src','');
			$("#userid").val('');
		}
	}
	
	function check(){
		var userid = $("#userid").val();
		var nickname = $("#nickname").val();
		var location = $("#location").val();
		var content = $("#content").val();
		var face = $("#spImage").attr('src');
		if(nickname==''){
			alert('请输入昵称');
			return;
		}
		if(content==''){
			alert('请输入评论');
			return;
		}
		if(face==''){
			alert('请上传图片');
			return;
		}
		
		$.ajax({
			url : '/weibo-studio/addOrUpdateBtvCommentWeibo',
			type : 'post',
			async : false,
			data : {'weiboId':weiboId,'btvComment.nickname':nickname,'btvComment.location':location,'btvComment.content':content,
				'btvComment.face':face,'btvComment.userid':userid,'taskWeiboId':taskWeiboId},
			success : function(data){
				var data = data.msg;
				if(data=='success'){
					alert('添加成功!');
					parent.window.loadData();
					selfWindowClose();
				}else{
					alert('添加失败!');
				}
			}
		});
	}

	
</SCRIPT>
</HEAD>
<BODY id="tjdyyh">
	<div class="tjdyyh">
		<dl>
			<form method="post">
				<input type="hidden" id="userid" />
				<center>
					<span><h3>添加评论</h3></span>
				</center>
				<span>&nbsp;</span>
				<dd>
					<span><em>挑选用户:</em></span> 
					<select id="userList" onchange="getUser(this.value)"></select>
				</dd>
				<dd>
					<span><em>昵称:</em><b>*</b></span> <input class="text" type="text"
						name="bean.nickname" id="nickname" value="" maxlength="128" />
				</dd>
				<dd>
					<span><em>地址:</em></span> <input class="text"
						type="text" name="bean.location" id="location" value=""
						maxlength="128" />
				</dd>
				<dd>
					<span><em>评论:</em><b>*</b></span> <textarea rows="2" cols="25" id="content"></textarea>
				</dd>
				<dd><span><em>头像:</em><b>*</b></span>
	            	<input id="spLogo" class="file" type="file" name="image" />
	            	<img src="/weibo-studio/style/image/no_image.jpg" id="spImage" height="80px" width="80px" style="margin-left: 85px">
            	<dd>
				</dd>
				<span>&nbsp;</span>
				<dd>
					<center>
						<font id="error" color=red></font>
					</center>
				</dd>
				<dd>
					<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg);" 
						id="tj" type="button" onclick="check();" class="submit" value="添加" /> 
					<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)" 
						type="button" class="button2" onclick="selfWindowClose();" value="关闭" />
				</dd>
			</form>
		</dl>
	</div>
</BODY>
</HTML>
