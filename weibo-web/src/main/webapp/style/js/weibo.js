var SEND = false;

function checkText(id, count) {
	var v = $.trim( $('#' + id).val() );
	var left = calWbText(v, count);
	if (left >= 0) {
		$('#' + id + "_warn").html('还能输入<em>'+left+'</em>字');
	} else {
		$('#' + id + "_warn").html('已超出<em style="color:red;">'+Math.abs(left)+'</em>字');
	}
			
	return left>=0 && v;
}
function calWbText(text, count) {
	var cLen=0;
	var matcher = text.match(/[^\x00-\xff]/g), wlen  = (matcher && matcher.length) || 0;
	return Math.floor((count*2 - text.length - wlen)/2);
}

function init_timesend() {
	$('#send_ts').toggle();
}

function isUrl(s) {
	var regexp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
	return regexp.test(s);
}

//发送微博
function send_status() {
	if(SEND) {
		show_tip("正在发送微博，请不要重复提交！");
		return;
	}
	var weiboUid = $('#userId').val();
	var content = $('#status').val();
	var img_url = $('#img_url').val();
	var beginTime = $('#beginTime').val();
	var status = 0;
	if($('input[name="bean.status"]').attr('checked')) {
		status = 1;
	}
	var ajax_url = '/weibo-studio/sendWeibo.action';
	if(checkText("status", 140)) {
		SEND = true;
		$.post(ajax_url, {'bean.weiboUid':weiboUid,'bean.content':content,'bean.status':status,'bean.imageUrl':img_url,'bean.beginTime':beginTime},
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

function ajaxImgUpload() {
	//$("#img_loading").ajaxStart(function(){ $(this).show(); }).ajaxComplete(function(){ $(this).hide(); });
	$("#img_loading").show();
	$("#img_upload").hide();

	//document.domain = 'wbto.cn';
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
				$("#img_pop").html('<img src="'+data.url+'"/>');
			},
			error: function (data, status, e) {
				console.log(e);
				$("#img_loading").hide();
				$("#img_upload").show();
				//alert(e);
			}
		}
	);
	return false;
}

function ajaxImgDel() {
	$("#img_upload").show();
	$("#img_name, #img_upload_list").hide();
	$("#img_url").val("");
	
	var v = $.trim( $('#status').val() );
	if(v == "分享图片") $("#status").val('');
	checkText("status", 140);
}