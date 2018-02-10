<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>修改敏感词</TITLE>
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
		
		var id = $("#id").val();
		$.ajax({
			url : '/weibo-studio/words/updateWords.action',
			type : 'post',
			async: false,
			data : {'bean.content':content,'bean.id':id},
			success : function(data){
				if(data.msg=='success'){
					alert('修改成功');
					parent.refreshTableData();
				}else if(data.msg=='limit'){
					alert('该敏感词已存在');
				}else{
					alert('修改失败');
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
<BODY id="tjdyyh">
	<div class="tjdyyh">
		<dl>
			<form id="wordsForm" action="/weibo-studio/words/updateWords.action" method="post">
				<center>
					<span><h3>修改敏感词</h3></span>
				</center>
				<span>&nbsp;</span> <input type="hidden" id="id" name="bean.id"
					value="${bean.id}" /> 
				<dd>
					<span><em>敏感词:</em><b>*</b></span> <input class="text" type="text"
						name="bean.content" id="content" value="${bean.content}"
						maxlength="128"/> <font id="contentError"
						color=red></font>
				</dd>
				<dd>
					<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg);"
						id="tj" type="button" onclick="checkWords();" class="submit" value="修改" /> 
					<input style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)" 
						type="button" class="button2" onclick="selfWindowClose();" value="关闭" />
				</dd>
			</form>
		</dl>
	</div>
</BODY>
</HTML>
