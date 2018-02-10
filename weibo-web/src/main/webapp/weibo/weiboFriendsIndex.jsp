<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>我的关注微博</title>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<link href="/weibo-studio/style/webcss/web_ipduanguanli.css" rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></script>
<script language="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></script>
<script language="javascript" src="/weibo-studio/style/js/loadData.js"></script>
<script language="JavaScript">

	//添加关注
	function addFriendWeibo() {
		var weiboId = $("#weiboId").val();
		var url = "/weibo-studio/addPreFriend?bean.weiboId=" + weiboId;
		$.fn.colorbox({
			iframe:true,
			innerWidth:505,//子页面宽度
			innerHeight:240, //子页面高度
			href:url,
			overlayClose:false
		});
		
	}
	//删除关注微博
	function delFriendWeibo(friendWeiboId, weiboToken, friendWeiboUid, weiboUserName, weiboPassword, appId) {
		if(confirm("您确定要删除吗?")){ 
			var url = '/weibo-studio/deleteFriend';
			var param = {"bean.friendWeiboId":friendWeiboId,"bean.weiboToken":weiboToken,"bean.friendWeiboUid":friendWeiboUid,
					"bean.weiboUserName":weiboUserName,"bean.weiboPassword":weiboPassword,"bean.appId":appId};
			del(url,param);
		}
	}
	
	//操作用户
	function del(url,param){
		$.ajax({
			type: "POST",
			url: url,
			data: param,
			success: function(msg){
			   alert(msg.result);
			   if(url.indexOf('delete')>-1){
				  if(from==to&&curPage>1){
					  $form[0]["example.page"].value = curPage - 1;
				  }
			   }
			   loadData();
			}
		});
	}
	
	//返回我的微博
	function back() {
		window.history.back();
	}
	
	//校验表单
	function check(form){
		$("#page").val(1);
		loadData();
	}
	
	//添加、更新完之后返回父页面所掉方法
	function refreshTableData() {
		$.fn.colorbox.close();
		loadData();
	}
	
	$(function(){
		$form = $('#queryForm');
		loadData();
	});
</script>
</head>

<body>

<div class="ipduanguanli">
    	<form id="queryForm" action="/weibo-studio/getByPagingFriend.action" method="post">
    		<input type="hidden" id="rows" name="example.rows" value="10"/>
			<input type="hidden" id="page" name="example.page" value="1"/>
			<input type="hidden" id="weiboId" name="example.weiboId" value="${bean.weiboId }"/>
	        <dl>
	            <dd>
	                <span>微博uid：</span><input type="text" name="example.friendWeiboUid"/>
	                <input type="text" style="display: none"/>  
	                <input class="submit" value="查询" type="button" onclick="check()" />
	            </dd>
	        </dl>
        </form>
     	<div class="list">
     	<table cellpadding="0" cellspacing="0" id="dataTable">
			<tr>
				<th colspan="5">
					<input class="button1" value="添加关注" onclick="addFriendWeibo();" type="button" />
					<input class="button1" value="返回我的微博" onclick="back();" type="button" />
					<span>我的关注微博</span>
				</th>
			</tr>
			<tr class="gruy">
				<td>序号</td>
				<td>微博uid</td>
				<td>微博名称</td>
				<td class="num_5">操作</td>
			</tr>
			<tr>
				<td colspan="4">数据加载中......</td>
			</tr>
		</table>
		<table cellpadding="0" cellspacing="0" id="noDataTable" style="display:none;">
			<tr>
				<td colspan="4">暂无数据</td>
			</tr>
		</table>
		<table style="display:none;" id="topTemplate">
			<tr>
				<th colspan="5">
					<input class="button1" value="添加关注" onclick="addFriendWeibo();" type="button" />
					<input class="button1" value="返回我的微博" onclick="back();" type="button" />
					<span>我的关注微博</span>
				</th>
			</tr>
			<tr class="gruy">
				<td>序号</td>
				<td>微博uid</td>
				<td>微博名称</td>
				<td class="num_5">操作</td>
			</tr>
		</table>
		<table style="display:none;">
			<tbody id="middleTemplate">
			<tr class="#{rowType}">
				<td>#{num}</td>
				<td>#{friendWeiboUid}</td>
				<td>#{friendWeiboName}</td>
				<td class="num_5">
					<a href="javascript:delFriendWeibo('#{friendWeiboId}','#{weiboToken}','#{friendWeiboUid}',
					'#{weiboUserName}','#{weiboPassword}','#{appId}');">删除</a>
				</td>
			</tr>
			</tbody>
		</table>
		<table style="display:none;" id="bottomTemplate">
			<tr>
				<td colspan="5"><em><a href="javascript: first()"><img
							src="/weibo-studio/style/webcss/img/frist.gif"/></a> <a href="javascript: pre()"><img
							src="/weibo-studio/style/webcss/img/pre.gif"/></a> 第  <font id="curPage"></font> 页 <a href="javascript: next()"><img
							src="/weibo-studio/style/webcss/img/next.gif"/></a> <a href="javascript: last()"><img
							src="/weibo-studio/style/webcss/img/last.gif"/></a> 共  <font id="pageSum"></font> 页</em><b>第  <font id="from"></font> 至  <font id="to"></font> 条 共   <font id="total"></font> 条</b></td>
			</tr>
		</table>
        </div>
</div>

</body>
</html>