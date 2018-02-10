<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>基础任务列表</title>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<link href="/weibo-studio/style/webcss/web_kejierudeyingyongliebiao.css" rel="stylesheet" type="text/css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></script>
<script language="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></script>
<script language="javascript" src="/weibo-studio/style/js/loadData.js"></script>
<script language="JavaScript">
	var idField = 'taskId';
	var taskTypeField = 'taskType';
	var taskForeverField = 'taskForever';
	var taskFrequencyField = 'taskFrequency';
	var taskTypeArr = ["我的微博内容采集","我的微博评论采集","我的微博转发采集","我的微博账户信息采集","我的微博关注信息采集","我的微博粉丝信息采集",
	                 "我的关注微博内容采集","我的关注微博评论采集","我的关注微博转发采集"];
	var taskStateField = 'taskState';
	var taskStateArr = ["未启动","已启动","已暂停","已等待","已删除","异常","已完成",'已就绪'];
	//操作调用的方法数组
	var url = ['<a href="javascript:update(\'#{idField}\',\'#{taskTypeField}\',\'#{taskForeverField}\',\'#{taskFrequencyField}\');">修改</a>&nbsp;',
			   '<a href="javascript:startPre(\'#{idField}\',\'#{taskTypeField}\',\'#{taskForeverField}\',\'#{taskFrequencyField}\');">启动</a>&nbsp;',
			   '<a href="javascript:regain(\'#{idField}\',\'#{taskTypeField}\',\'#{taskForeverField}\',\'#{taskFrequencyField}\');">恢复</a>&nbsp;',
			   '<a href="javascript:pause(\'#{idField}\',\'#{taskTypeField}\',\'#{taskForeverField}\',\'#{taskFrequencyField}\');">暂停</a>&nbsp;',
			   '<a href="javascript:start(\'#{idField}\',\'#{taskTypeField}\',\'#{taskForeverField}\',\'#{taskFrequencyField}\');">启动</a>&nbsp;',
			   '<a href="javascript:restart(\'#{idField}\',\'#{taskTypeField}\',\'#{taskForeverField}\',\'#{taskFrequencyField}\');">启动</a>&nbsp;',
			   '<a href="javascript:look(\'#{idField}\');">查看</a>&nbsp;'
			  ];
	//常量数组
	var constantInfo = ['启动&nbsp;','恢复&nbsp;','暂停&nbsp;','修改&nbsp;'];
	var actionArr = [
				constantInfo[3]+url[1]+constantInfo[1]+constantInfo[2]+url[6],
				url[0]+constantInfo[0]+constantInfo[1]+url[3]+url[6],
				url[0]+constantInfo[0]+url[2]+constantInfo[2]+url[6],
				url[0]+constantInfo[0]+constantInfo[1]+constantInfo[2]+url[6],
				url[6],
				url[0]+url[4]+constantInfo[1]+constantInfo[2]+url[6],
				url[0]+url[4]+constantInfo[1]+constantInfo[2]+url[6],
				url[0]+url[4]+constantInfo[1]+constantInfo[2]+url[6],
				constantInfo[3]+constantInfo[0]+constantInfo[1]+constantInfo[2]+url[6]
	            ];
	
	$(function() {
		$form = $('#queryForm');
		loadData();
		window.setInterval(function(){
			loadData();
		},30000);
	});
	
	//切换到高级任务
	function changeSuper() {
		var weiboId = $("#weiboId").val();
		window.location = "/weibo-studio/task/indexSuperPreTask?weibo.weiboId=" + weiboId;
	}
	
	//添加、更新完之后返回父页面所掉方法
	function refreshTableData() {
		$.fn.colorbox.close();
		loadData();
	}
	
	//启动任务
	function start(taskId, taskType, taskForever, taskFrequency) {
		var url = '/weibo-studio/task/startTask';
		var param = {'bean.taskId':taskId, 'bean.taskType':taskType, 'bean.taskForever':taskForever, 'bean.taskFrequency':taskFrequency};
		oprateUser(url,param);
	}
	
	//暂停任务
	function pause(taskId, taskType, taskForever, taskFrequency) {
		var url = '/weibo-studio/task/pauseTask';
		var param = {'bean.taskId':taskId, 'bean.taskType':taskType, 'bean.taskForever':taskForever, 'bean.taskFrequency':taskFrequency};
		oprateUser(url,param);
	}
	
	//恢复任务
	function regain(taskId, taskType, taskForever, taskFrequency) {
		var url = '/weibo-studio/task/regainTask';
		var param = {'bean.taskId':taskId, 'bean.taskType':taskType, 'bean.taskForever':taskForever, 'bean.taskFrequency':taskFrequency};
		oprateUser(url,param);
	}
	
	//准备启动任务
	function startPre(taskId, taskType, taskForever, taskFrequency) {
		var url = "/weibo-studio/task/startPreTask?bean.taskId=" + taskId
				+ '&bean.taskType=' + taskType + '&bean.taskForever=' + taskForever + '&bean.taskFrequency=' + taskFrequency;
		$.fn.colorbox({
			iframe:true,
			innerWidth:650,//子页面宽度
			innerHeight:300, //子页面高度
			href:url,
			overlayClose:false
		});
	}
	
	//遇到异常时重启任务（先查出任务信息）
	function restart(taskId) {
		update(taskId);
	}
	
	//更新任务
	function update(taskId) {
		var url = "/weibo-studio/task/updatePreTask?bean.taskId=" + taskId;
		$.fn.colorbox({
			iframe:true,
			innerWidth:650,//子页面宽度
			innerHeight:400, //子页面高度
			href:url,
			overlayClose:false
		});
	}
	
	//查看任务
	function look(taskId) {
		var url = "/weibo-studio/task/taskLook.jsp?taskId=" + taskId;
		$.fn.colorbox({
			iframe:true,
			innerWidth:650,//子页面宽度
			innerHeight:400, //子页面高度
			href:url,
			overlayClose:false
		});
	}
</script>
</head>
<body>
<div class="kejierudeyingyongliebiao">
	<form id="queryForm" action="/weibo-studio/task/searchBasicTask" method="post">
			<input type="hidden" id="page" name="example.taskLevel" value="0"/>
			<input type="hidden" id="weiboId" name="example.weiboId" value="${weibo.weiboId }"/>
			<input type="hidden" id="rows" name="example.rows" value="10"/>
			<input type="hidden" id="page" name="example.page" value="1"/>
	</form>
	<div class="list">
     	<table cellpadding="0" cellspacing="0" id="dataTable">
			<tr>
				<th colspan="5">
					<input class="button1" value="基础任务" type="button" onclick="window.location.reload()"/>
					<input id="super" class="button1" value="高级任务" onclick="changeSuper();" type="button" />
					<span style="padding-left:50px;" id="title">基础任务列表</span>
				</th>
			</tr>
			<tr class="gruy">
				<td>序号</td>
				<td>任务类型</td>
				<td>任务状态</td>
				<td class="num_5">操作</td>
			</tr>
			<tr>
				<td colspan="4">数据加载中......</td>
			</tr>
		</table>
		<table style="display:none;" id="topTemplate">
			<tr>
				<th colspan="5">
					<input class="button1" value="基础任务" type="button" onclick="window.location.reload()"/>
					<input class="button1" id="super" value="高级任务" onclick="changeSuper();" type="button" />
					<span style="padding-left:50px;" id="title">基础任务列表</span>
				</th>
			</tr>
			<tr class="gruy">
				<td>序号</td>
				<td>任务类型</td>
				<td>任务状态</td>
				<td class="num_5">操作</td>
			</tr>
		</table>
		<table style="display:none;">
			<tbody id="middleTemplate">
			<tr class="#{rowType}">
				<input type="hidden" id="appLevel" value="#{appLevel}"/>
				<td>#{num}</td>
				<td>#{taskType}</td>
				<td>#{taskState}</td>
				<td class="num_5">
					<div class="item" style="display: none">#{taskForever}</div>
					<div class="item" style="display: none">#{taskFrequency}</div>
					<div class="item" style="display: none">#{taskType}</div>
					<div class="item">#{taskId}</div>
				</td>
			</tr>
			</tbody>
		</table>
    </div>
</div>
</body>
</html>