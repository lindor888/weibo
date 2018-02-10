<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="zh-CN" lang="zh-CN">
  <head>
    <title>图片工具</title>
    <link type="text/css" rel="stylesheet" href="/weibo-studio/style/easyui/easyui.css" />
  	<link type="text/css" rel="stylesheet" href="/weibo-studio/style/easyui/icon.css" />
  	<link type="text/css" rel="stylesheet"  href="/weibo-studio/style/css/uploadify.css"/>
  	<link type="text/css" rel="stylesheet" href="/weibo-studio/style/css/global.css" />
  	<link type="text/css" rel="stylesheet" href="/weibo-studio/style/css/style.css" />
  	
  	<script src="/weibo-studio/style/js/jquery-1.4.2.min.js" type="text/javascript"></script>
  	<script src="/weibo-studio/style/js/jquery.easyui.min.js" type="text/javascript"></script>	
  	<script src="/weibo-studio/style/js/swfobject.js" type="text/javascript" ></script>
 	<script src="/weibo-studio/style/js/jquery.uploadify.v2.1.4.min.js" type="text/javascript" ></script> 	
 	<script src="/weibo-studio/style/js/jquery-ui.min.js" type="text/javascript"></script>
 	<script src="/weibo-studio/style/js/jquery-ui.min.js" type="text/javascript"></script>
 	<script src="/weibo-studio/style/js/photoAlbumEdit.js" type="text/javascript"></script>

	
	<style>
		img {margin: 0; padding: 0; float: left; clear: both;}
	  	.panel-body {border-style:none;}
	  	.widget-placeholder {border:1px dashed #FF99CC;}
	  	.c5 div{width:9%;}
	  	.c5 img:hover{border:1px solid red;}
	  	.header{height:80px;width:80px;}
	  	.photoBox {width:auto;margin:0 auto}
		.photolist{list-style:none;width:auto;}
		.photolist span{float: left; margin:0px;padding: 2px 2px 2px 2px;position:relative;}
		.photolist span:hover{border-color:red;border-width:2px;border-style: solid;padding: 0px;display:block}
		.flg {background: url("/weibo-studio/style/images/photo_flg.gif") no-repeat scroll;transparent;height:10px;width:10px;}
		.photolist .flg{right:2px;bottom:2px; overflow: hidden;display:none;position:absolute;}
		.del {background: url("/weibo-studio/style/images/photo_del.gif") no-repeat scroll;transparent;height:7px;width:7px;cursor:pointer;}		
		.photolist .del{top:5px;right:4px;overflow:hidden;display:block;position:absolute;}
		.photolist .sel{border-color:#33FF66;border-width:2px;border-style: solid;padding: 0px;display:block}
		.cur .flg{display:block}
		.alt .flg{background: url("/weibo-studio/style/images/photo_alt_flg.png") no-repeat scroll;transparent;height:38px;width:33px;display:block}
		.loding .flg{background: url("/weibo-studio/style/easyui/images/tree_loading.gif") no-repeat scroll;transparent;height:20px;width:20px;display:block}
		#uploadfileUploader{visibility: visible; position:relative;top:4px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			initUploadLogo("uploadfile");
			$("#ipt").focus();
			
		});
		
		function addLogo(){
			var photoIdList=new Array();
			var photoAltList=new Array();
			var logo="";	
			var logoObject=$("#_logoPhoto").children('span');
			logo=logoObject.attr("logourl");
			if(logo.length>0){
				parent.window.setLogo(logo);
				parent.window.CloseDialog();
			}else{
				$.messager.defaults.ok="确定";
				$.messager.defaults.cancel="取消";
				$.messager.alert('消息','请上传微博图');
			}
		}
	</script>
</head>
<body>
<div >
	<div><s:hidden name="tabUserInf.userId" id="userId"> </s:hidden>
		<div class="cut6" style="width:710px">
			<div>
				<div class="in1">
				<h2><span></span>微博图片</h2>
				<div class="blank10"></div>
				<div class="block7">
					<div class="block8" style="border-top: 1px solid #E6E6E6;">
						<div class="blank10"></div>
						<ul>
							<li>
								图片上传&nbsp;
								<input class="ipt"  id="ipt" style="width:140px;display:none" />&nbsp;<input id="uploadfile" name="upload" type="file" />																			
								<span style="padding-left:0px">支持.jpg .png .gif格式，上传文件小于300k</span>			
							</li>
						</ul>
						<div class="blank1_b w98"></div>
					</div>
				</div>				
				<div class="block9">
						<div class="blank10"></div>
						<div class="block19" style="border:0px">
							<div class="wrapper">
							     <div id="html-carousel" class="carousel-component" style="margin:0 auto">
							          <div class="photoBox" style="opacity: 0.999999;width:100px;">
							          	<center>
							          	  <span class="photolist" id="_logoPhoto">
							          	  		<span isadd="no" logourl="" ><img class=""  height="150" width="150" alt="" src=""/><i class="flg"></i></span>
							          	  </span>
										</center>
							         </div>
							    </div>
							</div>
						</div>
						<div class="blank10"></div>
				</div>
				<div class="blank10"></div>			
				<center>
					<a class="cms_btn" title=""><span class="cms_btn_t" onclick="addLogo()" >确定</span></a>
				</center>
				<div class="blank10"></div>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
