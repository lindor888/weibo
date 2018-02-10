<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>发送微博</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<link href="/weibo-studio/style/webcss/web_ipduanguanli.css" rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></script>
<script language="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></script>
<script language="javascript" src="/weibo-studio/style/js/loadData.js"></script>
<script language="javascript" src="/weibo-studio/style/js/checkValue.js"></script>
<script language="javascript" src="/weibo-studio/style/My97DatePicker/WdatePicker.js"></script>
<script language="JavaScript">

	String.prototype.trim=function(){
	    return this.replace(/(^\s*)|(\s*$)/g, "");
	}

	//校验表单
	function check(form) {
		
		var startDate = $('#startDate').val();
		if(startDate == '') {
			alert('开始时间不能为空');
			return;
		}
		var endDate = $('#endDate').val();
		if(endDate == '') {
			alert('结束时间不能为空');
			return;
		}
		var flag = checkDate('yyyy-MM-dd');
		if(!flag) return;
		loadData();
	}

	//添加、更新完之后返回父页面所掉方法
	function refreshTableData() {
		$.fn.colorbox.close();
		loadData();
	}
	
	//添加、更新完之后返回父页面所掉方法
	function refreshTableData(msg) {
		alert(msg);
		$.fn.colorbox.close();
		loadData();
	}
	
	
	//进入发送微博页
	function pushWeibo(title, url, photo, contentId) {
		var weiboId = $('#weiboId').val();
		if(weiboId == '') {
			alert('您还没有配置微博信息,请先填写微博信息!');
			return;
		}
		var url = "/weibo-studio/pushPreSend?example.title="+replaceChar(title)+"&example.contentId="+replaceChar(contentId)+
				"&example.url="+replaceChar(url)+"&example.logoPhoto=" + replaceChar(photo) +"&example.weiboId=" + replaceChar(weiboId);
		$.fn.colorbox({
			iframe : true,
			innerWidth : 550,//子页面宽度
			innerHeight : 300, //子页面高度
			href : url,
			overlayClose : false
		});
	}

	$(function() {
		$form = $('#queryForm');
	});
	
	function replaceChar(val) {
		if(val.trim() == '-') {
			return '';
		} else {
			return val;
		}
	}
</script>
</head>

<body>

	<div class="ipduanguanli">
		<form id="queryForm" action="/weibo-studio/getFromSearchSend.action" method="post">
			<input type="hidden" id="rows" name="example.rows" value="10" /> 
			<input type="hidden" id="page" name="example.page" value="1" />
			<dl>
				<dd>
					关键词:<input type="text" name="example.q"/>&nbsp;&nbsp;
					<s:select label="类型" id="coreName" class="bg30" name="example.coreName" list="#{'article':'正文','audio':'音频','audioalbum':'音频集','photo':'图集','topic':'专题','video':'视频','videoalbum':'视频集'}"/>
					<!-- <input class="submit" value="发送新微博" type="button" onclick="sendNewWeibo();"/> -->
					<input type="text" style="display: none" /> 
				</dd>
				<dd>处理时间段：
<% 
	Date today = new Date();
	Date lastDay = new Date(today.getTime() - 24*3600*1000*3);
	String format = "yyyy-MM-dd";
	SimpleDateFormat sdf = new SimpleDateFormat(format);
	String beginDay = sdf.format(lastDay);
	String endDay = sdf.format(today);
%>
					<input type="text" id="startDate" name="example.beginTime" class="text1 Wdate startDate" style="width:100px;" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" value="<%= beginDay%>"/> -
					<input type="text" id="endDate" name="example.endTime" class="text1 Wdate endDate" style="width:100px;" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  value="<%= endDay%>"/>
					<!-- <s:select label="微博" id="weiboId" class="bg30" emptyOption="暂无微博" name="example.weiboId" list="weibos" listKey="weiboId" listValue="weiboUserName" listTitle="weiboUserName"/> -->
					微博:<select name="example.weiboId" id="weiboId">
						<s:if test="weibos != null && weibos.size() > 0">
							<s:iterator value="weibos" var="weibo">
								<option value="${weibo.weiboId }">${weibo.weiboUserName}</option>
							</s:iterator>
						</s:if>
						<s:else>
							<option value="">-暂无微博-</option>
						</s:else>
					</select>
                	<input class="submit" value="查询" type="button" onclick="check(this.form);"/>
                </dd>
			</dl>
		</form>
		<div class="list">
			<table cellpadding="0" cellspacing="0" id="dataTable">
				<tr class="gruy">
					<td>序号</td>
					<td>标题</td>
					<td>创建时间</td>
					<td class="num_5">操作</td>
				</tr>
				<tr>
					<td colspan="4">请输入关键词和类型进行数据查询</td>
				</tr>
			</table>
			<table style="display: none;" id="topTemplate">
				<tr class="gruy">
					<td>序号</td>
					<td>标题</td>
					<td>创建时间</td>
					<td class="num_5">操作</td>
				</tr>
			</table>
			<table style="display: none;">
				<tbody id="middleTemplate">
					<tr class="#{rowType}">
						<td>#{num}</td>
						<td>#{title}</td>
						<td>#{currentdate}</td>
						<td class="num_5">
							<a href="javascript:pushWeibo('#{title}','#{url}','#{logoPhoto}','#{id}')">发送</a>
						</td>
					</tr>
				</tbody>
			</table>
			<table style="display: none;">
				<tbody id="noDataTable">
					<tr>
					<td colspan="4">暂无数据</td>
				</tr>
				</tbody>
			</table>
			<table style="display: none;" id="bottomTemplate">
				<tr>
					<td colspan="5"><em><a href="javascript: first()"><img src="/weibo-studio/style/webcss/img/frist.gif" /></a> 
					<a href="javascript: pre()"><img src="/weibo-studio/style/webcss/img/pre.gif" /></a> 第 <font id="curPage"></font> 页 
					<a href="javascript: next()"><img src="/weibo-studio/style/webcss/img/next.gif" /></a> 
					<a href="javascript: last()"><img src="/weibo-studio/style/webcss/img/last.gif" /></a> 
					共 <font id="pageSum"></font> 页</em><b>第 <font id="from"></font> 至 <font id="to"></font> 条 共 <font id="total"></font> 条
					</b></td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>