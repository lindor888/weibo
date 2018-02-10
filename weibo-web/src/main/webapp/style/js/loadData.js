var $form = null;
//处理顶部内容
var topTemplate = null;
function getTableTop(){
	if(topTemplate==null){
		topTemplate = $('#topTemplate').html();
	}
	return topTemplate;
}
//处理中间内容
var middleTemplate = null;
function getTableMiddle(msg){
	var content = new Array();
	if(middleTemplate==null){
		middleTemplate = $('#middleTemplate').html();
	}
	var rows = msg.rows;
	var len = rows.length;
	if(len > 0) {
		for(var i=0;i<rows.length;i++){
			var num = i + from;
			var rowType = 'gruy';
			if(i%2==0){
				rowType = '';
			}
			var item = rows[i];
			if(item == null) {
				continue;
			}
			var status = -1;
			var action = "";
			var line  = middleTemplate.replace(/#\{([^\}]+)\}/g,function(m,$1){
				var value =  eval("item."+$1);
				if(value==null){
					var key = $1+'';
					if(key=='num'||key=='rowType'){
						value = eval($1);
					}else if(key == 'taskFrequency'){
						action = action.replace(/#\{taskFrequencyField\}/g,'');;
					} else {
						value = ' - ';
					}
				}else{
					if((""+$1).toLowerCase().indexOf('time')>-1 || (""+$1).toLowerCase().indexOf('date')>-1){
						value = value.replace('T',' ');
					}
					if(typeof(taskTypeField) != "undefined"&&(""+$1)==taskTypeField){
						action = action.replace(/#\{taskTypeField\}/g,value);
						value = taskTypeArr[value];
					}
					if(typeof(taskStateField) != "undefined"&&(""+$1)==taskStateField){
						status = value;
						action = actionArr[status];
						value = taskStateArr[value];
					}
					if(status == 1 && (""+$1)==taskForeverField){
						if(value == 1) {
							action = actionArr[8];//针对一次性任务,启动后不能暂停/恢复等操作
						} 
						action = action.replace(/#\{taskForeverField\}/g,value);
					} else if(status > -1 && (""+$1)==taskForeverField){
						action = action.replace(/#\{taskForeverField\}/g,value);
					}
					if(status>-1&&(""+$1)==taskFrequencyField){
						action = action.replace(/#\{taskFrequencyField\}/g,value);
					}
					if(status>-1&&(""+$1)==idField){
						action = action.replace(/#\{idField\}/g,value);
						return action;
					}
				}
				return value;
			}); 
			content.push(line);
		}
	} else {
		content.push($('#noDataTable').html());
	}
	return content.join('');
}
//拿到底部内容
var total = null;//总条数
var pageSum = null;//总页数
var curPage = null;//当前第几页
var from = 0;//当前页从第多少条开始
var to = 0;//当前页到第多少条结束 
function getTableBottom(msg){
	total = msg.total;
	$('#total').html(total);
	pageSum = msg.pageSum;
	$('#pageSum').html(pageSum);
	
	curPage = 0;
	from = 0;
	to = 0;
	if(pageSum<=1){
		$form[0]["example.page"].value = 1;
		if(pageSum==1){
			curPage = 1;
			from = 1;
			to = msg.rows.length;
		}
	}else{
		curPage = parseInt($form[0]["example.page"].value);
		var rowNum = parseInt($form[0]["example.rows"].value);//1页显示多少行
		var endLastPage = rowNum*(curPage-1);//上一页最后一行的序号
		from = endLastPage + 1;
		to = endLastPage + msg.rows.length;
	}
	$('#curPage').html(curPage);
	$('#from').html(from);
	$('#to').html(to);
	return $('#bottomTemplate').html();
}
//展示数据装载信息
var loadingMsg = null;
function showLoadingMsg(){
	if(loadingMsg==null){
		loadingMsg = $('#dataTable').html();
	}
	$('#dataTable').html(loadingMsg);
}
//填充表格
function fillTable(msg){
	var arr = new Array();
	var top = getTableTop();
	var end = getTableBottom(msg);
	var middle = getTableMiddle(msg);
	arr.push(top,middle,end);
	$('#dataTable').html(arr.join(''));
	
	//taskIndex.jsp页面使用以下代码
	var appLevel = $("#appLevel").val();
	if(appLevel == 0) {$("#super").hide();}
}
//首页
function first(){
	if(curPage<=1){
		alert('目前已是第一页！');
	}else{
		$form[0]["example.page"].value = 1;
		loadData();
	}
}
//尾页
function last(){
	if(curPage>=pageSum){
		alert('目前已是最后一页！');
	}else{
		$form[0]["example.page"].value = pageSum;
		loadData();
	}
}
//上一页
function pre(){
	if(curPage<=1){
		alert('目前已是第一页！');
	}else{
		$form[0]["example.page"].value = curPage - 1;
		loadData();
	}
}
//下一页
function next(){
	if(curPage>=pageSum){
		alert('目前已是最后一页！');
	}else{
		$form[0]["example.page"].value = curPage + 1;
		loadData();
	}
}
//展示错误信息
function showError(msg){
	$('body').html(msg);
}
//装载数据
function loadData(){
	showLoadingMsg();
	var url = $form.attr('action');
	var param = $form.serialize();
	$.ajax({
		type: "POST",
		url: url,
		data: param,
		success: function(msg){
		   if(typeof msg.total == 'undefined'){
			   showError(msg);
		   }else{
			   fillTable(msg);
		   }
		}
	});
}
//操作用户
function oprateUser(url,param){
	$.ajax({
		type: "POST",
		url: url,
		data: param,
		success: function(msg){
		   if(typeof msg.msg == 'undefined' && msg.result == 'undefined'){
			   showError(msg);
		   }else{
			   alert('操作成功！');
			   if(url.indexOf('del')>-1 || url.indexOf('delete') > -1){
				  if(from==to&&curPage>1){
					  $form[0]["example.page"].value = curPage - 1;
				  }
			   }
			   loadData();
		   }
		}
	});
}