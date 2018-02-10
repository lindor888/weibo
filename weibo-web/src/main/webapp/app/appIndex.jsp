<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>我的应用</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<link href="/weibo-studio/style/webcss/web_ipduanguanli.css" rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></script>
<script language="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></script>
<script language="javascript" src="/weibo-studio/style/js/loadData.js"></script>
<script language="JavaScript">
	//添加应用
	function addApp() {
		var url = "/weibo-studio/addPreApp";
		$.fn.colorbox({
			iframe : true,
			innerWidth : 550,//子页面宽度
			innerHeight : 400, //子页面高度
			href : url,
			overlayClose : false
		});
	}
	//修改应用
	function updateApp(appId) {
		var url = "/weibo-studio/updatePreApp?bean.appId=" + appId;
		$.fn.colorbox({
			iframe : true,
			innerWidth : 515,//子页面宽度
			innerHeight : 360, //子页面高度
			href : url,
			overlayClose : false
		});
	}

	//查询绑定的微博
	function queryBindWeiBo(appId, appName) {
		window.location = "/weibo-studio/queryBindWeiBoPreApp?bean.appId=" + appId + "&bean.appName=" + appName;
	}

	//删除应用
	function delApp(appId) {
		if (confirm("您确定要删除吗?")) {
			var url = '/weibo-studio/deleteApp';
			var param = {
				"bean.appId" : appId
			};
			oprateUser(url, param);
		}
	}

	//添加、更新完之后返回父页面所掉方法
	function refreshTableData() {
		$.fn.colorbox.close();
		loadData();
	}
	$(function() {
		$form = $('#queryForm');
		loadData();
	});
</script>
</head>

<body>
	<div class="ipduanguanli">
		<form id="queryForm"
			action="/weibo-studio/getByPagingApp.action" method="post">
			<input type="hidden" id="rows" name="example.rows" value="10" /> <input
				type="hidden" id="page" name="example.page" value="1" />
		</form>
		<div class="list">
			<table cellpadding="0" cellspacing="0" id="dataTable">
				<tr>
					<th colspan="20"><input class="button1" value="添加应用"
						onclick="addApp();" type="button" /> <span>我的应用列表</span></th>
				</tr>
				<tr class="gruy">
					<td>序号</td>
					<td>应用名称</td>
					<td>App Key</td>
					<td>App Secret</td>
					<td>回调页</td>
					<td class="num_5">操作</td>
				</tr>
				<tr>
					<td colspan="6">数据加载中......</td>
				</tr>
			</table>
			<table style="display: none;" id="topTemplate">
				<tr>
					<th colspan="15"><input class="button1" value="添加应用"
						onclick="addApp();" type="button" /> <span>我的应用列表</span></th>
				</tr>
				<tr class="gruy">
					<td>序号</td>
					<td>应用名称</td>
					<td>App Key</td>
					<td>App Secret</td>
					<td>回调页</td>
					<td class="num_5">操作</td>
				</tr>
			</table>
			<table style="display: none;">
				<tbody id="middleTemplate">
					<tr class="#{rowType}">
						<td style="width: 30px; ">#{num}</td>
						<td style="width: 60px; ">#{appName}</td>
						<td>#{appKey}</td>
						<td>#{appSecret}</td>
						<td>#{appRedirectUri}</td>
						<td class="num_5" style="width: 140px; "><a
							href="javascript:updateApp('#{appId}');">修改</a> <a
							href="javascript:queryBindWeiBo('#{appId}','#{appName}');">查看绑定微博</a>
							<a href="javascript:delApp('#{appId}');">删除</a></td>
					</tr>
				</tbody>
			</table>
			<table style="display: none;" id="bottomTemplate">
				<tr>
					<td colspan="10"><em>
					<a href="javascript: first()">
						<img src="/weibo-studio/style/webcss/img/frist.gif" />
					</a> 
					<a href="javascript: pre()">
						<img src="/weibo-studio/style/webcss/img/pre.gif" />
					</a> 第 <font id="curPage"></font> 页 
					<a href="javascript: next()">
						<img src="/weibo-studio/style/webcss/img/next.gif" />
					</a> 
					<a href="javascript: last()">
						<img src="/weibo-studio/style/webcss/img/last.gif" />
				    </a> 
				    	共 <font id="pageSum"></font> 页</em><b>第 <font id="from"></font> 
				    	至 <font id="to"></font> 条 共 <font id="total"></font> 条
					</b></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>