<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>发送微博</TITLE>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/colorBox/colorbox.css" />
<link href="/weibo-studio/style/webcss/web_register.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="/weibo-studio/style/css/main.css" />
<link href="/weibo-studio/style/webcss/base.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/weibo-studio/style/js/jquery-1.4.2.min.js"></script>
<script language="JavaScript" src="/weibo-studio/style/js/jquery.colorbox-min.js"></script>
<script language="JavaScript" src="/weibo-studio/style/js/checkValue.js"></script>
<script language="javascript" src="/weibo-studio/style/My97DatePicker/WdatePicker.js"></script>
<script language="javascript" src="/weibo-studio/style/js/weibo.js"></script>
<script language="javascript" src="/weibo-studio/style/js/ajaxfileupload.js"></script>
<script language="JavaScript">
	function isBlank(content) {
		return content == null || content == "";
	}
	
	$(function(){
		$("#picUpload").click(function(){
			var url = "/weibo-studio/weibo/weiboPicUpload.jsp";
			$.fn.colorbox({
				iframe : true,
				innerWidth : 725,//子页面宽度
				innerHeight : 360, //子页面高度
				href : url,
				overlayClose : false
			});
		});	
		
		var imgUrl = '${example.logoPhoto }';
		if(imgUrl == undefined || imgUrl == '' || imgUrl == null) {
			$('#imgUrl').hide();
		} else {
			$('imgUrl').show();
		}
		
	});
	
	function check() {
		$("#weiboForm").submit();
	}
	
	//发送微博
	function send_weibo() {
		if(SEND) {
			show_tip("正在发送微博，请不要重复提交！");
			return;
		}
		var params = $('#weiboForm').serialize();
		var ajax_url = '/weibo-studio/sendWeibo.action';
		if(checkText("status", 140)) {
			SEND = true;
			$.post(ajax_url, params,
				function(data){
					SEND = false;
					if(data.msg == 'ok') {
						alert("发布成功");  
					}else {
						alert("发布失败");
					}
					checkText("status", 200);
				}
			);
			
		} else {
			alert("请输入内容为空或输入内容已经超过限制");
			return;
		}
	}
	
	function CloseDialog(){
		$.fn.colorbox.close();
	}
	
	function imgUpload() {
		$("#img_loading").show();
		$("#img_upload").hide();

		$.ajaxFileUpload({
				url: "/weibo-studio/uploadFileUploadAction",
				secureuri: false,
				fileElementId: 'image',
				dataType: 'jsonp',
				success: function (data, status) {
					$("#img_loading").hide();
					
					var v = $.trim( $('#status').val() );
					if(v == "") {
						$("#status").val('分享图片');
						checkText("status", 140);
					}
					$("#img_url").val(data.url);
					$("#img_select").html(data.name);
					$("#img_name").show();
					$("#imgUrl").html('<img height="80" width="80" src="'+data.url+'"/>');
					$("#imgUrl").show();
				},
				error: function (data, status, e) {
					console.log(e);
					$("#img_loading").hide();
					$("#img_upload").show();
					$("#imgUrl").hide();
				}
			}
		);
		return false;
	}
	
	function imgDel() {
		$("#img_upload").show();
		$("#img_name, #img_upload_list").hide();
		$("#img_url").val("");
		$("#imgUrl").html('');
		$("#imgUrl").hide();
		var v = $.trim( $('#status').val() );
		if(v == "分享图片") $("#status").val('');
		checkText("status", 140);
	}
</script>
</HEAD>
<BODY id="tjdyyh">
<div class="tjdyyh">
    <dl>
        <form id="weiboForm" action="/weibo-studio/sendWeibo.action" method="post" enctype="multipart/form-data">
            <div class="p_content">
	        	<div id="status_div">
	        		<textarea name="bean.content" onkeyup="checkText('status', 140)" rows="5" cols="45" id="status">${example.title }${example.url }</textarea>
	        		<%-- <input type="hidden" name="bean.imageUrl" value="${example.logoPhoto }"/> --%>
					<input type="hidden" name="bean.title" value="${example.title }"/>
					<input type="hidden" name="bean.url" value="${example.url }"/>
					<input type="hidden" name="bean.contentId" value="${example.contentId }"/>
					<input type="hidden" name="bean.weiboId" value="${example.weiboId }"/>
	        	</div>
	        	<!-- <div align="center">
	        		<img height="80" width="80" alt="" src="http://192.168.168.105:8080/image2014/07/01/Weib1404181137425003.jpg">
	        	</div> -->
	        	<div class="button">
        			<div class="img_box">
						<div class="imgupload" id="img_upload"><span style="float:left;"><a onmouseover="$('#img_upload_list').show();" href="javascript:;" class="imgicon">图片</a></span><em class="dot2"></em><ul class="up_list" id="img_upload_list" onmouseout="$('#img_upload_list').hide();" onmouseover="$('#img_upload_list').show();" style="display: none;">
			                    <li><a href="javascript:;" class="imgicon">图片<input type="file" onchange="imgUpload();" title="支持jpg、jpeg、gif、png格式，文件小于3M" class="imgfile" name="image" id="image"></a></li>
			                </ul></div>
						<div style="display:none;" class="imgloading" id="img_loading"><span>正在上传...</span></div>
						<div style="display:none;" class="imgname" id="img_name"><a onmouseout="$('#img_pop').hide();" onmouseover="$('#img_pop').show();" href="javascript:;" class="imgicon" id="img_select">{图片名称}</a> <a onclick="imgDel()" href="javascript:;">[取消]</a>
						</div>
						<div id="imgUrl" align="center">
							<img height="80" width="80" src="${example.logoPhoto }"/>
						</div>
						<input type="hidden" name="bean.imageUrl" id="img_url" value="${example.logoPhoto }"/>
				  	</div>
				  <div class="ts_box1"><input type="checkbox" onclick="init_timesend()" value="1" id="statusid" name="bean.status"></div>
				  <div class="ts_box2">
				  	<label for="set_time">定时微博</label>
				  	<span id="send_ts" style="display: none">
				  		<input type="text" name="bean.beginTime" class="text1 Wdate startDate" style="width:150px;" onfocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'%y-#{%M+12}-%d',minDate:'%y-%M-#{%d} %H:#{%m+5}:%s'})" />
				  	</span>
				  </div>
		        </div>
	        	<div class="button2" style="height: 35px;">
	          		<div class="send_s" id="send_s"><a onclick="send_weibo();return false;" href="javascript:;"></a></div>
	          		<div id="status_warn">还能输入<em>140</em>字</div>
	        	</div>
	      	</div>
        </form>
    </dl>
</div>
<script type="text/javascript">
	function setLogo(photoPath){
		$('#imageUrl').val(photoPath);
	}
</script>
</BODY>
</HTML>
