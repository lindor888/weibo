<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>我的微博</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/css/main.css" />
<link href="/weibo-studio/style/webcss/web_ipduanguanli.css" rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></script>
<script language="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></script>
<script language="javascript" src="/weibo-studio/style/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" src="/weibo-studio/style/js/weibo.js"></script>
<script language="javascript" src="/weibo-studio/style/js/ajaxfileupload.js"></script>
<script language="JavaScript">
	
	$(function() {
		$form = $('#queryForm');
		loadData();
	});
	
	var contentCurPage = 1;
	var contentPageSum = 0;
	
	//前一页
	function prePage(curPage) {
		contentCurPage = curPage;
		if(contentCurPage < 1) {
			alert('目前已是第一页！');
			return;
		}
		loadData();
	}
	//后一页
	function nextPage(curPage) {
		contentCurPage = curPage;
		if(contentCurPage > contentPageSum) {
			alert('目前已是最后一页！');
			return;
		}
		loadData();
	}
	
	//加载已发送微博信息
	function loadData() {
		$form[0]["example.page"].value = contentCurPage;
		var url = $form.attr('action');
		var param = $form.serialize();
		$.ajax({
			type: "POST",
			url: url,
			data: param,
			success: function(msg){
				var data = msg.rows;
				contentPageSum = msg.pageSum;
				fillWeibo(data);
			}
		});
	}
	
	//加载已发送微博信息
	function onWeiboChange() {
		$form[0]["example.page"].value = 1;
		var url = $form.attr('action');
		var param = $form.serialize();
		$.ajax({
			type: "POST",
			url: url,
			data: param,
			success: function(msg){
				var data = msg.rows;
				contentPageSum = msg.pageSum;
				fillWeibo(data);
			}
		});
	}
	
	//组装微博信息table
	function fillWeibo(data) {
		var checkText = $("#userId").find("option:selected").text(); 
		var html = new Array();
		if(data != undefined && data != null && data != '') {
			for(var i=0;i<data.length;i++) {
				var createTime = data[i].operateTime.replace('T',' ');
				html.push("<div id=\"home_wb\" class=\"wb\">");
					html.push("<div class=\"wb_sent\">");
						html.push("<div class=\"wb_r_t\">");
							html.push("<span>");
							html.push("<a title=\"\" class=\"font14b\" onclick=\"\" href=\"javascript:;\">"+checkText+"</a>");
							html.push("</span>:");
							html.push("<div class=\"wb_content\">"+data[i].brief+"</div>");
						html.push("</div>");
						html.push("<div class=\"date\">");
							html.push("<span class=\"l\">");
								html.push(createTime);
							html.push("</span>");
							html.push("<span class=\"r la\">");
								html.push("<a class=\"plLink\" id=\"home_plLink_3712667267535835\" href=\"javascript:del('"+data[i].id+"','"+data[i].weiboContentId+"');\">删除</a>");
							html.push("</span>");
						html.push("</div>");
					html.push("</div>");
				html.push("</div>");
			}
			html.push("<div style=\"display: block;\" class=\"qPager\" id=\"comments_list_page\">");
			html.push("<div class=\"stat\"></div>");
			html.push("<ul class=\"turn\">");
				html.push("<li>");
				html.push("<a onclick=\"prePage(1)\" href=\"javascript:;\">首页</a>");
				html.push("</li>");
				html.push("<li>");
				html.push("<a onclick=\"prePage("+(contentCurPage-1)+")\" href=\"javascript:;\">上一页</a>");
				html.push("</li>");
				html.push("<li>");
				html.push("<a onclick=\"nextPage("+(contentCurPage+1)+")\" href=\"javascript:;\">下一页</a>");
				html.push("</li>");
				html.push("<li>");
				html.push("<a onclick=\"nextPage("+contentPageSum+")\" href=\"javascript:;\">末页</a>");
				html.push("</li>");
			html.push("</ul>");
			html.push("</div>");
		} else {
			html.push("<div id=\"home_wb\" class=\"wb\">");
			html.push("暂无发布的微博信息");
			html.push("</div>");
		}
		$('#weibocontent').html(html.join(""));
	}
	
	//删除微博
	function del(sentId,weiboContentId) {
		var userId = $('#userId').val();
		if(userId == '') {
			alert('请先选择一个微博！');
			return;
		}
		if (confirm("您确定要删除吗?")) {
			var url = '/weibo-studio/deleteSend.action';
			var param = {"bean.weiboContentId":weiboContentId,"bean.userId":userId,"bean.id":sentId};
			$.ajax({
				type: "POST",
				url: url,
				data: param,
				success: function(msg){
				   if(typeof msg.result == 'undefined'){
					   showError(msg);
				   }else{
					   alert('操作成功！');
					   loadData();
				   }
				}
			});
		}
	}
	
	//切换我的定时任务列表
	function changeTime() {
		var weiboId = $("#weiboId").val();
		window.location = "/weibo-studio/sentPreTimeSend?weibo.weiboId=" + weiboId;
	}
</script>
</head>

<body>
	<div class="ipduanguanli" style="overflow:auto;">
		<form id="queryForm" action="/weibo-studio/selectSentSend.action" method="post">
			<input type="hidden" name="example.page" value="1" />
			<input type="hidden" name="example.rows" id="rows" value="20" />
			<input type="hidden" name="example.status" id="status" value="2"/> 
			<dl>
				<dd>
					<span>微博：</span> <select name="example.userId" id="userId" onchange="onWeiboChange();">
						<s:if test="weibos != null && weibos.size() > 0">
							<s:iterator value="weibos" var="weibo">
								<option value="${weibo.weiboUid }">${weibo.weiboUserName}</option>
							</s:iterator>
						</s:if>
						<s:else>
							<option value="">-暂无微博-</option>
						</s:else>
					</select>
					<input class="button1" value="定时任务" onclick="changeTime();" type="button" />
				</dd>
			</dl>
		</form>
      	<div id="divWrap"></div>
      
		<div id="weibocontent">
		</div>
	</div>
</body>
</html>