<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<title>微博管理系统</title>
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></script>
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<script language="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></script>
<script>

$(function(){
	$("ul li a").click(function(){
		$("ul li a").removeClass();
		$(this).addClass("cur");
	});
});
</script>
<script type="text/javascript">
            function getObject(objectId) {
                if (document.getElementById && document.getElementById(objectId)) {
                    return document.getElementById(objectId);
                } else if (document.all && document.all(objectId)) {
                    return document.all(objectId);
                } else if (document.layers && document.layers[objectId]) {
                    return document.layers[objectId];
                } else {
                    return false;
                }
            }
            var last = null;
            function showHide(e, id) {
            	$('.menu').hide();
            	if(last!=null){
            		last.removeClass('minus').addClass('plus');
            	}
            	$('#'+id).show();
            	var current = $(e);
            	current.removeClass('plus').addClass('minus');
            	last = current;
            }
</script>
<style type="text/css">
	li.disabled a {
    	color: #8B8B8B;
	}
</style>
</head>
<body id="color">

<div class="sidebar">
	<dl>
    	<dt>
        	<a href="/weibo-studio/indexPreApp" class="plus" target="in">
            	我的应用
            </a>
       	</dt>
    </dl>
    <dl>
    	<dt>
        	<a href="/weibo-studio/indexPreWeibo" class="plus" target="in">
               	我的微博
            </a>
       	</dt>
    </dl>
    <%-- <dl>
    	<dt>
        	<a href="/weibo-studio/sendPreSend" class="plus" target="in">
            	发送微博
            </a>
       	</dt>
    </dl> --%>
    <%-- <dl>
    	<dt>
        	<a href="/weibo-studio/sentPreSend" class="plus" target="in">
            	系统已发微博
            </a>
       	</dt>
    </dl> --%>
    <dl>
    	<dt>
        	<a href="/weibo-studio/contentIndexPreWeibo" class="plus" target="in">
            	微博评论审核
            </a>
       	</dt>
    </dl>
    <dl>
    	<dt>
        	<a href="/weibo-studio/words/initWords" class="plus" target="in">
            	敏感词管理
            </a>
       	</dt>
    </dl>
    <dl>
    	<dt>
        	<a href="/weibo-studio/user/indexPreUser" class="plus" target="in">
            	密码修改
            </a>
       	</dt>
    </dl>
</div>
</body>
</html>

