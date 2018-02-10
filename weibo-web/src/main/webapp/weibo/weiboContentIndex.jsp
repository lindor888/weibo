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
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	};
	
	var commentsCount = 0;
	
	$(function() {
		var nowStr = new Date().Format('yyyy-MM-dd');
		$("#endTimeStr").val(nowStr);
		var beginTimeStr = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 2).Format('yyyy-MM-dd');
		$("#beginTimeStr").val(beginTimeStr);
		$form = $('#queryForm');
		loadData();
		
		/* window.setInterval(function(){
			loadData();
		},30000); */
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
			async: false,
			success: function(msg){
				var data = msg.rows;
				contentPageSum = msg.pageSum;
				fillWeibo(data);
			}
		});
	}
	
	function check(){
		$form[0]["example.page"].value = 1;
		var beginTimeStr = $("#beginTimeStr").val();
		var endTimeStr = $("#endTimeStr").val();
		
		if(beginTimeStr==''||endTimeStr==''){
			alert('请选择微博发布时间段');
			return;
		}
		if(beginTimeStr >= endTimeStr){
			alert('开始时间应小于结束时间');
			return;
		}
		var beginTime = new Date(beginTimeStr.replace(/-/g,"/"));
		var endTime = new Date(endTimeStr.replace(/-/g,"/"));
		if(endTime.getTime()-beginTime.getTime()>1000*60*60*24*10){
			alert('时间段不超过10天');
			return;
		}
		
		var url = $form.attr('action');
		var param = $form.serialize();
		$.ajax({
			type: "POST",
			url: url,
			data: param,
			async:false,
			success: function(msg){
				var data = msg.rows;
				contentPageSum = msg.pageSum;
				fillWeibo(data);
			}
		});
	}
	
	//加载已发送微博信息
	/* function onWeiboChange() {
		$form[0]["example.page"].value = 1;
		var url = $form.attr('action');
		var param = $form.serialize();
		$.ajax({
			type: "POST",
			url: url,
			data: param,
			async:false,
			success: function(msg){
				var data = msg.rows;
				contentPageSum = msg.pageSum;
				fillWeibo(data);
			}
		});
	} */
	
	//组装微博信息table
	function fillWeibo(data) {
		var weiboIdArray = new Array();
		var html = new Array();
		if(data != undefined && data != null && data != '') {
			for(var i=0;i<data.length;i++) {
				var weiboId = data[i].weiboId;
				weiboIdArray.push(weiboId);
				commentsCount = data[i].commentsCount;
				var createTime = data[i].weiboCreateTime.replace('T',' ');
				html.push("<div id=\"home_wb_"+data[i].weiboId+"\" class=\"wb\">");
					html.push("<div class=\"wb_r\">");
						html.push("<div class=\"wb_r_t\">");
							html.push("<span>");
							html.push("<a title=\"\" class=\"font14b\" onclick=\"\" href=\"javascript:;\">"+data[i].userCcreenName+"</a>");
							html.push("</span>:");
							html.push("<div class=\"wb_content\">"+data[i].content+"</div>");
						html.push("</div>");
						html.push("<div class=\"date\">");
							html.push("<span class=\"l\">");
								html.push("<a title=\""+createTime+"\" rel=\"1400652317\" href=\"\" target=\"_blank\" class=\"time\">"+createTime+"</a>");
								if(data[i].flag==1){
									html.push("<a style='color:red'>(审核已通过)</a>");
								}
							html.push("</span>");
							html.push("<span class=\"r la\">");
								html.push("<a style='cursor:pointer' class='wTianjia' id='"+data[i].weiboId+"'>添加评论</a>");
								html.push("<span class=\"wb_line_l\">|</span>");
								html.push("<a style='cursor:pointer' class='wTongguo' id='"+data[i].weiboId+"'>通过</a>");
								html.push("<span class=\"wb_line_l\">|</span>");
								html.push("<a style='cursor:pointer' class='wJujue' id='"+data[i].weiboId+"'>拒绝</a>");
								//html.push("<a class=\"plLink\" id=\"home_plLink_3712667267535835\" onclick=\"get_comment('"+data[i].weiboId+"',"+data[i].commentsCount+")\" href=\"javascript:;\">评论("+data[i].commentsCount+")</a>");
							html.push("</span>");
						html.push("</div>");
					html.push("</div>");
					html.push("<div class=\"wb_l\">");
						html.push("<a onclick=\"\" href=\"javascript:;\">");
						html.push("<img title=\"关注："+data[i].friendsCount+" 粉丝："+data[i].followersCount+" 微博："+data[i].statusesCount+" 简介："+data[i].description+"\" onerror=\"this.src='';\" src=\""+data[i].profileImageUrl+"\">");
						html.push("</a>");
					html.push("</div>");
					html.push("<div id=\"user_t_plWrap_"+data[i].weiboId+"\" class=\"plWrap\" style=\"display: block\">");
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
			html.push("该时间段暂无抓取的微博信息");
			html.push("</div>");
		}
		$('#weibocontent').html(html.join(""));
		
		$(".wTianjia").unbind('click').click(function(){
			var userId = $("#userId").val();
			var thisWeiboId = $(this).attr('id');
			var url = "/weibo-studio/weibo/commentAdd.jsp?weiboId="+thisWeiboId+"&userId="+userId;
			$.fn.colorbox({
				iframe : true,
				innerWidth : 550,//子页面宽度
				innerHeight : 400, //子页面高度
				href : url,
				overlayClose : false
			});
		});
		
		$(".wTongguo").unbind('click').click(function(){
			var userId = $("#userId").val();
			var thisWeiboId = $(this).attr('id');
			$(this).parent().parent().find('a').eq(0).after('<a style="color:red">(审核已通过)</a>');
			$.ajax({
				url : '/weibo-studio/shenhewWeibo',
				type : 'post',
				async : false,
				data : {'example.userId':userId,'example.weiboId':thisWeiboId , 'example.flag':1},
				success : function(data){
					if(data.msg == 'success'){
						
					}
				}
			});
		});
		
		$(".wJujue").unbind('click').click(function(){
			var userId = $("#userId").val();
			var thisWeiboId = $(this).attr('id');
			$(this).parent().parent().find('a').eq(1).remove();
			$.ajax({
				url : '/weibo-studio/shenhewWeibo',
				type : 'post',
				async : false,
				data : {'example.userId':userId,'example.weiboId':thisWeiboId , 'example.flag':0},
				success : function(data){
					if(data.msg == 'success'){
						
					}
				}
			});
		});
		for(var i=0;i<weiboIdArray.length;i++){
			get_comment(weiboIdArray[i],commentsCount);
		}
		
	}
	
	//获取评论信息
	var commentCurrentPage = 1;
	var pageSumAll = 1;
	var commentRows = 10;
	//上一页、下一页时调用该方法
	function do_comment(weiboId,page,commentCount,pageSum) {
		commentCurrentPage = page;
		pageSumAll = pageSum;
		if(commentCurrentPage == 0) {
			alert('目前已是第一页！');
			return;
		}
		if(commentCurrentPage > pageSumAll) {
			alert('目前已是最后一页！');
			return;
		}
		var url = "/weibo-studio/selectCommentWeibo.action";
		$.ajax({
			type: "POST",
			url: url,
			async:false,
			data: {'example.weiboId':weiboId,'example.rows':commentRows,'example.page':page},
			success: function(msg){
				var data = msg.rows;
				fillComment(data,weiboId,commentCount,msg.pageSum);
			}
		});
	}
	
	//点击页面评论时调用该方法
	function get_comment(weiboId, commentCount) {
		var html = $('#user_t_plWrap_'+weiboId).html();
		/* if(commentCount == 0 || html != '') {
			$('#user_t_plWrap_'+weiboId).html('');
			$('#user_t_plWrap_'+weiboId).hide();
			return;
		} */
		var url = "/weibo-studio/selectCommentWeibo.action";
		$.ajax({
			type: "POST",
			url: url,
			async: false,
			data: {'example.weiboId':weiboId,'example.rows':commentRows,'example.page':1},
			success: function(msg){
				var data = msg.rows;
				fillComment(data,weiboId,commentCount,msg.pageSum);
			}
		});
	}
	
	//组装评论table
	function fillComment(data,weiboId,commentCount,pageSum) {
		var html = new Array();
		$('#user_t_plWrap_'+weiboId).show();
		if(data != undefined && data != null) {
			for(var i=0;i<data.length;i++) {
				var createTime = data[i].weiboCreateTime.replace('T',' ');
				html.push("<div class=\"btn\">");
					html.push("<div id=\"comments_list\">");
						html.push("<div id=\"user_t_pl_3712689471758244\" class=\"pl\">");
							html.push("<div style=\"padding-right:90px;\" class=\"pl_r\">");
							html.push("<span><a title=\"关注："+data[i].friendsCount+" 粉丝："+data[i].followersCount+
									"微博："+data[i].statusesCount+" 简介："+data[i].description+"\" class=\"blue\" onclick=\"\" href=\"javascript:;\">"
									+data[i].userCcreenName+"</a></span> "+data[i].content+"(" + createTime +")");
							if(data[i].flag==1){
								html.push("<a style='color:red'>(审核已通过)</a>");
							}
							html.push("</div>");
							html.push("<div class=\"pl_l\">");
							html.push("<a onclick=\"\" href=\"javascript:;\">");
							html.push("<img title=\"关注："+data[i].friendsCount+" 粉丝："+data[i].followersCount+
									"微博："+data[i].statusesCount+" 简介："+data[i].description+"\" src=\""+data[i].profileImageUrl+"\"></a>");
							html.push("</div>");
							html.push("<div style='float:right'>");
								html.push("<a style='cursor:pointer' class='cXiugai' id='"+data[i].weiboId+"'>修改</a> | <a style='cursor:pointer' class='cTongguo' id='"+data[i].weiboId+"'>通过</a> | <a style='cursor:pointer' class='cJujue' id='"+data[i].weiboId+"'>拒绝</a>");
							html.push("</div>");
						html.push("</div>");
					html.push("</div>");
				html.push("</div>");
			}
			html.push("<div style=\"display: block;\" class=\"qPager\" id=\"comments_list_page\">");
				html.push("<div class=\"stat\"></div>");
				html.push("<ul class=\"turn\">");
					html.push("<li>");
					html.push("<a onclick=\"do_comment('"+weiboId+"',"+(commentCurrentPage-1)+","+commentCount+","+pageSum+")\" href=\"javascript:;\">上一页</a>");
					html.push("</li>");
					html.push("<li>");
					html.push("<a onclick=\"do_comment('"+weiboId+"',"+(commentCurrentPage+1)+","+commentCount+","+pageSum+")\" href=\"javascript:;\">下一页</a>");
					html.push("</li>");
				html.push("</ul>");
			html.push("</div>");	
		}
		$('#user_t_plWrap_'+weiboId).html(html.join(""));
		
		$(".cTongguo").unbind('click').click(function(){
			var userId = $("#userId").val();
			var thisWeiboId = $(this).attr('id');
			$(this).parent().parent().find('.pl_r').append('<a style="color:red">(审核已通过)</a>');
			$.ajax({
				url : '/weibo-studio/shenhecWeibo',
				type : 'post',
				async : false,
				data : {'example.userId':userId,'example.weiboId':thisWeiboId , 'example.flag':1},
				success : function(data){
					if(data.msg == 'success'){
						
					}
				}
			});
		});
		
		$(".cJujue").unbind('click').click(function(){
			var userId = $("#userId").val();
			var thisWeiboId = $(this).attr('id');
			$(this).parent().parent().find('.pl_r').find('a').eq(1).remove();
			$.ajax({
				url : '/weibo-studio/shenhecWeibo',
				type : 'post',
				async : false,
				data : {'example.userId':userId,'example.weiboId':thisWeiboId , 'example.flag':0},
				success : function(data){
					if(data.msg == 'success'){
						
					}
				}
			});
		});
		
		$(".cXiugai").unbind('click').click(function(){
			var thisWeiboId = $(this).attr('id');
			var url = "/weibo-studio/weibo/commentUpdate.jsp?commentId="+thisWeiboId;
			$.fn.colorbox({
				iframe : true,
				innerWidth : 550,//子页面宽度
				innerHeight : 400, //子页面高度
				href : url,
				overlayClose : false
			});
			
			
		});
	}
</script>
</head>

<body>
	<div class="ipduanguanli" style="overflow:auto;">
		<form id="queryForm" action="/weibo-studio/selectContentWeibo.action" method="post">
			<input type="hidden" name="example.page" value="1" />
			<input type="hidden" id="rows" name="example.rows" value="5" />
			<dl>
				<dd>
					<span style="width:100px;">系统微博：</span> <select name="example.userId" id="userId" >
						<s:if test="weibos != null && weibos.size() > 0">
							<s:iterator value="weibos" var="weibo">
								<option value="${weibo.weiboId }">${weibo.weiboUserName}</option>
							</s:iterator>
						</s:if>
						<s:else>
							<option value="">-暂无微博-</option>
						</s:else>
					</select>
				</dd>
				<dd>
					<span style="width:100px;">微博发布时间：</span>
					<input type="text" id="beginTimeStr" name="example.beginTimeStr" class="text1 Wdate startDate" style="width:150px;" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					至
					<input type="text" id="endTimeStr" name="example.endTimeStr" class="text1 Wdate startDate" style="width:150px;" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
				</dd>
				<dd>
					<input type="button" onclick="check()" value="查询" class="submit">
				</dd>
			</dl>
		</form>
		<%-- <div class="p_content">
        	<div id="status_div"><textarea onkeyup="checkText('status', 140)" rows="5" cols="45" id="status" name="bean.content"></textarea></div>
        	<div class="button">
        		<div class="img_box">
				<div class="imgupload" id="img_upload"><span style="float:left;"><a onmouseover="$('#img_upload_list').show();" href="javascript:;" class="imgicon">图片</a></span><em class="dot2"></em><ul class="up_list" id="img_upload_list" onmouseout="$('#img_upload_list').hide();" onmouseover="$('#img_upload_list').show();" style="display: none;">
	                    <li><a href="javascript:;" class="imgicon">图片<input type="file" onchange="ajaxImgUpload();" title="支持jpg、jpeg、gif、png格式，文件小于3M" class="imgfile" name="image" id="image"></a></li>
	                </ul></div>
				<div style="display:none;" class="imgloading" id="img_loading"><span>正在上传...</span></div>
				<div style="display:none;" class="imgname" id="img_name"><a onmouseout="$('#img_pop').hide();" onmouseover="$('#img_pop').show();" href="javascript:;" class="imgicon" id="img_select">{图片名称}</a> <a onclick="ajaxImgDel()" href="javascript:;">[取消]</a><div id="img_pop"></div></div>
				<input type="hidden" name="img_url" id="img_url"/>
			  </div>
			  <!-- <div id="open_changweibo" class="changweibo_box"><a href="javascript:;">长微博</a></div> -->
			  <div class="ts_box1"><input type="checkbox" onclick="init_timesend()" value="1" id="statusid" name="bean.status"></div>
			  <div class="ts_box2">
			  	<label for="set_time">定时微博</label>
			  	<span id="send_ts" style="display: none">
			  		<input type="text" name="bean.beginTime" id="beginTime" class="text1 Wdate startDate" style="width:150px;" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-#{%M+12}-%d',minDate:'%y-%M-#{%d} %H:#{%m+5}:%s'})" />
			  	</span>
			  </div>
	        </div>
        	<div class="button2" style="height: 35px;">
          		<div class="send_s" id="send_s"><a onclick="send_status();return false;" href="javascript:;"></a></div>
          		<div id="status_warn">还能输入<em>140</em>字</div>
        	</div>
      	</div> --%>
      	<div id="divWrap"></div>
      
		<div id="weibocontent">
		</div>
	</div>
</body>
</html>