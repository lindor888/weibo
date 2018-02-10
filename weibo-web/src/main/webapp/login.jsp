<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微博管理系统登录</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<SCRIPT LANGUAGE="JavaScript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></SCRIPT>
<link href="/weibo-studio/style/webcss/web_guanlizhongxindenglu.css" rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<script language="javascript">
	function register() {
		var url = "/weibo-studio/register.jsp";
		$.fn.colorbox({
			iframe : true,
			innerWidth : 555,//子页面宽度
			innerHeight : 405, //子页面高度
			href : url,
			overlayClose : false
		});
	}

	function changeImg() {
		document.getElementById("loginImg").src = document .getElementById("loginImg").src + "?r=" + Math.random();
		return false;
	}
	function isBlank(content) {
		return content == null || content == "";
	}
	function transform() {
		var loginId = $("#username").val();
		if (isBlank(loginId)) {
			alert("请输入用户名!");
			return false;
		}
		$("#loginId").val(encodeURI(loginId));
		var password = $("#password").val();
		if (isBlank(password)) {
			alert("请输入密码!");
			return false;
		}
		var randomCode = $("#logincode").val();
		if (isBlank(randomCode)) {
			alert("请输入验证码!");
			return false;
		}
	}
	
	
	function login() {
		var url = $('#loginForm').attr('action');
		var params = $('#loginForm').serialize();
		$.ajax({
			type:'POST',
			url:url,
			data:params,
			dataType:'json',
			success:function(rs) {
				var message = rs.msg;
				if(message == 'success') {
					window.location = '/weibo-studio/index.jsp';
				} else {
					alert(message);
					return;
				}
			}
		});
	}
</script>
</head>

<body>

	<div class="glzxdl">
		<form id="loginForm" method="post" action="/weibo-studio/user/loginUser">
			<dl>
				<dt>
					<span>微博管理系统</span><em>-- 登录</em>
				</dt>
				<dd class="dd1">
					<span>用户名：</span><input type="text" class="text1"
						name="bean.userName" id="username" />
				</dd>
				<dd class="dd2">
					<span>密码：</span><input type="password" class="text2"
						name="bean.password" id="password" />
				</dd>
				<dd class="dd3">
					<span>验证码：</span><input type="text" class="text3" name="logincode"
						id="logincode" /><img src="/weibo-studio/image.jsp"
						width="104" height="48" id="loginImg"><em><a href="#"
							onclick="changeImg()">看不清？</a></em>
				</dd>
				<dd class="dd4">
					<input type="button" class="submit" value="登 录"
						onclick="login();" /> <input type="button"
						class="submit" value="注册" onclick="register();"
						style="margin-left: 50px" />
				</dd>
				<dd class="dd4">
					<div
						style="width: 300px; margin: 65px auto; font-size: 12px; color: red;">浏览器版本要求：&nbsp;ie&nbsp;7&nbsp;及以上，&nbsp;firefox&nbsp;19&nbsp;及以上</div>
				</dd>
			</dl>
		</form>
	</div>

</body>
</html>