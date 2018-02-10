<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码</title>
<META NAME="Generator" CONTENT="EditPlus" />
<META NAME="Author" CONTENT="" />
<META NAME="Keywords" CONTENT="" />
<META NAME="Description" CONTENT="" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet"
	href="/weibo-studio/style/colorBox/colorbox.css" />
<SCRIPT LANGUAGE="JavaScript"
	src="/weibo-studio/style/js/jquery-1.4.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript"
	src="/weibo-studio/style/js/jquery.colorbox-min.js"></SCRIPT>
<link href="/weibo-studio/style/webcss/web_mimaxiugai.css"
	rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet"
	type="text/css" />
<script language="javascript">
	var minLength = 6;
	function isBlank(content) {
		return content == null || content == "";
	}
	function isShort(content) {
		return content.length < minLength ? true : false;
	}
	function transform() {
		var password = $("#password").val();
		if (isBlank(password)) {
			alert("请输入旧密码!");
			return false;
		}

		var password1 = $("#password1").val();
		if (isBlank(password1)) {
			alert("请输入新密码!");
			return false;
		} else if (isShort(password1)) {
			alert("新密码长度必须为6~16位!");
			return false;
		}

		var regu = "^[\u4e00-\u9fa5]+$";
		var re = new RegExp(regu);
		if (re.test(password1)) {
			alert('密码不能输入汉字!');
			return false;
		}

		var password2 = $("#password2").val();
		if (isBlank(password2)) {
			alert("请再次输入新密码!");
			return false;
		} else if (isShort(password2)) {
			alert("新密码长度必须为6~16位!");
			return false;
		}
		if (re.test(password2)) {
			alert('密码不能输入汉字!');
			return false;
		}
		if (password1 != password2) {
			alert("两次输入密码不一致，请重新输入！");
			return false;
		}
		var url = '/weibo-studio/user/modifyUser';
		var param = {
			"newPassword" : password1,
			"bean.password" : password
		};
		$.ajax({
			url : url,
			type : "POST",
			data : param,
			success : function(response) {
				if (response.msg == "1") {
					alert("修改成功！");
				} else if (response.msg == "3") {
					alert("密码错误！");
				} else {
					alert("修改失败！");
				}
			}
		});
	}
</script>
</head>

<body>
	<div class="tjdyyh">
		<form method="post">
			<dl>
				<dd>
					<span><em>输入旧密码：</em><b>*</b></span><input class="text"
						id="password" name="password" type="password" maxlength="16" />
				</dd>
				<dd>
					<span><em>输入新密码：</em><b>*</b></span><input class="text"
						id="password1" name="newPassword" type="password" maxlength="16" />(长度为6~16位)
				</dd>
				<dd>
					<span><em>再次输入新密码：</em><b>*</b></span><input class="text"
						id="password2" name="reNewPassword" type="password" maxlength="16" />(长度为6~16位)
				</dd>
				<dd class="dd4">
					<input type="button" class="submit" value="修  改"
						onclick="transform()" style="margin-left: 160px;" />
				</dd>
			</dl>
		</form>
	</div>
</body>
</html>