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
  		//'9':'微博关键词搜索','10':'第三方微博转发采集','11':'第三方微博内容采集',
		//'12':'粉丝全量列表采集','13':'第三方微博评论采集','14':'第三方微博用户信息采集'
		var taskType = [9,10,11,12,13,14];//任务类型
    	var taskLevel = '${bean.taskLevel}';
      	$(document).ready(function(){ 
      		var taskFrequencySort = '${bean.taskFrequencySort}';
      		var taskEverySort = '${bean.taskEverySort}';
      		var forever = '${bean.forever}';
        	$('#frequencySort').val(taskFrequencySort);
        	$('#everySort').val(taskEverySort);
        	$("#taskStartTime").val('${bean.taskBeginTimeStr}');
        	$("#taskEndTime").val('${bean.taskEndTimeStr}');
        	myClick(taskFrequencySort);
        	if(taskFrequencySort == 1) {//2时会隐藏时间点
        		callBackMyClickSecond(taskEverySort);
        	}
        	if(forever == 'true') {
				checkTimeForever();
			}
        	
        	if(taskLevel == 0) {//基础任务
        		$("#rwlx").hide();
        		showXml("hide");
        	} else {//高级任务
        		$("#rwlx").show();
        		var taskType = '${bean.taskType}';
          		$("#taskType").val(taskType);
            	showXml(taskType);//进入页面显示任务类型的第一个
        	}
        	
      	}); 
      	
      //选取时间点(修改进入页面时调用)
		function  callBackMyClickSecond(value){
		    if(value==1){
				$('#everySecond').show(); 
				$('#everyMinute').show(); 
				$('#everyHour').show();
				$('#everyWeek').hide();
				$('#everyDay').hide();
				$('#everyMonth').hide();
				$('#point').show();
		    }else if(value == 2){
				$('#everySecond').show(); 
			    $('#everyMinute').show(); 
			    $('#everyHour').show();
				$('#everyWeek').show();
				$('#everyDay').hide();
				$('#everyMonth').hide(); 
				$('#point').show();
           }else if(value == 3){
				$('#everySecond').show(); 
				$('#everyMinute').show(); 
				$('#everyHour').show();
				$('#everyDay').show();
				$('#everyWeek').hide();
				$('#everyMonth').hide(); 
				$('#point').show();
            }else {
				$('#everySecond').show(); 
				$('#everyMinute').show(); 
				$('#everyHour').show();
				$('#everyWeek').hide();
				$('#everyDay').hide();
				$('#everyMonth').hide();
				$('#point').show();
           }
		}
      	

      	//定时类型选择
		function myClick(value){
		  if(value==1){
		      $('#everySecond').show(); 
              $('#everyMinute').show(); 
              $('#everyHour').show();
		      $('#everyWeek').hide();
		      $('#everyDay').hide();
		      $('#everyMonth').hide();
		      $('#everyMany').show();
		      $('#point').show();
		      $('#timeBetween').show();
			  $('#timeever').show();
		  } else if (value==2){
		      $('#everySecond').show(); 
              $('#everyMinute').show(); 
              $('#everyHour').show();
		      $('#everyWeek').hide();
		      $('#everyDay').hide();
		      $('#everyMonth').hide(); 
		      $('#everyMany').hide();
		      $('#point').hide();
		      $('#timeBetween').show();
			  $('#timeever').show();
		  } else if (value == 3){
			  $('#everySecond').hide(); 
              $('#everyMinute').hide(); 
              $('#everyHour').hide();
		      $('#everyWeek').hide();
		      $('#everyDay').hide();
		      $('#everyMonth').hide(); 
		      $('#everyMany').hide();
		      $('#point').hide();
			  $('#timeBetween').hide();
			  $('#timeever').hide();
		  } else {
			  myClickSecond("1");
		  }
		}
		
		//选取时间点
		function  myClickSecond(value){
		    if(value==1){
				$('#tempHour').val("");
				$('#tempMinute').val("");
				$('#tempSecond').val("");
				$('#everySecond').show(); 
				$('#everyMinute').show(); 
				$('#everyHour').show();
				$('#everyWeek').hide();
				$('#everyDay').hide();
				$('#everyMonth').hide();
				$('#point').show();
		    }else if(value == 2){
				$('#tempHour').val("");
				$('#tempMinute').val("");
				$('#tempSecond').val("");
				$('#everySecond').show(); 
			    $('#everyMinute').show(); 
			    $('#everyHour').show();
				$('#everyWeek').show();
				$('#everyDay').hide();
				$('#everyMonth').hide(); 
				$('#point').show();
           }else if(value == 3){
				$('#tempHour').val("");
				$('#tempMinute').val("");
				$('#tempSecond').val("");
				$('#everySecond').show(); 
				$('#everyMinute').show(); 
				$('#everyHour').show();
				$('#everyDay').show();
				$('#everyWeek').hide();
				$('#everyMonth').hide(); 
				$('#point').show();
            }else {
				$('#tempHour').val("");
				$('#tempMinute').val("");
				$('#tempSecond').val("");
				$('#everySecond').show(); 
				$('#everyMinute').show(); 
				$('#everyHour').show();
				$('#everyWeek').hide();
				$('#everyDay').hide();
				$('#everyMonth').hide();
				$('#point').show();
           }
		}

//时间永远标记
function checkTimeForever(){
  if($("#timeForever").attr("checked") == true){
     $('#taskStartTime').val("");
     $('#taskEndTime').val("");
     $('#timeBetween').hide();
  }else{
    $('#timeBetween').show();
  }
}

//岂止时间标记
function startEndTime(){
  var startTime = $('#taskStartTime').val();
  var endTime = $('#taskEndTime').val(); 
  if(null != startTime || null != endTime){
    $("#timeForever").attr("checked",'false');
  }
}

</script>

<script type="text/javascript">	 
 
    function timebetweenHide(){
    	
      var taskStartTime = $('#taskStartTime').val();
      var taskEndTime = $('#taskEndTime').val();
      if(null!=taskStartTime && taskStartTime.length>0){
        $('#timeever').hide();
      }else if(null!=taskEndTime && taskEndTime.length>0){
        $('#timeever').hide();
      }else if(taskEndTime.length <=0 && taskStartTime.length<=0){
        $('#timeever').show();
      }
    }

    function getTimeCount(obj){
       if(obj!=null && obj.length>0){
         return 1;
       }else{
         return 0;
       }
    }
 
	function addTimerTask(){
  		var startTime = $('#taskStartTime').val();
  		var endTime = $('#taskEndTime').val();

    	//时间永远标记
    	var timeForever ="";
    	$('input[name=bean.forever]:checkbox').each(function(){ 
      	if($('input[name=bean.forever]').attr("checked")==true){
         	timeForever = "true";
      	}else{
         	timeForever = "";
      	}
    });
	  //月
	    var tempMonth = $('#tempMonth').val();
	  //天
	    var  tempDay = $('#tempDay').val();
	  //星期
	    var  tempWeek = $('#tempWeek').val();
	  //小时
	    var  tempHour = $('#tempHour').val();
	  //分钟
	    var tempMinute = $('#tempMinute').val();
	  //秒
	    var tempSecond = $('#tempSecond').val();
	  //everySort   
	   var everySort =  $(":radio[name='bean.taskEverySort']:checked").val(); 
	  //定时类型
	  var frequencySort =  $(":radio[name='bean.taskFrequencySort']:checked").val(); 
	
      if((timeForever=="" || timeForever.length<=0) && frequencySort != 3){
         if(null == startTime || startTime.length<=0){
             if(null == endTime || endTime.length<=0){
                 alert("请选择时间");
	             return;
             }
         }
      }

	   if(frequencySort ==1){
	     if(everySort ==1){
	       if(tempHour==null || tempHour.length<=0){
	         alert("请填写小时");
	         return false;
	       }else if(tempHour !=null && tempHour.length >0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempHour)){
                 alert("请填写规范时间");
	             return false;
              }else if(tempHour>24 || tempHour <=0){
                 alert("时间格式不正确");
                 return false;
              }
	       }
	       
	       if(null ==tempMinute || tempMinute.length <=0){
	          alert("请填写分钟");
	          return false;
	       }else if(null!= tempMinute && tempMinute.length >0){
	          var patrn=/^[0-9]{1,2}$/; 
	          if (!patrn.exec(tempMinute)){
                 alert("分钟格式不正确");
	             return false;
              }else if(tempMinute>=60 || tempMinute < 0){
                 alert("分钟在0-59之间");
                 return false;
              }
	       }
	       
	       if(null==tempSecond || tempSecond.length <=0){
	          alert("请填写时间秒");
	          return false;
	       }else if(tempSecond !=null && tempSecond.length > 0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempSecond)){
                 alert("秒格式不正确");
	             return false;
              }else if(tempSecond>=60 || tempSecond < 0){
                 alert("秒在0-59之间");
                 return false;
              }
	       }
	       
	       if(tempSecond != null && tempMinute.length == 0 && tempSecond.length > 0){
	          alert("请填写分钟");
	          return false;
	       }else if(tempSecond!=null && tempMinute!=null && tempSecond.length>0 && tempMinute.length>0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempMinute)){
                 alert("分钟格式不正确");
	             return false;
              }else if(tempMinute>=60 || tempMinute < 0){
                 alert("分钟在0-59之间");
                 return false;
              }
	       }
	       
	     }else if(everySort ==2){
	       if(tempWeek ==null || tempWeek.length<=0 ){
	          alert("请填写星期");
	          return false;
	       }else if(tempWeek != null && tempWeek.length > 0){
	          var patrn=/^[1-7]{1}$/; 
              if(!patrn.exec(tempWeek)){
                 alert("星期格式不正确");
	             return false;
              }
	       }
	       if(tempHour==null || tempHour.length<=0){
	         alert("请填写小时");
	         return false;
	       }else if(tempHour !=null){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempHour)){
                 alert("请填写规范时间");
	             return false;
              }else if(tempHour>24 || tempHour <=0){
                 alert("时间格式不正确");
                 return false;
              }
	       }
	       if(tempSecond!=null && tempSecond.length>0 && tempMinute.length<=0){
	          alert("请填写分钟");
	          return false;
	       }else if(tempMinute !=null && tempMinute.length > 0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempMinute)){
                 alert("请填写规范时间");
	             return false;
              }else if(tempMinute > 60 || tempMinute <=0){
                 alert("分钟在0-59之间");
                 return false;
              }
	       }
	       if(tempSecond !=null && tempSecond.length > 0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempSecond)){
                 alert("秒格式不正确");
	             return false;
              }else if(tempSecond>=60 || tempSecond < 0){
                 alert("秒在0-59之间");
                 return false;
              }
	       }
	     }else if(everySort ==3){
	       var date = new Date();
	       var year  = date.getFullYear();
	       var month = date.getMonth()+1;
	       var isLeapYear = false;
	       if((year%4==0 && year%100!=0) || year%400==0){
	         isLeapYear = true;
	       }
	       if(tempDay == null || tempDay.length<=0 ){
	         alert("请填写天");
	         return false;
	       }else if(tempDay != null && tempDay.length > 0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempDay)){
                 alert("天格式不正确");
	             return false;
              }else{
		         switch(month){
		            case 1,3,5,7,8,10,12:
		            if(tempDay < 0 || tempDay > 31){
		              alert("天数在1-31之间");
		              return false;
		            }
		            break;
		          case 4,6,9,11:
		            if(tempDay < 0 || tempDay > 30){
		              alert("天数在1-30之间");
		              return false;
		            }
		           break;
		          case 2:
		            if(isLeapYear==true){
		              if(tempDay < 0 || tempDay > 29){
		                alert("天数在1-29之间");
		                return false;
		              }
		            }else{
		              if(tempDay < 0 || tempDay > 28){
		               alert("天数在1-28之间");
		               return false;
		              } 
		            }
		            break;
		         }
		     }    
	       }
	       if(tempHour==null || tempHour.length<=0){
	         alert("请填写小时");
	         return false;
	       }else if(tempHour !=null){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempHour)){
                 alert("请填写规范时间");
	             return false;
              }else if(tempHour>24 || tempHour <=0){
                 alert("时间格式不正确");
                 return false;
              }
	       }
	       if(tempSecond!=null && tempSecond.length>0 && tempMinute.length<=0){
	          alert("请填写分钟");
	          return false;
	       }else if(tempMinute !=null && tempMinute.length > 0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempMinute)){
                 alert("请填写规范时间");
	             return false;
              }else if(tempMinute > 60 || tempMinute <=0){
                 alert("分钟在0-59之间");
                 return false;
              }
	       }
	       if(tempSecond !=null && tempSecond.length > 0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempSecond)){
                 alert("秒格式不正确");
	             return false;
              }else if(tempSecond>=60 || tempSecond < 0){
                 alert("秒在0-59之间");
                 return false;
              }
	       }
	     }
	     
	   }else if(frequencySort == 2){
	       if(tempHour !=null && tempHour.length>0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempHour)){
                 alert("请填写规范时间");
	             return false;
              }else if(tempHour>24 || tempHour <=0){
                 alert("时间格式不正确");
                 return false;
              }
	       }
	       
	       if(tempMinute !=null && tempMinute.length > 0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempMinute)){
                 alert("请填写规范时间");
	             return false;
              }else if(tempMinute > 60 || tempMinute <=0){
                 alert("分钟在0-59之间");
                 return false;
              }
	       }
	       
	       if(tempSecond !=null && tempSecond.length > 0){
	          var patrn=/^[0-9]{1,2}$/; 
              if (!patrn.exec(tempSecond)){
                 alert("秒格式不正确");
	             return false;
              }else if(tempSecond>=60 || tempSecond < 0){
                 alert("秒在0-59之间");
                 return false;
              }
	       }
	       if((tempHour==null || tempHour.length==0) 
	            && (tempMinute==null || tempMinute.length==0) 
	            && (tempSecond== null || tempSecond.length==0)
	       ){
	          alert("请填写间隔时间");
	          return false;
	       }
	    
	       var timesize = 0;
	       if(getTimeCount(tempHour)==1){
	          timesize++;
	       }
	       if(getTimeCount(tempMinute)==1){
	          timesize++;
	       }
	       if(getTimeCount(tempSecond)==1){
	          timesize++;
	       }
	       if(timesize>1){
	          alert("只能单选时分秒其一");
	          return false;
	       }
	   }else if(frequencySort==3){
			$('#taskStartTime').val('');
	  		$('#taskEndTime').val('');
	   }
	   
	   if(taskLevel == 1) {//高级任务时进行以下处理
		   var taskType = $("#taskType").val();
		   var htmlArr = new Array();
		   //组成xml文件
		   htmlArr.push("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		   htmlArr.push("<config>");
		   if(taskType == taskType[0]) {
			   var keyWord = $("#keyWord").val();
			   var startDate = $("#startDate").val();
			   var endDate = $("#endDate").val();
			   var program = $("#program").val();
			   if(isNull(keyWord)) {alert("关键词不能为空");return false;};
		   		htmlArr.push("<q>");
			   		htmlArr.push(keyWord);
		   		htmlArr.push("</q>");
			   if(isNull(startDate)) {
					alert("开始时间不能为空");
					return false;
				}
			   if(isNotNull(endDate)) {
				   var flag = checkDateTwo("yyyy-MM-dd HH:mm:ss");
				   if(!flag) {return false;};
				} else {
					alert("结束时间不能为空");
					return false;
				}
			   if(isNull(program)) {alert("栏目不能为空");return false;};
				   htmlArr.push("<lanmu>");
				   		htmlArr.push(program);
				   htmlArr.push("</lanmu>");
				   htmlArr.push("<startTime>");
				   		htmlArr.push(startDate);
				   htmlArr.push("</startTime>");
				   htmlArr.push("<endTime>");
				   		htmlArr.push(endDate);
				   htmlArr.push("</endTime>");
			   htmlArr.push("</config>");
		   } else {
			   var uid = $("#uid").val();
			   if(isNull(uid)) {alert("微博uid不能为空");return false;};
		   		htmlArr.push("<uid>");
		   			htmlArr.push(uid);
		   		htmlArr.push("</uid>");
	  			htmlArr.push("</config>");
		   }
		   $("#taskConfigXml").val(htmlArr.join(""));
	   }
	  
	  $('#mainform').action="/weibo-studio/task/startTask";
	  $('#mainform').submit();
	}
	
	//添加、更新完之后返回父页面所掉方法
	function refreshTableData() {
		$.fn.colorbox.close();
		loadData();
	}
	
	function selfWindowClose(){
		parent.$.fn.colorbox.close();
	}
	
	//显示隐藏操作
	function showXml(val) {
		$(".xml").hide();
		$(".xml" + val).show();
	}
</script>

<head>
<title>定时任务修改</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
</head>
<body>
<div class="Area">
  <div id="right">
     <div class="cut1">
	    <div class="block1">
		  <div class="clear"></div>
		  </div>
		  <div id="subnav_0" class="block2" style="display:">
          <s:form name="mainform" id="mainform" namespace="/task" action="updateTask" method="post" theme="simple">
             <input type="hidden" name="bean.taskId" value="${bean.taskId }"/>
             <input type="hidden" id="taskConfigXml" name="bean.taskConfigXml" value=""/>
             <input type="hidden" id="bean.taskState" name="bean.taskState" value="${bean.taskState }"/>
	         <p id="rwlx" style="display: none"><i>*</i><b>任务类型</b>&nbsp;&nbsp;&nbsp;&nbsp;
		        <span>
		             <s:select list="#{'9':'微博关键词搜索','10':'第三方微博转发采集','11':'第三方微博内容采集',
		             	'12':'粉丝全量列表采集','13':'第三方微博评论采集','14':'第三方微博用户信息采集'}" 
		             	listKey="key" listValue="value"  id="taskType" name="bean.taskType" theme="simple" onchange="showXml(this.value);"/>
		        </span>
		     </p>
	         <p><i>*</i><b>时间范围</b>&nbsp;&nbsp;&nbsp;&nbsp;<span id="timeBetween">开始日期<s:textfield name="bean.taskBeginTime" theme="simple" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'})" id="taskStartTime" onblur="timebetweenHide()"/>&nbsp;&nbsp;结束日期<s:textfield name="bean.taskEndTime" theme="simple"  onblur="timebetweenHide()" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd H:mm:ss'})" id="taskEndTime"/>&nbsp;&nbsp;</span><span id="timeever">永远<s:checkbox name="bean.forever" theme="simple" id="timeForever" onclick="checkTimeForever();"/></span></p>
	         <p><i>*</i><b>定时类型</b>&nbsp;&nbsp;&nbsp;<s:radio list="#{'1':'定点','2':'间隔','3':'一次'}" listKey="key" listValue="value" id="frequencySort" name ="bean.taskFrequencySort"  theme="simple" onclick="myClick(this.value);"/></p>
             <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        <span id="point">
		             <s:radio list="#{'1':'每天','2':'每周','3':'每月'}" listKey="key" listValue="value" 
		                      id="everySort" name="bean.taskEverySort" onclick="myClickSecond(this.value)" theme="simple"/>
		        </span>
		     </p>
			 <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			    <span id="everyMonth">月<input type="input" name="tempMonth"   id="tempMonth" value="${tempMonth }"/></span>
			    <span id="everyDay">天<input type="input" name="tempDay"       id="tempDay" value="${tempDay }"   /></span>
			    <span id="everyWeek">星期<input type="input" name="tempWeek"   id="tempWeek" value="${tempWeek }" /></span>
			    <span id="everyHour">小时<input type="input" name="tempHour"   id="tempHour" value="${tempHour }"  /></span>
			    <span id="everyMinute">分<input type="input" name="tempMinute" id="tempMinute" value="${tempMinute }"/></span>
			    <span id="everySecond">秒<input type="input" name="tempSecond" id="tempSecond" value="${tempSecond }"/></span>
		    </p>
		    <p class="xml xml9"><i>*</i><b>关键词</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        <span id="point" >
		             <input type="input" name="keyWord" id="keyWord" value="${bean.keyWord }" maxlength="50"/>
		        </span>
		     </p>
		     <p class="xml xml9"><i>*</i><b>开始时间</b>&nbsp;&nbsp;&nbsp;&nbsp;
		        <span id="point">
		             <input type="input" name="startDate" value="${bean.startDateStr }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="startDate" />
		        </span>
		     </p>
		     <p class="xml xml9"><i>*</i><b>结束时间</b>&nbsp;&nbsp;&nbsp;&nbsp;
		        <span id="point">
		             <input type="input" name="endDate" value="${bean.endDateStr }" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" id="endDate"/>
		        </span>
		     </p>
		     <p class="xml xml9"><i>*</i><b>栏目</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        <span id="point">
		             <input type="input" name="program" id="program" value="${bean.program }" maxlength="32"/>
		        </span>
		     </p>
		     <p class="xml xml10 xml11 xml12 xml13 xml14"><i>*</i><b>微博uid</b>&nbsp;&nbsp;&nbsp;&nbsp;
		        <span id="point">
		             <input type="input" name="uid" id="uid" value="${bean.uid }" maxlength="32"/>
		        </span>
		     </p>
	     <div class="blank10"></div>
	     <div class="blank1_b w98"></div>
	     <div class="blank10"></div>
	     <ul class="button_1">
           <li><span onclick="addTimerTask()">确定</span></li>
		   <li><span onclick="selfWindowClose()">取消</span></li>
		 </ul>
		<p></p>
      </s:form>
    </div>
   </div>
  </div>
</body>
</html>
