<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>注册新用户</TITLE>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="/weibo-studio/style/colorBox/colorbox.css" />
<link href="/weibo-studio/style/webcss/web_register.css"
	rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet"
	type="text/css" />
<SCRIPT LANGUAGE="JavaScript"
	src="/weibo-studio/style/js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript"
	src="/weibo-studio/style/js/jquery.colorbox-min.js"></SCRIPT>
<script language="JavaScript"
	src="/weibo-studio/style/js/checkValue.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var minLength = 6;
	function isBlank(content) {
		return content == null || content == "";
	}
	function isShort(content) {
		return content.length < minLength ? true : false;
	}

	function isExist(login) {
		var url = '/weibo-studio/user/selectByNameUser';
		var param = {
			"bean.userName" : login
		};
		var flag = true;
		$.ajax({
			type : "POST",
			url : url,
			data : param,
			async : false,
			success : function(res) {
				if (res.msg == '1') {
					$("#login").focus();
					alert("该用户已经存在！");
					$("#login").val("");
					$("#password").val("");
					$("#password1").val("");
					flag = false;
				}
			}
		});
		return flag;
	}

	function roleForm() {

		var checkName = "";
		var login = $.trim($("#login").val());

		var len = login.length;
		if (login == undefined || len == 0) {
			alert("请填写用户名!");
			return;
		}
		checkName = "用户名";
		flag = checkSpecialVal(checkName, login);
		if (!flag)
			return;

		var flag = isExist(login);
		if (!flag)
			return;

		var regu = "^[\u4e00-\u9fa5]+$";
		var re = new RegExp(regu);
		var password = $("#password").val();
		if (isBlank(password)) {
			alert("请输入密码!");
			return false;
		} else if (isShort(password)) {
			alert("密码长度必须为6~16位!");
			return false;
		}
		if (re.test(password)) {
			alert('密码不能输入汉字!');
			return false;
		}

		var password1 = $("#password1").val();
		if (isBlank(password1)) {
			alert("请再次输入新密码!");
			return false;
		} else if (isShort(password1)) {
			alert("密码长度必须为6~16位!");
			return false;
		}
		if (re.test(password1)) {
			alert('密码不能输入汉字!');
			return false;
		}
		if (password != password1) {
			alert("两次输入密码不一致，请重新输入！");
			return false;
		}
		var url = "/weibo-studio/user/registerUser";
		var param = {
			"bean.userName" : login,
			"bean.password" : password
		};
		$.ajax({
			url : url,
			type : "POST",
			data : param,
			success : function(response) {
				//1.注册成功 2.注册失败 3.未知错误
				if (response.msg == "1") {
					alert("成功注册");
				} else if (response.msg == "2") {
					alert("注册失败！");
				} else {
					alert("未知错误！");
				}
			}
		});
	}

	function selfWindowClose() {
		parent.$.fn.colorbox.close();
	}
</SCRIPT>
</HEAD>
<BODY id="tjdyyh">
	<div class="tjdyyh">
		<dl>
			<form id="accountForm" method="post">
				<center>
					<span><h3>注册新用户</h3></span>
				</center>
				<span>&nbsp;</span>
				<dd>
					<span><em>用户名:</em><b>*</b></span><input class="text" type="text"
						name="bean.userName" id="login" maxlength="10" />
				</dd>
				<dd>
					<span><em>密码：</em><b>*</b></span><input class="text" id="password"
						name="bean.password" type="password" maxlength="16" />(长度为6~16位)
				</dd>
				<dd>
					<span><em>再次密码：</em><b>*</b></span><input class="text"
						id="password1" name="password1" type="password" maxlength="16" />(长度为6~16位)
				</dd>
				<dd>
					<input
						style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)"
						type="button" onclick="roleForm();" class="submit" value="提交" />
					<input
						style="background:url(/weibo-studio/style/webcss/img/chaxunbg.jpg)"
						type="button" class="button2" style=""
						onclick="selfWindowClose();" value="关闭" />
				</dd>
			</form>
		</dl>
	</div>
</BODY>
</HTML>
