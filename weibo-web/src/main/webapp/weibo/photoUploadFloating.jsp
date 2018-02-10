<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"  xml:lang="zh-CN" lang="zh-CN">
  <head>
    <title>图片工具</title>
    <link type="text/css" rel="stylesheet" href="/weibo-studio/style/easyui/easyui.css" />
  	<link type="text/css" rel="stylesheet" href="/weibo-studio/style/easyui/icon.css" />
  	<link type="text/css" rel="stylesheet" href="/weibo-studio/style/css/global.css" />
  	<link type="text/css" rel="stylesheet" href="/weibo-studio/style/css/style.css" />
  	<link type="text/css" rel="stylesheet"  href="/weibo-studio/style/css/uploadify.css"/>
  	
  	<script src="/weibo-studio/style/js/jquery-1.4.2.min.js" type="text/javascript"></script>
  	<script src="/weibo-studio/style/js/jquery.easyui.min.js" type="text/javascript"></script>
  	<script src="/weibo-studio/style/js/swfobject.js" type="text/javascript" ></script>
  	<script src="/weibo-studio/style/js/jquery.uploadify.v2.1.4.min.js" type="text/javascript" ></script>
  	<script src="/weibo-studio/style/js/calendar/WdatePicker.js" type="text/javascript"></script>
  	<script src="/weibo-studio/icms/standard/content/js/photoAlbumEdit.js" type="text/javascript"></script>
  	<script src="/weibo-studio/icms/standard/content/js/queryDataRecord.js" type="text/javascript"></script>
  	<script src="/weibo-studio/style/js/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
</head>
	<style>
		img {margin: 0; padding: 0; float: left; clear: both;}
	  	.panel-body {border-style:none;}
	  	.widget-placeholder {border:1px dashed #FF99CC;}
	  	.c5 div{width:9%;}
	  	.c5 img:hover{border:1px solid red;}
	  	.header{height:80px;width:80px;}
	  	.photoBox {width:auto;margin:0 auto}
		.photolist{list-style:none;width:auto;}
		.photolist li{float: left; margin:0px;padding: 2px 2px 2px 2px;position:relative;}
		.photolist li:hover{border-color:red;border-width:2px;border-style: solid;padding: 0px;display:block}
		.flg {background: url("/weibo-studio/style/images/photo_flg.gif") no-repeat scroll;transparent;height:10px;width:10px;}
		.photolist .flg{right:2px;bottom:2px; overflow: hidden;display:none;position:absolute;}
		.del {background: url("/weibo-studio/style/images/photo_del.gif") no-repeat scroll;transparent;height:7px;width:7px;cursor:pointer;}		
		.photolist .del{top:5px;right:4px;overflow:hidden;display:block;position:absolute;}
		.photolist .sel{border-color:#33FF66;border-width:2px;border-style: solid;padding: 0px;display:block}
		.cur .flg{display:block}
		.alt .flg{background: url("/weibo-studio/style/images/photo_alt_flg.png") no-repeat scroll;transparent;height:38px;width:33px;display:block}
		.loding .flg{background: url("/weibo-studio/style/easyui/images/tree_loading.gif") no-repeat scroll;transparent;height:20px;width:20px;display:block}
	</style>
<script>
	$(document).ready(function(){
		initUploadZip("uploadzip",$('#userId').val());
		initUploadPhoto("uploadfile",$('#userId').val());
		initQuery("PHOTOALBUM","","");
	});
	var total = 10;
	function QueryData(datagrId){
		var param={};
		jQuery.ajaxSettings.traditional = true;
		var queryKeytitle=$('#queryKeytitle').val();
		var beginTime=$('#beginTime').val();
		var endTime=$('#endTime').val();
		if(beginTime.length<=0||endTime.length<=0){
			$.messager.alert('消息','请选择开始时间和结束时间条件！');
			return false;
		}
		var start = new Date(beginTime.replace(/-/g,"/")); 
		var end = new Date(endTime.replace(/-/g,"/")); 
		var diff = parseInt((end - start) / (1000*60*60*24));
		if(diff>30){
			$.messager.alert('消息','开始时间，结束时间不能相差30天以上!!!');
			return false;
		}
		$.extend(param,{"queryDataBean.startDate":beginTime+""});
		$.extend(param,{"queryDataBean.endDate":endTime+""});
		$.extend(param,{"queryDataBean.queryKeyWord":queryKeytitle});
		getClassListChecked();
		$.extend(param,{"queryDataBean.pageClassList":pageClassListcheckedList});
		var QueryAction="/weibo-studio/content/queryphotoPhoto";
		$('#photoSearch').datagrid({
			width:700,
			url:QueryAction,
			striped: true,
			pageNumber:1,
			pageSize:10,
			pageList:[10],
			queryParams:param,
			idField:"photoId",
			frozenColumns:[[{field:'ck',checkbox:true}]],
			columns:[[
				{field:"photoId",title:'photoId',width:130},
				{field:'photoPath',title:'缩略图',width:90,align:'center',sortable:true,
					formatter:function(value,rowData,rowIndex){
						if(value!=null&&value.length>0){
							value=value.replace(".","_87x64.");
							return "<img src='"+value+"' height='30' width='30' />";
						}else{
							return '';
						}
					}
				},
				{field:"photoTag",title:'关键字',width:250},				
				{field:"photoCurrentDate",title:'时间',width:120,formatter:function(value,rowData,rowIndex){							
					return value.replace("T"," ");}
				}
			]],
			onLoadSuccess:function(data){
				if($('.datagrid-row-selected').length>=20){
					$('#photoSearch').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", true);	
				}else{
					$('#photoSearch').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false);	
				}	
			},
			onSelect:function(rowIndex, rowData){				
				if($("#photolist li[isadd='yes']").length>=20){
					$.messager.alert('消息','超出数量！！');
					$('#photoSearch').datagrid('unselectRow',rowIndex);		
					return false;
				}else{
					addToShowList(rowData);
				}
				var selected = $('.datagrid-row-selected').length/2;
				if(total==selected)
					$('#photoSearch').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", true); 
			},
			onUnselect:function(rowIndex, rowData){
				dataGridRemoveSelectImg(rowData.photoId);
				var selected = $('.datagrid-row-selected').length/2;
			 	if(total!=selected)
				$('#photoSearch').parent().find("div .datagrid-header-check").children("input[type='checkbox']").eq(0).attr("checked", false); 
			},
			onSelectAll:function(rows){
				$(rows).each(function(i){
					addToShowList(rows[i]);
				});
			},
			onUnselectAll:function(rows){
				$(rows).each(function(i){
					dataGridRemoveSelectImg(rows[i].photoId);
				});
			},
			rownumbers:true,
			pagination:true,
			remoteSort:false
		});
	}	
	
	function changeNav(cur){
		var items=$('#tagNav > li');
		$.each(items, function(j,item){
			if($(cur).attr("id")==$(item).attr("id")){
				$("#subnavb_"+j).show();
				$(item).addClass("onsec");
			}else{
				$("#subnavb_"+j).hide();
				$(item).removeClass("onsec");
				$(item).addClass("unsec");
			}
		});
	}
	
	function rerefshTree(){
		$('#queryClasstree').combotree({url:'/weibo-studio/content/treeClassTreeAction'});
	}
	
</script>
<body><s:hidden name="tabUserInf.userId" id="userId"> </s:hidden>
		<div class="cut6">
				<div class="in1">
				<h2><span></span>影集图片添加</h2>
				<div class="blank10"></div>
				<div class="block7">
					<ul id="tagNav">
					<li class="onsec" style="cursor:hand" id="bli1" onclick="changeNav(this)">图片上传</li>
					<li class="unsec" style="cursor:hand" id="bli2" onclick="changeNav(this);rerefshTree()">图片搜索</li>
					</ul>
					<div class="clear"></div>
				</div>
				<div id="subnavb_0" style="display:">
					<div class="block8">
							<div class="blank10"></div>
							<ul>
							<li>
								单张上传&nbsp;
								<input class="ipt" style="width:140px" type="hidden" />&nbsp;<input id="uploadfile" name="upload" type="file"  />
								<span style="padding-left:0px">支持.jpg .png .gif格式，上传文件小于2M</span>						
							</li>
							<li>
								批量上传&nbsp;
								<input class="ipt" style="width:140px" type="hidden" />&nbsp;<input id="uploadzip" name="upload" type="file"  />				
								<span style="padding-left:0px">支持.zip .rar格式，上传文件小于30M&nbsp;&nbsp;<font color=red>注意压缩包中不能包含文件夹！</font></span>		
							</li>
							<li>
								<div style="width:260px;height:25px;">
								<div style="display:block;float:left" >网络图片&nbsp;<input type="text"  style="button" name="webPhoto" id="webPhoto" class="ipt" style="width:140px" /></div>
								<span onclick="appendWebPic();return false;" style="background:url('/weibo-studio/style/images/upload.jpg') no-repeat scroll;transparent;height:21px;width:43px;display:block;float:right;cursor:pointer;" >&nbsp;</span>	
								<img src="" id="webphoto_test" style="display:none" />
								</div>
							</li>
							<li>
								<div style="width:280px;height:25px;clear:both">
									<input id="watermark" name="watermark" type="checkbox" value="/weibo-studio/style/images/cctv_logo.gif" />&nbsp;添加水印
								</div>
							</li>
							</ul>
							<div class="blank10" id="errorInfo"></div>
							<div class="blank1_b w98"></div>
					</div>
				</div>
				<div id="subnavb_1" style="display:none">
					<div class="block8">
					<ul>
						<li>
						页面分类：<select class="easyui-combotree" name="queryClasstree" id="queryClasstree" multiple="true" cascadeCheck="false" style="width:300px;"></select><br/>
						<input type="hidden" name="queryDataBean.pageClassList" value="" id="queryDataBean.pageClassList"/>	
						时间范围：<input class="Wdate" id="beginTime" name="queryBean.beginTime" type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\',{d:-30});}',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd',readOnly:true});" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>" />
						至&nbsp;<input class="Wdate" id="endTime"   name="queryBean.endTime"   type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',maxDate:'#F{$dp.$D(\'beginTime\',{d:30});}',dateFmt:'yyyy-MM-dd',readOnly:true});" value="<%=new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>" />
						关&nbsp;键&nbsp;字：<input id="queryKeytitle" name="queryBean.title" type="text" value="" />
						<a class="cms_btn" ><span class="cms_btn_t" style="color:#333333" id="QueryBtn" onclick="QueryData('photoSearch')" >查询</span></a> 						
						</li>
					</ul>
						<table id='photoSearch'></table>
					</div>
					</div>
				</div>			
				<div class="blank10"></div>		
				<div class="block19" style="border:0px">
					<ul><li>待插入的图片：(一次最多添加20张,可添加多次;)&nbsp;&nbsp;<span id="uploading"></span></li></ul>
					<div class="wrapper">
					     <div id="html-carousel" class="carousel-component" style="width:680px">
					          <div class="photoBox" style="opacity: 0.999999;">
					               <ul id="photolist" class="widget-place photolist" style="left: 0px; top: 0px;">
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
									  <li class='widget' isadd="no"><img class=""  height="60" width="60" alt="图片标题关键字" src="/weibo-studio/style/images/pic4.gif"/><i class="flg"></i></li>
					              </ul>
					         </div>
					    </div>
					</div>
				</div>
				
				
				<div class="blank10"></div>
				<center>
					<a class="cms_btn" ><span class="cms_btn_t" onclick="insertPhotoAlbum()" >插入图集</span></a>
				</center>
				<div class="blank10"></div>
				</div>
			</div>
		<div class="blank10"></div>
<script>
$('#webphoto_test').load(function(){//sucssue
		var ajaxurl="/weibo-studio/content/insertwebPhoto";
		var resultJson ="";
		var webPhotoValue="";
		var param={};
		$('#webInfo').html("请等待.......");
		webPhotoValue=$('#webphoto_test').attr('src');
		if(webPhotoValue.length<=0){return false;}
		//if(photoSizeCheck(webPhotoValue)){
			$.extend(param,{"webPhoto":webPhotoValue});
			if($("#watermark").attr("checked")==true){
				var waterMarkLogoImgPath=$("#watermark").val();
				$.extend(param,{"waterMarkLogoImgPath":waterMarkLogoImgPath});
			}
			$.getJSON(ajaxurl,param, function(data){
				if(data.message=='OK') {
					addToShowList(data.root[0]);
					$.messager.alert('消息','添加成功！');
					$('#webPhoto').val("");
					$('#webInfo').html("");
					$('#webphoto_test').attr('src','');
					$(".loding").removeClass("loding");
				}else if(data.message=='SIZE ERROR'){
					$.messager.alert('消息','图片已超过超过2M,请选择不超于2M的图片~~');
					$('#webPhoto').val("");
					$('#webInfo').html("");
					$('#webphoto_test').attr('src','');
					$(".loding").removeClass("loding");
				}else{
					$.messager.alert('消息','添加出错！');
					$('#webPhoto').val("");
					$('#webInfo').html("");
					$('#webphoto_test').attr('src','');
					$(".loding").removeClass("loding");
				}		  	
			});
	//	}else{
	//		$(".loding").removeClass("loding");
	//		$.messager.alert('消息','图片宽度已超过超过1024px，请选择不超于1024px的图片~~');
	//	}
}).error(function() {//失败
	var v=$('#webphoto_test').attr('src');
	$(".loding").removeClass("loding");
    if(v.length>0){
        $.messager.alert('消息','请填写正确的网络图片地址~~');
    }
    return false;
});


</script>
</body>
</html>
