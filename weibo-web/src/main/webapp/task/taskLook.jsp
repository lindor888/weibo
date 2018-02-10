<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
  	<script src="/weibo-studio/style/js/jquery-1.4.2.min.js" type="text/javascript"></script>
  	<script src="/weibo-studio/style/js/jquery.colorbox-min.js" type="text/javascript"></script>
  	<script src="/weibo-studio/style/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
  	<script src="/weibo-studio/style/js/checkValue.js" type="text/javascript"></script>
    <link type="text/css" rel="stylesheet" href="/weibo-studio/style/css/global.css" />
    <link type="text/css" rel="stylesheet" href="/weibo-studio/style/css/style.css" />   
 	<style>
		i{color: #CC0000;font-style: normal;padding-top: 4px;}label.error {color: red;}
		label{margin: 0 4px;}
	</style>
 	
    <script type="text/javascript">
    function getParameter(args){
    	var url = window.location.href;
    	var paraString = url.substring(url.indexOf('?') + 1, url.length).split('&');//截取出url?后面的字符以&的字符
    	var paraObj = {};
    	for (var i = 0; j = paraString[i]; i++) {
    		paraObj[j.substring(0, j.indexOf('=')).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    	}
    	var returnValue = paraObj[args.toLowerCase()];
    	if (typeof(returnValue) == 'undefined') {
    		return "";
    	}
    	else {
    		return returnValue;
    	}
    }
	
	function selfWindowClose(){
		parent.$.fn.colorbox.close();
		parent.refreshTableData();
	}
	
	var taskTypeArr = ["我的微博内容采集","我的微博评论采集","我的微博转发采集","我的微博账户信息采集","我的微博关注信息采集","我的微博粉丝信息采集",
		                 "我的关注微博内容采集","我的关注微博评论采集","我的关注微博转发采集","微博关键词搜索","第三方微博转发采集","第三方微博内容采集",
		                 "粉丝全量列表采集","第三方微博评论采集","第三方微博账户信息采集"];
	
	var tableIdArr = ["","","","","","","","","","taskThirdWeiboSearchCollect","","taskThirdWeiboContentCollect","taskThirdWeiboFollowerCollect","",""
	                  ];
	
	var taskStateArr = ["未启动","已启动","已暂停","已等待","已删除","异常","已完成",'已就绪'];
	var taskId = getParameter("taskId");
	var taskLookUrl = "/weibo-studio/task/lookTask?bean.taskId=" + taskId;
	
	function getTaskInfo(){
		$.ajax({
			type: "POST",
			url: taskLookUrl,
			async: false,
			success: function(msg){
				var data = msg;
				var taskType = data.taskType;
				var taskStateNum = data.taskState;
				var dataNum = data.dataNum;
				var countList = data.countList;
				var taskState = taskStateArr[taskStateNum];
				var sex = data.sex;
				$("#taskState").html(taskState);
				$("#dataNum").html(dataNum);
								
				
				if(taskTypeArr[taskType]=="微博关键词搜索"){
					if(countList.length>0){
						var tableHtml = "";
						for(var i=0;i<countList.length;i++){
							tableHtml += "<tr>";
							tableHtml += "<td>" + (i+1) + "</td>";
							tableHtml += "<td>" + countList[i].lanmu + "</td>";
							tableHtml += "<td>" + countList[i].q + "</td>";
							tableHtml += "<td>" + countList[i].countTime + "</td>";
							tableHtml += "<td>" + countList[i].countWeiboNum + "</td>";
							tableHtml += "<td>" + countList[i].countUserNum + "</td>";
							tableHtml += "</tr>";
						}
						$("#"+tableIdArr[taskType]).append(tableHtml);
						$("#"+tableIdArr[taskType]).css("display","");
					}
				}else if(taskTypeArr[taskType] == "粉丝全量列表采集"){
					if(sex!=null&&sex!=""){
						var sexTableHtml = "";
						sexTableHtml += "<tr><td>" + sex.countMaleNum + "</td><td>" +  sex.countFemaleNum + "</td></tr>";
						$("#"+tableIdArr[taskType]+"_sex").append(sexTableHtml);
						$("#"+tableIdArr[taskType]+"_sex").css("display","");
					}
					if(countList.length>0){
						var districtTableHtml = "";
						for(var i=0;i<countList.length;i++){
							districtTableHtml += "<tr>";
							districtTableHtml += "<td>" + (i+1) + "</td>";
							districtTableHtml += "<td>" + countList[i].district + "</td>";
							districtTableHtml += "<td>" + countList[i].countDistrictNum + "</td>";
							districtTableHtml += "</tr>";
						}
						$("#"+tableIdArr[taskType]+"_district").append(districtTableHtml);
						$("#"+tableIdArr[taskType]+"_district").css("display","");
					}
				}else if(taskTypeArr[taskType] == "第三方微博内容采集"){
					var tableHtml = "";
					for(var i=0;i<countList.length;i++){
						tableHtml += "<tr>";
						tableHtml += "<td>" + (i+1) + "</td>";
						tableHtml += "<td>" + countList[i].countTime + "</td>";
						tableHtml += "<td>" + countList[i].countCommentNum + "</td>";
						tableHtml += "<td>" + countList[i].countRepostNum + "</td>";
						tableHtml += "<td>" + countList[i].countWeiboNum + "</td>";
						tableHtml += "</tr>";
					}
					$("#"+tableIdArr[taskType]).append(tableHtml);
					$("#"+tableIdArr[taskType]).css("display","");
				}
			}
		});
	}
	$(function() {
		getTaskInfo();
	});
</script>

<head>
<title>查看任务</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
</head>
<body>
<div class="Area">
  <div id="right">
     <div class="cut1">
     	<div id="subnav_0" class="block2">
	     <div class="blank10"></div>
	     <div class="blank1_b w98"></div>
	     <div class="blank10"></div>
	     <p><b>当前任务状态为：</b><span id="taskState"></span></p>
	     <p><b>当前任务已抓取数据：</b><span id="dataNum"></span></p>
	     <div class="blank10"></div>
	     <div class="blank1_b w98"></div>
	     <div class="blank10"></div>
	     <p>
	     	<table width="100%" cellpadding="0" cellspacing="0" border="1" id="taskThirdWeiboSearchCollect" style="display:none">
	     		<tr>
					<td>序号</td>
					<td>栏目</td>
					<td>关键字</td>
					<td>时间</td>
					<td>提及关键字微博数</td>
					<td>提及关键字用户数</td>
				</tr>
	     	</table>
	    
	     	<table width="100%" cellpadding="0" cellspacing="0" border="1" id="taskThirdWeiboFollowerCollect_sex" style="display:none">
	     		<tr>
					<td>男粉丝数</td>
					<td>女粉丝数</td>
				</tr>
	     	</table>
	    
	     	<table width="100%" cellpadding="0" cellspacing="0" border="1" id="taskThirdWeiboFollowerCollect_district" style="display:none">
	     		<tr>
					<td>序号</td>
					<td>地域</td>
					<td>数目</td>
				</tr>
	     	</table>
	    
	     	<table width="100%" cellpadding="0" cellspacing="0" border="1" id="taskThirdWeiboContentCollect" style="display:none">
	     		<tr>
					<td>序号</td>
					<td>时间</td>
					<td>评论数</td>
					<td>转发数</td>
					<td>微博数</td>
				</tr>
	     	</table>
	     </p>
	     <ul class="button_1">
           <li><span onclick="selfWindowClose()">返回</span></li>
		 </ul>
		<p></p>
	</div>
    </div>
   </div>
  </div>
</body>
</html>
