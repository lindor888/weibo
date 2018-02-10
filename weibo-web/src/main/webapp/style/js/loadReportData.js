/*****************网站概况******************/


var siteGeneralActionUrl = [
                 '/weibo-studio/report/getTowDaysHourReportFormSiteGeneral.action',
                 '/weibo-studio/report/getSomeDaysReportFormSiteGeneral.action',
                 '/weibo-studio/report/getTodayAndYesterDayTableSiteGeneral.action',
                 '/weibo-studio/report/getSourceDataSiteGeneral.action',
                 '/weibo-studio/report/getStartPageDataSiteGeneral.action',
                 '/weibo-studio/report/getPageDataSiteGeneral.action'
                 ];

var siteGeneralTime = new Array();
var siteGeneralDataLegend = new Array();
var siteGeneralData = new Array();
var siteGeneralJsonData1 = null;
var siteGeneralJsonData2 = null;

function initSiteGeneral(){
	siteGeneralTime = new Array();
	siteGeneralDataLegend = new Array();
	siteGeneralData = new Array();
	siteGeneralJsonData1 = null;
	siteGeneralJsonData2 = null;
}

function loadSiteGeneralData(){
	$("#dataTable tr:not(:first)").remove();
	url = siteGeneralActionUrl[2];
	$.ajax({
		type: "POST",
		url: url,
		async: false,
		success: function(msg){
			var data = msg.rows;
			var html = "";
			for(var i=0;i<data.length;i++){
				html += "<tr><td>" + data[i].countTime.replace('T',' ').substring(0,10) + "</td>";
				html += "<td>" + tranNullToBlank(data[i].countPv) + "</td>";
				html += "<td>" + tranNullToBlank(data[i].countUv) + "</td>";
				html += "<td>" + tranNullToBlank(data[i].countIp) + "</td>";
				html += "<td>" + tranRate(data[i].jumpRate) + "</td>";
				html += "<td>" + tranTime(data[i].avgVisitTime) + "</td>";
				html += "<td>" + tranNullToBlank(data[i].tranNum) + "</td></tr>";
			}
			$("#dataTable").append(html);
		}
	});
}

function loadSiteGeneralSourceTable(id){
	$("#sourceTable tr:not(:first)").remove();
	url = siteGeneralActionUrl[3];
	if(id==0){
		type = 1;
	}else if(id==1){
		type = 7;
	}else{
		type = 30;
	}
	$.ajax({
		type: "POST",
		url: url,
		async: false,
		data:{'type':type},
		success: function(msg){
			var data = msg.data;
			var html = "";
			if(data.length==0){
				html += "<tr><td colspan='3'>暂无数据</td></tr>";
			}else{
				var totalPv = 0;
				for(var i=0;i<data.length;i++){
					totalPv += data[i].countPv;
				}
				var showPv = 0;
				for(var i=0;i<(data.length>9?9:data.length);i++){
					html += "<tr><td>" + (data[i].pageUrl=="direct"?"直接访问":data[i].pageUrl) +"</td>";
					html += "<td>" + data[i].countPv +"</td>";
					html += "<td>" + parseFloat(data[i].countPv/totalPv*100).toFixed(2) +"%</td></tr>";
					showPv += data[i].countPv;
				}
				if(data.length>9){
					html += "<tr><td>其他</td><td>" + (totalPv-showPv) + "</td>";
					html += "<td>" + parseInt((totalPv-showPv)/totalPv*100) +"%</td></tr>";
				}
			}
			$("#sourceTable").append(html);
		}
	});
}

function loadSiteGeneralStartPageTable(id){
	$("#startPageTable tr:not(:first)").remove();
	url = siteGeneralActionUrl[4];
	if(id==0){
		type = 1;
	}else if(id==1){
		type = 7;
	}else{
		type = 30;
	}
	$.ajax({
		type: "POST",
		url: url,
		async: false,
		data:{'type':type},
		success: function(msg){
			var data = msg.data;
			var html = "";
			if(data.length==0){
				html += "<tr><td colspan='3'>暂无数据</td></tr>";
			}else{
				var totalPv = 0;
				for(var i=0;i<data.length;i++){
					totalPv += data[i].countPv;
				}
				var showPv = 0;
				for(var i=0;i<(data.length>9?9:data.length);i++){
					html += "<tr><td>" + data[i].pageUrl +"</td>";
					html += "<td>" + data[i].countPv +"</td>";
					html += "<td>" + parseFloat(data[i].countPv/totalPv*100).toFixed(2) +"%</td></tr>";
					showPv += data[i].countPv;
				}
				if(data.length>9){
					html += "<tr><td>其他</td><td>" + (totalPv-showPv) + "</td>";
					html += "<td>" + parseInt((totalPv-showPv)/totalPv*100) +"%</td></tr>";
				}
			}
			$("#startPageTable").append(html);
		}
	});
}

function loadSiteGeneralPageTable(id){
	$("#pageTable tr:not(:first)").remove();
	url = siteGeneralActionUrl[5];
	if(id==0){
		type = 1;
	}else if(id==1){
		type = 7;
	}else{
		type = 30;
	}
	$.ajax({
		type: "POST",
		url: url,
		async: false,
		data:{'type':type},
		success: function(msg){
			var data = msg.data;
			var html = "";
			if(data.length==0){
				html += "<tr><td colspan='3'>暂无数据</td></tr>";
			}else{
				var totalPv = 0;
				for(var i=0;i<data.length;i++){
					totalPv += data[i].countPv;
				}
				var showPv = 0;
				for(var i=0;i<(data.length>9?9:data.length);i++){
					html += "<tr><td>" + data[i].pageUrl +"</td>";
					html += "<td>" + data[i].countPv +"</td>";
					html += "<td>" + parseFloat(data[i].countPv/totalPv*100).toFixed(2) +"%</td></tr>";
					showPv += data[i].countPv;
				}
				if(data.length>9){
					html += "<tr><td>其他</td><td>" + (totalPv-showPv) + "</td>";
					html += "<td>" + parseInt((totalPv-showPv)/totalPv*100) +"%</td></tr>";
				}
			}
			$("#pageTable").append(html);
		}
	});
}

function siteGeneralLoadData(siteGeneralJsonData1,siteGeneralJsonData2,siteGeneralTime,siteGeneralDataLegend){
	siteGeneralData = new Array();
	var data1 = new Array();
	var type = $('input[name="choose"]:checked').val();
	if(type=="countPv"){
		for(var i=0;i<siteGeneralTime.length;i++){
			data1.push(tranNullToZero(siteGeneralJsonData1[i].countPv));			
		}
	}else if(type=="countUv"){
		for(var i=0;i<siteGeneralTime.length;i++){
			data1.push(tranNullToZero(siteGeneralJsonData1[i].countUv));			
		}
	}else if(type=="countIp"){
		for(var i=0;i<siteGeneralTime.length;i++){
			data1.push(tranNullToZero(siteGeneralJsonData1[i].countIp));			
		}
	}else if(type=="jumpRate"){
		for(var i=0;i<siteGeneralTime.length;i++){
			data1.push(tranNullToZero(siteGeneralJsonData1[i].jumpRate));			
		}
	}else if(type=="avgVisitTime"){
		for(var i=0;i<siteGeneralTime.length;i++){
			data1.push(tranNullToZero(siteGeneralJsonData1[i].avgVisitTime));
			
		}
	}else if(type=="tranNum"){
		for(var i=0;i<siteGeneralTime.length;i++){
			data1.push(tranNullToZero(siteGeneralJsonData1[i].tranNum));
		}
	}
	siteGeneralData1 = new lineData(siteGeneralDataLegend[0],'line',data1);
	siteGeneralData.push(siteGeneralData1);
	if(siteGeneralJsonData2!=null&&siteGeneralJsonData2.length>0){
		var data2 = new Array();
		if(type=="countPv"){
			for(var i=0;i<siteGeneralTime.length;i++){
				data2.push(tranNullToZero(siteGeneralJsonData2[i].countPv));			
			}
		}else if(type=="countUv"){
			for(var i=0;i<siteGeneralTime.length;i++){
				data2.push(tranNullToZero(siteGeneralJsonData2[i].countUv));			
			}
		}else if(type=="countIp"){
			for(var i=0;i<siteGeneralTime.length;i++){
				data2.push(tranNullToZero(siteGeneralJsonData2[i].countIp));			
			}
		}else if(type=="jumpRate"){
			for(var i=0;i<siteGeneralTime.length;i++){
				data2.push(tranNullToZero(siteGeneralJsonData2[i].jumpRate));			
			}
		}else if(type=="avgVisitTime"){
			for(var i=0;i<siteGeneralTime.length;i++){
				data2.push(tranNullToZero(siteGeneralJsonData2[i].avgVisitTime));
				
			}
		}else if(type=="tranNum"){
			for(var i=0;i<siteGeneralTime.length;i++){
				data2.push(tranNullToZero(siteGeneralJsonData2[i].tranNum));
			}
		}
		siteGeneralData2 = new lineData(siteGeneralDataLegend[1],'line',data2);
		siteGeneralData.push(siteGeneralData2);
	}
	
	
}

function loadDataByA(id){
	$("#main").html('');
	$("[id^=a]").removeClass();
	$("#a"+id).attr('class','cur');
	if(id==0){
		url = siteGeneralActionUrl[0];
		$.ajax({
			type: "POST",
			url: url,
			async: false,
			success: function(msg){
				initSiteGeneral();
				var jsonTime = msg.times;
				siteGeneralJsonData1 = msg.data1;
				siteGeneralJsonData2 = msg.data2;
				for(var i=0;i<jsonTime.length;i++){
					siteGeneralTime.push(jsonTime[i]);
				}
				siteGeneralDataLegend = ['今天','昨天'];
				siteGeneralLoadData(siteGeneralJsonData1, siteGeneralJsonData2, siteGeneralTime, siteGeneralDataLegend);
				getLineReportForm(siteGeneralTime, siteGeneralDataLegend, siteGeneralData,"main");
			}
		});
		
	}else{
		url = siteGeneralActionUrl[1];
		var type;
		if(id==1){
			type = 7;
		}else{
			type = 30;
		}
		$.ajax({
			type: "POST",
			url: url,
			async: false,
			data:{'type':type},
			success: function(msg){
				initSiteGeneral();
				var jsonTime = msg.times;
				siteGeneralJsonData1 = msg.data1;
				for(var i=0;i<jsonTime.length;i++){
					siteGeneralTime.push(jsonTime[i]);
				}
				siteGeneralDataLegend = ['value'];
				siteGeneralLoadData(siteGeneralJsonData1, siteGeneralJsonData2, siteGeneralTime, siteGeneralDataLegend);
				getLineReportForm(siteGeneralTime, siteGeneralDataLegend, siteGeneralData,"main");
			}
		});	
	}
	loadSiteGeneralSourceTable(id);
	loadSiteGeneralStartPageTable(id);
	loadSiteGeneralPageTable(id);
}

function loadDataByR(){
	$("#main").html('');
	siteGeneralLoadData(siteGeneralJsonData1, siteGeneralJsonData2, siteGeneralTime, siteGeneralDataLegend);
	getLineReportForm(siteGeneralTime, siteGeneralDataLegend, siteGeneralData,"main");
}


/******************实时访客*****************/

var realTimeActionUrl = [
                         "/weibo-studio/report/getLast30ReportFormRealTime.action",
                         "/weibo-studio/report/getLastVisitRealTime.action"
                         ];

function chooseData(type){
	$("#main").html('');
	var checkboxArray = $(':checkbox');
	var checkboxCheckedArray = $(':checkbox:checked');
	var checkedLen = checkboxCheckedArray.length;
	if(checkedLen==0){
		checkboxArray[type].checked = true;
	}
	if(checkedLen==3){
		if(type==0){
			checkboxCheckedArray[1].checked = false;
		}else{
			checkboxCheckedArray[0].checked = false;
		}
		
	}
	checkboxCheckedArray = $(':checkbox:checked');
	if(checkboxCheckedArray.length>0){
		url = realTimeActionUrl[0];
		$.ajax({
			type: "POST",
			url: url,
			async: false,
			success: function(msg){
				var jsonTime = msg.times;
				var jsonData = msg.data;
				
				var time = new Array();
				var data1 = new Array();
				var data2 = new Array();
				var dataLegend = new Array();
				var dataSeries =  new Array();
				for(var i=0;i<jsonTime.length;i++){
					time.push(jsonTime[i]);
				}
				
				checkboxCheckedArray = $(':checkbox:checked');
				
				if(checkboxCheckedArray[0].value=="countPv"){
					for(var i=0;i<time.length;i++){
						data1.push(tranNullToZero(jsonData[i].countPv));
					}
					dataLegend.push("PV");
				}else if(checkboxCheckedArray[0].value=="countUv"){
					for(var i=0;i<time.length;i++){
						data1.push(tranNullToZero(jsonData[i].countUv));
					}
					dataLegend.push("UV");
				}else if(checkboxCheckedArray[0].value=="countIp"){
					for(var i=0;i<time.length;i++){
						data1.push(tranNullToZero(jsonData[i].countIp));
					}
					dataLegend.push("IP");
				}
				
				if(checkboxCheckedArray.length==2){
					if(checkboxCheckedArray[1].value=="countPv"){
						for(var i=0;i<time.length;i++){
							data2.push(tranNullToZero(jsonData[i].countPv));
						}
						dataLegend.push("PV");
					}else if(checkboxCheckedArray[1].value=="countUv"){
						for(var i=0;i<time.length;i++){
							data2.push(tranNullToZero(jsonData[i].countUv));
						}
						dataLegend.push("UV");
					}else if(checkboxCheckedArray[1].value=="countIp"){
						for(var i=0;i<time.length;i++){
							data2.push(tranNullToZero(jsonData[i].countIp));
						}
						dataLegend.push("IP");
					}
				}
				var realTimeData1 = new lineData(dataLegend[0],'line',data1);
				dataSeries.push(realTimeData1);
				if(data2.length > 0){
					var realTimeData2 = new lineData(dataLegend[1],'line',data2);
					dataSeries.push(realTimeData2);
				}
				getLineReportForm(time, dataLegend, dataSeries,"main");
			}
		});
	}
}

function loadLastVisitData(){
	url = realTimeActionUrl[1];
	$.ajax({
		type: "POST",
		url: url,
		async: false,
		success: function(msg){
			var data = msg.data;
			var jsonDataArray = eval("(" + data + ")");
			var html = "";
			for(var i=0;i<jsonDataArray.length;i++){
				html += "<tr>";
				html += "<td>" + (i+1) +"</td>";
				html += "<td>" + jsonDataArray[i].server_time.$date.replace('T',' ').substring(0,19) +"</td>";
				html += "<td>" + (jsonDataArray[i].source_page_url=="direct"?"直接访问":jsonDataArray[i].source_page_url) +"</td>";
				html += "<td>" + jsonDataArray[i].start_page_url +"</td>";
				html += "<td>" + (jsonDataArray[i].end_page_url==null?"-":jsonDataArray[i].end_page_url) +"</td>";
				html += "<td>" + tranTime(parseFloat(jsonDataArray[i].spent_time)/1000) +"</td>";
				html += "<td>" + jsonDataArray[i].active_num +"</td>";
				html += "</tr>";
			}
			$("#mainTable").append(html);
		}
	});
}
/*************今日统计、最近30天***********************/
var siteCountActionUril = [
                          "/weibo-studio/report/getDayCountDataSiteCount.action",
                          "/weibo-studio/report/getDayHourDataSiteCount.action",
                          "/weibo-studio/report/getSomeTimeCountDataSiteCount.action",
                          "/weibo-studio/report/getSomeTimeDayDataSiteCount.action"
                           ];
var siteCountTimes = new Array();
var siteCountData = new Array();

function siteCountInit(){
	siteCountTimes = new Array();
	siteCountData = new Array();
}

function loadCountToday(id){
	siteCountInit();
	type = id;
	if(id!="countTime"){
		$("[id^=a]").removeClass();
		$("#a"+id).attr('class','cur');
		$("#countTime").val('');
		
		loadCountTodayDataTable(type);
		loadCountTodayHourData(type);
		loadCountBottomTable();
		chooseCountTodayData(0);
	}else if($("#countTime").val()!=null&&$("#countTime").val()!=""){
		$("[id^=a]").removeClass();
		type = $("#countTime").val().replace(new RegExp("-","gm"),"");
		
		loadCountTodayDataTable(type);
		loadCountTodayHourData(type);
		loadCountBottomTable();
		chooseCountTodayData(0);
	}
}


function loadCountTodayHourData(type){
	url = siteCountActionUril[1];
	$.ajax({
		type: "POST",
		url: url,
		async: false,
		data:{'type':type},
		success: function(msg){
			siteCountTimes = msg.times;
			siteCountData = msg.data;
		}
	});
}


function loadCountBottomTable(){
	$("#bottomTable tr:not(:first)").remove();
	var html = "";
	for(var i=0;i<siteCountData.length;i++){
		html += "<tr><td>" + (i+1) + "</td>";
		html += "<td>" + siteCountData[i].countTime.replace('T',' ') + "</td>";
		html += "<td>" + siteCountData[i].countPv + "</td>";
		html += "<td>" + siteCountData[i].countVisitNum + "</td>";
		html += "<td>" + siteCountData[i].countUv + "</td>";
		html += "<td>" + siteCountData[i].countNewUv + "</td>";
		html += "<td>" + siteCountData[i].countIp + "</td>";
		html += "<td>" + tranRate(siteCountData[i].jumpRate) + "</td>";
		html += "<td>" + tranTime(siteCountData[i].avgVisitTime) + "</td>";
		html += "<td>" + siteCountData[i].avgVisitPageNum + "</td>";
		html += "<td>" + siteCountData[i].tranNum + "</td>";
		html += "<td>" + tranRate(siteCountData[i].tranRate) + "</td></tr>";
	}
	$("#bottomTable").append(html);
}



function loadCountTodayDataTable(type){
	$("#dataTable tr:not(:first)").remove();
	url = siteCountActionUril[0];
	$.ajax({
		type: "POST",
		url: url,
		async: false,
		data:{'type':type},
		success: function(msg){
			var data = msg.data;
			var html = "";
			html += "<tr><td>" + data.countPv+ "</td>";
			html += "<td>" + data.countUv+ "</td>";
			html += "<td>" + data.countNewUv+ "</td>";
			html += "<td>" + data.countIp+ "</td>";
			html += "<td>" + data.avgVisitPageNum + "</td>";
			html += "<td>" + tranRate(data.tranRate)+ "</td></tr>";
			$("#dataTable").append(html);
		}
	});
}

function chooseCountTodayData(id){
	$("#main").html('');
	var checkboxArray = $(':checkbox');
	var checkboxCheckedArray = $(':checkbox:checked');
	var checkedLen = checkboxCheckedArray.length;
	if(checkedLen==0){
		checkboxArray[id].checked = true;
	}
	if(checkedLen>2){
		if("choose"+(id+1)==checkboxCheckedArray[0].id){
			checkboxCheckedArray[1].checked = false;
		}else if("choose"+(id+1)==checkboxCheckedArray[1].id){
			checkboxCheckedArray[2].checked = false;
		}else{
			checkboxCheckedArray[0].checked = false;
		}
	}
	checkboxCheckedArray = $(':checkbox:checked');
	var data1 = new Array();
	var data2 = new Array();
	var dataLegend = new Array();
	var dataSeries = new Array();
	
	if(checkboxCheckedArray[0].value=="countPv"){
		for(var i=0;i<siteCountTimes.length;i++){
			data1.push(tranNullToZero(siteCountData[i].countPv));
		}
		dataLegend.push("PV");
	}else if(checkboxCheckedArray[0].value=="countVisitNum"){
		for(var i=0;i<siteCountTimes.length;i++){
			data1.push(tranNullToZero(siteCountData[i].countVisitNum));
		}
		dataLegend.push("访问次数");
	}else if(checkboxCheckedArray[0].value=="countUv"){
		for(var i=0;i<siteCountTimes.length;i++){
			data1.push(tranNullToZero(siteCountData[i].countUv));
		}
		dataLegend.push("UV");
	}else if(checkboxCheckedArray[0].value=="countNewUv"){
		for(var i=0;i<siteCountTimes.length;i++){
			data1.push(tranNullToZero(siteCountData[i].countNewUv));
		}
		dataLegend.push("新访客数");
	}else if(checkboxCheckedArray[0].value=="countIp"){
		for(var i=0;i<siteCountTimes.length;i++){
			data1.push(tranNullToZero(siteCountData[i].countIp));
		}
		dataLegend.push("IP");
	}else if(checkboxCheckedArray[0].value=="jumpRate"){
		for(var i=0;i<siteCountTimes.length;i++){
			data1.push(tranNullToZero(siteCountData[i].jumpRate));
		}
		dataLegend.push("跳出率");
	}else if(checkboxCheckedArray[0].value=="avgVisitTime"){
		for(var i=0;i<siteCountTimes.length;i++){
			data1.push(tranNullToZero(siteCountData[i].avgVisitTime));
		}
		dataLegend.push("平均访问时长");
	}else if(checkboxCheckedArray[0].value=="avgVisitPageNum"){
		for(var i=0;i<siteCountTimes.length;i++){
			data1.push(tranNullToZero(siteCountData[i].avgVisitPageNum));
		}
		dataLegend.push("平均访问页数");
	}else if(checkboxCheckedArray[0].value=="tranNum"){
		for(var i=0;i<siteCountTimes.length;i++){
			data1.push(tranNullToZero(siteCountData[i].tranNum));
		}
		dataLegend.push("转化次数");
	}else if(checkboxCheckedArray[0].value=="tranRate"){
		for(var i=0;i<siteCountTimes.length;i++){
			data1.push(tranNullToZero(siteCountData[i].tranRate));
		}
		dataLegend.push("转化率");
	}
	
	if(checkboxCheckedArray.length==2){
		if(checkboxCheckedArray[1].value=="countPv"){
			for(var i=0;i<siteCountTimes.length;i++){
				data2.push(tranNullToZero(siteCountData[i].countPv));
			}
			dataLegend.push("PV");
		}else if(checkboxCheckedArray[1].value=="countVisitNum"){
			for(var i=0;i<siteCountTimes.length;i++){
				data2.push(tranNullToZero(siteCountData[i].countVisitNum));
			}
			dataLegend.push("访问次数");
		}else if(checkboxCheckedArray[1].value=="countUv"){
			for(var i=0;i<siteCountTimes.length;i++){
				data2.push(tranNullToZero(siteCountData[i].countUv));
			}
			dataLegend.push("UV");
		}else if(checkboxCheckedArray[1].value=="countNewUv"){
			for(var i=0;i<siteCountTimes.length;i++){
				data2.push(tranNullToZero(siteCountData[i].countNewUv));
			}
			dataLegend.push("新访客数");
		}else if(checkboxCheckedArray[1].value=="countIp"){
			for(var i=0;i<siteCountTimes.length;i++){
				data2.push(tranNullToZero(siteCountData[i].countIp));
			}
			dataLegend.push("IP");
		}else if(checkboxCheckedArray[1].value=="jumpRate"){
			for(var i=0;i<siteCountTimes.length;i++){
				data2.push(tranNullToZero(siteCountData[i].jumpRate));
			}
			dataLegend.push("跳出率");
		}else if(checkboxCheckedArray[1].value=="avgVisitTime"){
			for(var i=0;i<siteCountTimes.length;i++){
				data2.push(tranNullToZero(siteCountData[i].avgVisitTime));
			}
			dataLegend.push("平均访问时长");
		}else if(checkboxCheckedArray[1].value=="avgVisitPageNum"){
			for(var i=0;i<siteCountTimes.length;i++){
				data2.push(tranNullToZero(siteCountData[i].avgVisitPageNum));
			}
			dataLegend.push("平均访问页数");
		}else if(checkboxCheckedArray[1].value=="tranNum"){
			for(var i=0;i<siteCountTimes.length;i++){
				data2.push(tranNullToZero(siteCountData[i].tranNum));
			}
			dataLegend.push("转化次数");
		}else if(checkboxCheckedArray[1].value=="tranRate"){
			for(var i=0;i<siteCountTimes.length;i++){
				data2.push(tranNullToZero(siteCountData[i].tranRate));
			}
			dataLegend.push("转化率");
		}
	}
	
	var countData1 = new lineData(dataLegend[0],'line',data1);
	dataSeries.push(countData1);
	if(data2.length > 0){
		var countData2 = new lineData(dataLegend[1],'line',data2);
		dataSeries.push(countData2);
	}
	getLineReportForm(siteCountTimes, dataLegend, dataSeries,"main");
}


function loadCountSomeTime(id){
	siteCountInit();
	if(id!="countTime"){
		$("[id^=a]").removeClass();
		$("#a"+id).attr('class','cur');
		$("#countBeginTime").val('');
		$("#countEndTime").val('');
		
		loadCountSomeTimeTable(id);
		loadCountSomeTimeData(id);
		loadCountBottomTable();
		chooseCountTodayData(0);
	}else if($("#countBeginTime").val()!=null&&$("#countBeginTime").val()!=""&&$("#countEndTime").val()!=null&&$("#countEndTime").val()!=""){
		$("[id^=a]").removeClass();
		beginTime = $("#countBeginTime").val().replace(new RegExp("-","gm"),"");
		endTime = $("#countEndTime").val().replace(new RegExp("-","gm"),"");
		
		beginTime = strToDate(beginTime);
		endTime = strToDate(endTime);
		var infactDistance = endTime.getTime()-beginTime.getTime();
		if(infactDistance<0){
			alert("开始时间小于结束时间!");
		}else{
			var distance = 1000 * 60 * 60 *24 *30;
			if(infactDistance>distance){
				alert("时间差不能大于30天!");
			}else{
				loadCountSomeTimeTable(id);
				loadCountSomeTimeData(id);
				loadCountBottomTable();
				chooseCountTodayData(0);
			}
		}
	}
}

function strToDate(time){
	var timeYear = parseInt(time.substring(0,4));
	var timeMonth = parseInt(time.substring(4,6))-1;
	var timeDay = parseInt(time.substring(6,8));
	return new Date(timeYear,timeMonth,timeDay);
}

function loadCountSomeTimeTable(id){
	$("#dataTable tr:not(:first)").remove();
	url = siteCountActionUril[2];
	if(id=='countTime'){
		beginTime = $("#countBeginTime").val().replace(new RegExp("-","gm"),"");
		endTime = $("#countEndTime").val().replace(new RegExp("-","gm"),"");
		$.ajax({
			type: "POST",
			url: url,
			async: false,
			data:{'beginTime':beginTime,'endTime':endTime},
			success: function(msg){
				var data = msg.data;
				var html = "";
				html += "<tr><td>" + data.countPv+ "</td>";
				html += "<td>" + data.countUv+ "</td>";
				html += "<td>" + data.countNewUv+ "</td>";
				html += "<td>" + data.countIp+ "</td>";
				html += "<td>" + data.avgVisitPageNum + "</td>";
				html += "<td>" + tranRate(data.tranRate)+ "</td></tr>";
				$("#dataTable").append(html);
			}
		});
	}else{
		type = id==0?30:7;
		$.ajax({
			type: "POST",
			url: url,
			async: false,
			data:{'type':type},
			success: function(msg){
				var data = msg.data;
				var html = "";
				html += "<tr><td>" + data.countPv+ "</td>";
				html += "<td>" + data.countUv+ "</td>";
				html += "<td>" + data.countNewUv+ "</td>";
				html += "<td>" + data.countIp+ "</td>";
				html += "<td>" + data.avgVisitPageNum + "</td>";
				html += "<td>" + tranRate(data.tranRate)+ "</td></tr>";
				$("#dataTable").append(html);
			}
		});
	}
}

function loadCountSomeTimeData(id){
	url = siteCountActionUril[3];
	if(id=="countTime"){
		beginTime = $("#countBeginTime").val().replace(new RegExp("-","gm"),"");
		endTime = $("#countEndTime").val().replace(new RegExp("-","gm"),"");
		
		$.ajax({
			type: "POST",
			url: url,
			async: false,
			data:{'beginTime':beginTime,'endTime':endTime},
			success: function(msg){
				siteCountTimes = msg.times;
				siteCountData = msg.data;
			}
		});
		
	}else{
		type = id==0?30:7;
		$.ajax({
			type: "POST",
			url: url,
			async: false,
			data:{'type':type},
			success: function(msg){
				siteCountTimes = msg.times;
				siteCountData = msg.data;
			}
		});
	}
	
}
/*************来源分析***********************/
var sourceActionUrl = [
                       "/weibo-studio/report/getDayAllDataSource.action",
                       "/weibo-studio/report/getDayHourDataSource.action",
                       "/weibo-studio/report/getSomeDayAllDataSource.action",
                       "/weibo-studio/report/getSomeDayDayDataSource.action"
                       ];

var directDataForPie = null;
var waibuDataForPie = null;
var searchDataForPie = null;
var allData = null;
var lineDataValue = new Array();

var directDataForLine = null;
var waibuDataForLine = null;
var searchDataForLine = null;
var pieDataValue = new Array();
var sourceTimes = new Array();



function sourceInit(){
	directDataForPie = null;
	waibuDataForPie = null;
	searchDataForPie = null;
	allData = null;
	lineDataValue = new Array();

	directDataForLine = null;
	waibuDataForLine = null;
	searchDataForLine = null;
	pieDataValue = new Array();
	sourceTimes = new Array();
}


function loadSourcePieDataValue(){
	$("#mainPie").html('');
	pieDataValue = new Array();
	var dataName = new Array();
	dataName = ['外部链接','直接访问','搜索引擎'];
	var radioType = $('input[name="choose"]:checked').val();
	if(radioType=="countPv"){
		itemWaiBu = new pieData('外部链接',waibuDataForPie.countPv);
		itemDirect = new pieData('直接访问',directDataForPie.countPv);
		itemSearch = new pieData('搜索引擎',searchDataForPie.countPv);
		
	}else if(radioType=="countVisitNum"){
		itemWaiBu = new pieData('外部链接',waibuDataForPie.countVisitNum);
		itemDirect = new pieData('直接访问',directDataForPie.countVisitNum);
		itemSearch = new pieData('搜索引擎',searchDataForPie.countVisitNum);
		
	}else if(radioType=="countUv"){
		itemWaiBu = new pieData('外部链接',waibuDataForPie.countUv);
		itemDirect = new pieData('直接访问',directDataForPie.countUv);
		itemSearch = new pieData('搜索引擎',searchDataForPie.countUv);
		
	}else if(radioType=="countNewUv"){
		itemWaiBu = new pieData('外部链接',waibuDataForPie.countNewUv);
		itemDirect = new pieData('直接访问',directDataForPie.countNewUv);
		itemSearch = new pieData('搜索引擎',searchDataForPie.countNewUv);
		
	}else if(radioType=="countIp"){
		itemWaiBu = new pieData('外部链接',waibuDataForPie.countIp);
		itemDirect = new pieData('直接访问',directDataForPie.countIp);
		itemSearch = new pieData('搜索引擎',searchDataForPie.countIp);
		
	}else if(radioType=="tranNum"){
		itemWaiBu = new pieData('外部链接',waibuDataForPie.tranNum);
		itemDirect = new pieData('直接访问',directDataForPie.tranNum);
		itemSearch = new pieData('搜索引擎',searchDataForPie.tranNum);
		
	}
	pieDataValue.push(itemWaiBu);
	pieDataValue.push(itemDirect);
	pieDataValue.push(itemSearch);
	getPieReportForm(dataName,pieDataValue,"mainPie");
}


function loadSourceData(id){
	sourceInit();
	if(id!="countTime"){
		$("[id^=a]").removeClass();
		$("#a"+id).attr('class','cur');
		$("#countTime").val('');
		if(id==0){
			url = sourceActionUrl[0];
			$.ajax({
				type: "POST",
				url: url,
				async: false,
				success: function(msg){
					directDataForPie = msg.directData;
					waibuDataForPie = msg.waibuData;
					searchDataForPie = msg.searchData;
					allData = msg.allData;
					
					loadSourceTopTable();
					loadSourceBottomTable();
					loadSourcePieDataValue();
				}
			});
			getHourData();
		}else{
			var type;
			if(id==1){
				type = 7;
			}else{
				type = 30;
			}
			url = sourceActionUrl[2];
			$.ajax({
				type: "POST",
				url: url,
				async: false,
				data:{'type':type},
				success: function(msg){
					
					directDataForPie = msg.directData;
					waibuDataForPie = msg.waibuData;
					searchDataForPie = msg.searchData;
					allData = msg.allData;
					times = msg.times;
					
					loadSourceTopTable();
					loadSourceBottomTable();
					loadSourcePieDataValue();
				}
			});
			getDayData(id);
		}
	}else if($("#countTime").val()!=null&&$("#countTime").val()!=""){
		$("[id^=a]").removeClass();
		var type = $("#countTime").val().replace(new RegExp("-","gm"),"");
		url = sourceActionUrl[0];
		$.ajax({
			type: "POST",
			url: url,
			async: false,
			data:{'type':type},
			success: function(msg){
				directDataForPie = msg.directData;
				waibuDataForPie = msg.waibuData;
				searchDataForPie = msg.searchData;
				allData = msg.allData;
				
				loadSourceTopTable();
				loadSourceBottomTable();
				loadSourcePieDataValue();
			}
		});
		getHourData();
	}
	
	
}

function loadSourceTopTable(){
	$("#allTable tr:not(:first)").remove();
	var topTableHtml = "";
	topTableHtml += "<tr><td>" + allData.countPv + "</td>";
	topTableHtml += "<td>" + allData.countUv + "</td>";
	topTableHtml += "<td>" +allData.countIp + "</td>";
	topTableHtml += "<td>" + allData.countVisitNum +"</td>";
	topTableHtml += "<td>" + allData.countNewUv +"</td>";
	topTableHtml += "<td>" + allData.tranNum +"</td></tr>";
	$("#allTable").append(topTableHtml);
}

function getDayData(id){
	var type;
	if(id==1){
		type = 7;
	}else{
		type = 30;
	}
	$.ajax({
		type: "POST",
		url: sourceActionUrl[3],
		async: false,
		data:{'type':type},
		success: function(msg){
			directForLine = msg.directData;
			searchForLine = msg.searchData;
			waibuForLine = msg.waibuData;
			sourceTimes = msg.times;
			loadSourceLineData(id);			
		}
	});
}


function getHourData(){
	var type = $("#countTime").val().replace(new RegExp("-","gm"),"");
	$.ajax({
		type: "POST",
		url: sourceActionUrl[1],
		async: false,
		data:{'type':type},
		success: function(msg){
			directForLine = msg.directData;
			searchForLine = msg.searchData;
			waibuForLine = msg.waibuData;
			sourceTimes = msg.times;
			loadSourceLineData(0);	
		}
	});
}


function loadSourceLineData(id){
	$("#main").html('');
	lineDataValue = new Array();
	var num = 0;
	if(id==0){
		num = 24;
	}else if(id==1){
		num = 7;
	}else if(id==2){
		num = 30;
	}
	
	var dataLegend = ['外部链接','直接访问','搜索引擎'];
	var data1 = new Array();
	var data2 = new Array();
	var data3 = new Array();
	
	var type = $('input[name="choose"]:checked').val();
	if(type=="countPv"){
		for(var i=0;i<num;i++){
			data1.push(waibuForLine[i].countPv);
			data2.push(directForLine[i].countPv);
			data3.push(searchForLine[i].countPv);
		}
	}else if(type=="countUv"){
		for(var i=0;i<num;i++){
			data1.push(waibuForLine[i].countUv);
			data2.push(directForLine[i].countUv);
			data3.push(searchForLine[i].countUv);
		}
	}else if(type=="countIp"){
		for(var i=0;i<num;i++){
			data1.push(waibuForLine[i].countIp);
			data2.push(directForLine[i].countIp);
			data3.push(searchForLine[i].countIp);
		}
		
	}else if(type=="countVisitNum"){
		for(var i=0;i<num;i++){
			data1.push(waibuForLine[i].countVisitNum);
			data2.push(directForLine[i].countVisitNum);
			data3.push(searchForLine[i].countVisitNum);
		}
		
	}else if(type=="countNewUv"){
		for(var i=0;i<num;i++){
			data1.push(waibuForLine[i].countNewUv);
			data2.push(directForLine[i].countNewUv);
			data3.push(searchForLine[i].countNewUv);
		}
		
	}else if(type=="tranNum"){
		for(var i=0;i<num;i++){
			data1.push(waibuForLine[i].tranNum);
			data2.push(directForLine[i].tranNum);
			data3.push(searchForLine[i].tranNum);
		}
	}
	dataSeries1 = new lineData(dataLegend[0], 'line', data1);
	dataSeries2 = new lineData(dataLegend[1], 'line', data2);
	dataSeries3 = new lineData(dataLegend[2], 'line', data3);
	lineDataValue.push(dataSeries1);
	lineDataValue.push(dataSeries2);
	lineDataValue.push(dataSeries3);
	
	getLineReportForm(sourceTimes, dataLegend, lineDataValue,"main");
}

function loadSourceBottomTable(){
	$("#bottomTable tr:not(:first)").remove();
	var bottomTableHtml = "";
	bottomTableHtml += "<tr><td>1</td>";
	bottomTableHtml += "<td>外部链接</td>";
	bottomTableHtml += "<td>" + waibuDataForPie.countPv + "</td>";
	bottomTableHtml += "<td>" + waibuDataForPie.countUv +"</td>";
	bottomTableHtml += "<td>" + waibuDataForPie.countIp +"</td>";
	bottomTableHtml += "</tr>";
	bottomTableHtml += "<tr><td>2</td>";
	bottomTableHtml += "<td>直接访问</td>";
	bottomTableHtml += "<td>" + directDataForPie.countPv + "</td>";
	bottomTableHtml += "<td>" + directDataForPie.countUv +"</td>";
	bottomTableHtml += "<td>" + directDataForPie.countIp +"</td>";
	bottomTableHtml += "</tr>";
	bottomTableHtml += "<tr><td>3</td>";
	bottomTableHtml += "<td>搜索引擎</td>";
	bottomTableHtml += "<td>" + searchDataForPie.countPv + "</td>";
	bottomTableHtml += "<td>" + searchDataForPie.countUv +"</td>";
	bottomTableHtml += "<td>" + searchDataForPie.countIp +"</td>";
	bottomTableHtml += "</tr>";
	bottomTableHtml += "<tr><td>&nbsp;</td>";
	bottomTableHtml += "<td>汇总</td>";
	bottomTableHtml += "<td>" + allData.countPv + "</td>";
	bottomTableHtml += "<td>" + allData.countUv +"</td>";
	bottomTableHtml += "<td>" + allData.countIp +"</td>";
	bottomTableHtml += "</tr>";
	$("#bottomTable").append(bottomTableHtml);
}


function loadSourceDataR(){
	var id = null;
	for(var i=0;i<3;i++){
		if($("#a"+i).attr('class')=='cur'){
			id = i;
			break;
		}	
	}
	loadSourcePieDataValue();
	loadSourceLineData(id);
}

/************外部链接*******************/
var waibuActionUrl = [
                          "/weibo-studio/report/getWaiBuDataSource.action"
                          ];

var waiBuUrls = new Array();
var waiBuAllUrls = new Array();
var waiBuTimes = new Array();
function waiBuInit(){
	waiBuUrls = new Array();
	waiBuAllUrls = new Array();
	waiBuTimes = new Array();
}

function loadWaiBuSourceData(id){
	waiBuInit();
	if(id!="countTime"){
		$("[id^=a]").removeClass();
		$("#a"+id).attr('class','cur');
		$("#countTime").val('');
		if(id==0){
			type = 1;
		}else if(id==1){
			type = 7;
		}else{
			type = 30;
		}
		$.ajax({
			type: "POST",
			url: waibuActionUrl[0],
			async: false,
			data:{'type':type},
			success: function(msg){
				waiBuUrls = msg.urls;
				waiBuAllUrls = msg.allurls;
				var all = msg.all;
				waiBuTimes = msg.times;
				
				loadWaiBuSourceTopTable(all);
				loadWaiBuSourceBottomTable(all);
				loadWaiBuSourcePie();
				loadWaiBuSourceLine();
			}
		});
	}else if($("#countTime").val()!=null&&$("#countTime").val()!=""){
		$("[id^=a]").removeClass();
		var type = $("#countTime").val().replace(new RegExp("-","gm"),"");
		$.ajax({
			type: "POST",
			url: waibuActionUrl[0],
			async: false,
			data:{'type':type},
			success: function(msg){
				waiBuUrls = msg.urls;
				waiBuAllUrls = msg.allurls;
				var all = msg.all;
				waiBuTimes = msg.times;
				
				loadWaiBuSourceTopTable(all);
				loadWaiBuSourceBottomTable(all);
				loadWaiBuSourcePie();
				loadWaiBuSourceLine();
			}
		});
	}
}

function loadWaiBuSourcePie(){
	$("#mainPie").html('');
	var pieValue = new Array();
	var dataName = new Array();
	var radioType = $('input[name="choose"]:checked').val();
	
	
	for(var i=0;i<waiBuAllUrls.length;i++){
		dataName.push(waiBuAllUrls[i].pageUrl);
		var itemPie = null;
		if(radioType=="countPv"){
			itemPie = new pieData(waiBuAllUrls[i].pageUrl,waiBuAllUrls[i].countPv);
		}else if(radioType=="countVisitNum"){
			itemPie = new pieData(waiBuAllUrls[i].pageUrl,waiBuAllUrls[i].countVisitNum);
		}else if(radioType=="countUv"){
			itemPie = new pieData(waiBuAllUrls[i].pageUrl,waiBuAllUrls[i].countUv);
		}else if(radioType=="countNewUv"){
			itemPie = new pieData(waiBuAllUrls[i].pageUrl,waiBuAllUrls[i].countNewUv);
		}else if(radioType=="countIp"){
			itemPie = new pieData(waiBuAllUrls[i].pageUrl,waiBuAllUrls[i].countIp);
		}else if(radioType=="tranNum"){
			itemPie = new pieData(waiBuAllUrls[i].pageUrl,waiBuAllUrls[i].tranNum);
		}
		pieValue.push(itemPie);
	}
	getPieReportForm(dataName,pieValue,"mainPie");
}

function loadWaiBuSourceLine(){
	$("#main").html('');
	var dataLegend = new Array();
	var lineDataValue = new Array();
	var radioType = $('input[name="choose"]:checked').val();
	
	for(var i=0;i<waiBuUrls.length;i++){
		var dataArray = new Array();
		dataLegend.push(waiBuUrls[i][0].pageUrl);
		for(var j=0;j<waiBuUrls[i].length;j++){
			if(radioType=="countPv"){
				dataArray.push(waiBuUrls[i][j].countPv);
			}else if(radioType=="countVisitNum"){
				dataArray.push(waiBuUrls[i][j].countVisitNum);
			}else if(radioType=="countUv"){
				dataArray.push(waiBuUrls[i][j].countUv);
			}else if(radioType=="countNewUv"){
				dataArray.push(waiBuUrls[i][j].countNewUv);
			}else if(radioType=="countIp"){
				dataArray.push(waiBuUrls[i][j].countIp);
			}else if(radioType=="tranNum"){
				dataArray.push(waiBuUrls[i][j].tranNum);
			}
		}
		var itemLine = new lineData(waiBuUrls[i][0].pageUrl, 'line', dataArray);
		lineDataValue.push(itemLine);
	}
	getLineReportForm(waiBuTimes, dataLegend, lineDataValue,"main");
}

function loadWaiBuSourceTopTable(all){
	$("#topTable tr:not(:first)").remove();
	var topTableHtml = "";
	topTableHtml += "<tr><td>" + all.countPv +"</td>";
	topTableHtml += "<td>" + all.countUv + "</td>";
	topTableHtml += "<td>" + all.countIp + "</td>";
	topTableHtml += "<td>" + all.countVisitNum + "</td>";
	topTableHtml += "<td>" + all.countNewUv + "</td>";
	topTableHtml += "<td>" + all.tranNum + "</td></tr>";
	$("#topTable").append(topTableHtml);
}

function loadWaiBuSourceBottomTable(all){
	$("#bottomTable tr:not(:first)").remove();
	var bottomTableHtml = "";
	for(var i=0;i<waiBuAllUrls.length;i++){
		bottomTableHtml += "<tr><td>" + (i+1) +"</td>";
		bottomTableHtml += "<td>" + waiBuAllUrls[i].pageUrl +"</td>";
		bottomTableHtml += "<td>" + waiBuAllUrls[i].countPv +"</td>";
		bottomTableHtml += "<td>" + waiBuAllUrls[i].countUv + "</td>";
		bottomTableHtml += "<td>" + waiBuAllUrls[i].countIp + "</td></tr>";
	}
	bottomTableHtml +="<tr><td>&nbsp;</td>";
	bottomTableHtml +="<td>汇总</td>";
	bottomTableHtml += "<td>" + all.countPv +"</td>";
	bottomTableHtml += "<td>" + all.countUv + "</td>";
	bottomTableHtml += "<td>" + all.countIp + "</td></tr>";
	
	$("#bottomTable").append(bottomTableHtml);
}

function loadWaiBuSourceDataR(){
	loadWaiBuSourcePie();
	loadWaiBuSourceLine();
}
/************页面分析*******************/
var pageActionUrl = [
                       "/weibo-studio/report/getDayDataPage.action",
                       "/weibo-studio/report/getSomedayDataPage.action" 
                       ];

var pageTotalData = null;
var pageUrlData = null;

function pageInit(){
	pageTotalData = null;
	pageUrlData = null;
}

function getPageDayData(id){
	pageInit();
	if(id!="countTime"){
		$("[id^=a]").removeClass();
		$("#a"+id).attr('class','cur');
		$("#countTime").val('');
		if(id==0){
			$.ajax({
				type: "POST",
				url: pageActionUrl[0],
				async: false,
				success: function(msg){
					pageTotalData = msg.all;
					pageUrlData = msg.urls;
					loadPageTopTable();
					loadPageBottomTable();
				}
			});
		}else{
			var type = id==1?7:30;
			$.ajax({
				type: "POST",
				url: pageActionUrl[1],
				async: false,
				data:{'type':type},
				success: function(msg){
					pageTotalData = msg.all;
					pageUrlData = msg.urls;
					loadPageTopTable();
					loadPageBottomTable();
				}
			});
		}
	}else if($("#countTime").val()!=null&&$("#countTime").val()!=""){
		$("[id^=a]").removeClass();
		var type = $("#countTime").val().replace(new RegExp("-","gm"),"");
		$.ajax({
			type: "POST",
			url: pageActionUrl[0],
			async: false,
			data:{'type':type},
			success: function(msg){
				pageTotalData = msg.all;
				pageUrlData = msg.urls;
				loadPageTopTable();
				loadPageBottomTable();
			}
		});
	}
	
	
}

function loadPageTopTable(){
	$("#topTable tr:not(:first)").remove();
	var topTableHtml = "";
	topTableHtml += "<tr><td>" + pageTotalData.countPv +"</td>";
	topTableHtml += "<td>" + pageTotalData.countUv + "</td>";
	topTableHtml += "<td>" + pageTotalData.startUrlNum + "</td>";
	topTableHtml += "<td>" + pageTotalData.endUrlNum + "</td>";
	topTableHtml += "<td>" + tranTime(pageTotalData.avgVisitTime) + "</td></tr>";
	$("#topTable").append(topTableHtml);
}

function loadPageBottomTable(){
	$("#bottomTable tr:not(:first)").remove();
	var bottomTableHtml = "";
	for(var i=0;i<pageUrlData.length;i++){
		bottomTableHtml += "<tr><td>" + (i+1) +"</td>";
		bottomTableHtml += "<td>" + pageUrlData[i].pageUrl +"</td>";
		bottomTableHtml += "<td>" + pageUrlData[i].countPv +"</td>";
		bottomTableHtml += "<td>" + pageUrlData[i].startUrlNum + "</td>";
		bottomTableHtml += "<td>" + pageUrlData[i].endUrlNum + "</td>";
		bottomTableHtml += "<td>" + tranTime(pageUrlData[i].avgVisitTime) + "</td></tr>";
	}
	bottomTableHtml +="<tr><td>&nbsp;</td>";
	bottomTableHtml +="<td>汇总</td>";
	bottomTableHtml += "<td>" + pageTotalData.countPv +"</td>";
	bottomTableHtml += "<td>" + pageTotalData.startUrlNum + "</td>";
	bottomTableHtml += "<td>" + pageTotalData.endUrlNum + "</td>";
	bottomTableHtml += "<td>" + tranTime(pageTotalData.avgVisitTime) + "</td></tr>";
	$("#bottomTable").append(bottomTableHtml);
}

/**************入口页面******************/
var startPageActionUrl = [
                          "/weibo-studio/report/getStartDataPage.action"
                          ];

var startPageUrls = new Array();
var startPageAllUrls = new Array();
var startPageTimes = new Array();
function startPageInit(){
	startPageUrls = new Array();
	startPageAllUrls = new Array();
	startPageTimes = new Array();
}

function loadStartPageData(id){
	startPageInit();
	if(id!="countTime"){
		$("[id^=a]").removeClass();
		$("#a"+id).attr('class','cur');
		$("#countTime").val('');
		if(id==0){
			type = 1;
		}else if(id==1){
			type = 7;
		}else{
			type = 30;
		}
		$.ajax({
			type: "POST",
			url: startPageActionUrl[0],
			async: false,
			data:{'type':type},
			success: function(msg){
				startPageUrls = msg.urls;
				startPageAllUrls = msg.allurls;
				var all = msg.all;
				startPageTimes = msg.times;
				
				loadStartPageTopTable(all);
				loadStartPageBottomTable(all);
				loadStartPagePie();
				loadStartPageLine();
			}
		});
	}else if($("#countTime").val()!=null&&$("#countTime").val()!=""){
		$("[id^=a]").removeClass();
		var type = $("#countTime").val().replace(new RegExp("-","gm"),"");
		$.ajax({
			type: "POST",
			url: startPageActionUrl[0],
			async: false,
			data:{'type':type},
			success: function(msg){
				startPageUrls = msg.urls;
				startPageAllUrls = msg.allurls;
				var all = msg.all;
				startPageTimes = msg.times;
				
				loadStartPageTopTable(all);
				loadStartPageBottomTable(all);
				loadStartPagePie();
				loadStartPageLine();
			}
		});
	}
	
	
}

function loadStartPagePie(){
	$("#mainPie").html('');
	var pieValue = new Array();
	var dataName = new Array();
	var radioType = $('input[name="choose"]:checked').val();
	
	
	for(var i=0;i<startPageAllUrls.length;i++){
		dataName.push(startPageAllUrls[i].pageUrl);
		var itemPie = null;
		if(radioType=="countVisitNum"){
			itemPie = new pieData(startPageAllUrls[i].pageUrl,startPageAllUrls[i].countVisitNum);
		}else if(radioType=="countUv"){
			itemPie = new pieData(startPageAllUrls[i].pageUrl,startPageAllUrls[i].countUv);
		}else if(radioType=="countNewUv"){
			itemPie = new pieData(startPageAllUrls[i].pageUrl,startPageAllUrls[i].countNewUv);
		}else if(radioType=="countIp"){
			itemPie = new pieData(startPageAllUrls[i].pageUrl,startPageAllUrls[i].countIp);
		}else if(radioType=="countJumpNum"){
			itemPie = new pieData(startPageAllUrls[i].pageUrl,startPageAllUrls[i].countJumpNum);
		}
		pieValue.push(itemPie);
	}
	getPieReportForm(dataName,pieValue,"mainPie");
}

function loadStartPageLine(){
	$("#main").html('');
	var dataLegend = new Array();
	var lineDataValue = new Array();
	var radioType = $('input[name="choose"]:checked').val();
	
	for(var i=0;i<startPageUrls.length;i++){
		var dataArray = new Array();
		dataLegend.push(startPageUrls[i][0].pageUrl);
		for(var j=0;j<startPageUrls[i].length;j++){
			if(radioType=="countVisitNum"){
				dataArray.push(startPageUrls[i][j].countVisitNum);
			}else if(radioType=="countUv"){
				dataArray.push(startPageUrls[i][j].countUv);
			}else if(radioType=="countNewUv"){
				dataArray.push(startPageUrls[i][j].countNewUv);
			}else if(radioType=="countIp"){
				dataArray.push(startPageUrls[i][j].countIp);
			}else if(radioType=="countJumpNum"){
				dataArray.push(startPageUrls[i][j].countJumpNum);
			}
		}
		var itemLine = new lineData(startPageUrls[i][0].pageUrl, 'line', dataArray);
		lineDataValue.push(itemLine);
	}
	
	getLineReportForm(startPageTimes, dataLegend, lineDataValue,"main");
	
	
}


function loadStartPageTopTable(all){
	$("#topTable tr:not(:first)").remove();
	var topTableHtml = "";
	topTableHtml += "<tr><td>" + all.countVisitNum +"</td>";
	topTableHtml += "<td>" + all.countUv + "</td>";
	topTableHtml += "<td>" + all.countNewUv + "</td>";
	topTableHtml += "<td>" + all.countIp + "</td>";
	topTableHtml += "<td>" + all.countJumpNum + "</td></tr>";
	$("#topTable").append(topTableHtml);
}

function loadStartPageBottomTable(all){
	$("#bottomTable tr:not(:first)").remove();
	var bottomTableHtml = "";
	for(var i=0;i<startPageAllUrls.length;i++){
		bottomTableHtml += "<tr><td>" + (i+1) +"</td>";
		bottomTableHtml += "<td>" + startPageAllUrls[i].pageUrl +"</td>";
		bottomTableHtml += "<td>" + startPageAllUrls[i].countVisitNum +"</td>";
		bottomTableHtml += "<td>" + startPageAllUrls[i].countUv + "</td>";
		bottomTableHtml += "<td>" + startPageAllUrls[i].countNewUv + "</td>";
		bottomTableHtml += "<td>" + startPageAllUrls[i].countIp + "</td>";
		bottomTableHtml += "<td>" + startPageAllUrls[i].countJumpNum + "</td></tr>";
	}
	bottomTableHtml +="<tr><td>&nbsp;</td>";
	bottomTableHtml +="<td>汇总</td>";
	bottomTableHtml += "<td>" + all.countVisitNum +"</td>";
	bottomTableHtml += "<td>" + all.countUv + "</td>";
	bottomTableHtml += "<td>" + all.countNewUv + "</td>";
	bottomTableHtml += "<td>" + all.countIp + "</td>";
	bottomTableHtml += "<td>" + all.countJumpNum + "</td></tr>";
	$("#bottomTable").append(bottomTableHtml);
}

function loadStartPageDataR(){
	loadStartPagePie();
	loadStartPageLine();
}



/**************访客分析******************/
var visitorActionUrl = [
                     "/weibo-studio/report/getTypeDataVisitor.action",
                     "/weibo-studio/report/getVisitDepthVisitor.action"
                     ];

function getVisitorData(id){
	if(id!='countTime'){
		$("[id^=a]").removeClass();
		$("#a"+id).attr('class','cur');
		$("#countTime").val("");
		if(id==0){
			type = 1;
		}else if(id==1){
			type = 7;
		}else{
			type = 30;
		}
		postVisitorData(type);
	}else if($("#countTime").val()!=null&&$("#countTime").val()!=""){
		$("[id^=a]").removeClass();
		type = $("#countTime").val().replace(new RegExp("-","gm"),"");
		postVisitorData(type);
	}
}
	
function postVisitorData(type){
	$.ajax({
		type: "POST",
		url: visitorActionUrl[0],
		async: false,
		data:{'type':type},
		success: function(msg){
			var newVisitorNum = msg.newNum;
			var oldVisitorNum = msg.oldNum;
			var dataName = ['新访客','老访客'];
			itemNew = new pieData('新访客',newVisitorNum);
			itemOld = new pieData('老访客',oldVisitorNum);
			var visitorPieDataValue = new Array();
			visitorPieDataValue.push(itemNew);
			visitorPieDataValue.push(itemOld);
			getPieReportForm(dataName,visitorPieDataValue,"mainPie");
		}
	});
	$.ajax({
		type: "POST",
		url: visitorActionUrl[1],
		async: false,
		data:{'type':type},
		success: function(msg){
			var depthList = msg.depthList;
			var dataName = new Array();
			var visitorPieDataValue = new Array();
			var bottomTableHtml = "";
			for(var i=0;i<depthList.length;i++){
				itemDepth = new pieData(depthList[i].visitDepth,depthList[i].visitNum); 
				dataName.push(depthList[i].visitDepth);
				visitorPieDataValue.push(itemDepth);
				
				bottomTableHtml += "<tr><td>" + (i+1) + "</td>";
				bottomTableHtml += "<td>" + depthList[i].visitDepth  +"</td>";
				bottomTableHtml += "<td>" + depthList[i].visitNum  +"</td>";
			}
			getPieReportForm(dataName,visitorPieDataValue,"mainPie1");
			$("#bottomTable tr:not(:first)").remove();
			$("#bottomTable").append(bottomTableHtml);
		}
	});
}

/*******************事件跟踪********************/
var eventActionUrl = [
                      "/weibo-studio/report/getDataEvent.action"
                      ];
var events = new Array();
var allEvents = new Array();
var eventTimes = new Array();

function eventInit(){
	events = new Array();
	allEvents = new Array();
	eventTimes = new Array();
}

function loadEventData(id){
	eventInit();
	if(id!="countTime"){
		$("[id^=a]").removeClass();
		$("#a"+id).attr('class','cur');
		$("#countTime").val('');
		if(id==0){
			type = 1;
		}else if(id==1){
			type = 7;
		}else{
			type = 30;
		}
		$.ajax({
			type: "POST",
			url: eventActionUrl[0],
			async: false,
			data:{'type':type},
			success: function(msg){
				events = msg.urls;
				allEvents = msg.allurls;
				var all = msg.all;
				eventTimes = msg.times;
				
				loadEventTopTable(all);
				loadEventBottomTable(all);
				loadEventLine();
			}
		});
	}else if($("#countTime").val()!=null&&$("#countTime").val()!=""){
		$("[id^=a]").removeClass();
		var type = $("#countTime").val().replace(new RegExp("-","gm"),"");
		$.ajax({
			type: "POST",
			url: eventActionUrl[0],
			async: false,
			data:{'type':type},
			success: function(msg){
				events = msg.urls;
				allEvents = msg.allurls;
				var all = msg.all;
				eventTimes = msg.times;
				
				loadEventTopTable(all);
				loadEventBottomTable(all);
				loadEventLine();
			}
		});
	}
}

function loadEventLine(){
	$("#main").html('');
	var dataLegend = new Array();
	var lineDataValue = new Array();
	var radioType = $('input[name="choose"]:checked').val();
	
	for(var i=0;i<events.length;i++){
		var dataArray = new Array();
		dataLegend.push(events[i][0].eventDesc);
		for(var j=0;j<events[i].length;j++){
			if(radioType=="eventNum"){
				dataArray.push(events[i][j].eventNum);
			}else if(radioType=="eventVisitorNum"){
				dataArray.push(events[i][j].eventVisitorNum);
			}
		}
		var itemLine = new lineData(events[i][0].eventDesc, 'line', dataArray);
		lineDataValue.push(itemLine);
	}
	getLineReportForm(eventTimes, dataLegend, lineDataValue,"main");
}

function loadEventTopTable(all){
	$("#topTable tr:not(:first)").remove();
	var topTableHtml = "";
	topTableHtml += "<tr><td>" + all.eventNum +"</td>";
	topTableHtml += "<td>" + all.eventVisitorNum + "</td></tr>";
	$("#topTable").append(topTableHtml);
}

function loadEventBottomTable(all){
	$("#bottomTable tr:not(:first)").remove();
	var bottomTableHtml = "";
	for(var i=0;i<allEvents.length;i++){
		bottomTableHtml += "<tr><td>" + (i+1) +"</td>";
		bottomTableHtml += "<td>" + allEvents[i].eventDesc +"</td>";
		bottomTableHtml += "<td>" + allEvents[i].eventNum +"</td>";
		bottomTableHtml += "<td>" + allEvents[i].eventVisitorNum + "</td></tr>";
	}
	bottomTableHtml +="<tr><td>&nbsp;</td>";
	bottomTableHtml +="<td>汇总</td>";
	bottomTableHtml += "<td>" + all.eventNum +"</td>";
	bottomTableHtml += "<td>" + all.eventVisitorNum + "</td></tr>";
	$("#bottomTable").append(bottomTableHtml);
}

function loadEventDataR(){
	loadEventLine();
}
	
/****************自定义变量********************/
var customVariableActionUrl = [
                            "/weibo-studio/report/getDistinctKeyCustomVariable.action",
                            "/weibo-studio/report/getDataCustomVariable.action"
                            ];

function getCustomVariableKey(){
	$.ajax({
		type: "POST",
		url: customVariableActionUrl[0],
		async: false,
		success: function(msg){
			var visit = msg.visit;
			var page = msg.page;
			var visitHtml = "";
			if(visit.length>0){
				for(var i=0;i<visit.length;i++){
					visitHtml += "<input type='radio' value='"+visit[i]+"' id='visit' name='chooseKey' onclick='chooseCustomVariableValue()'>"+visit[i];
				}
			}else{
				visitHtml += "无访问级自定义变量";
			}
			var pageHtml = "";
			if(page.length>0){
				for(var i=0;i<page.length;i++){
					pageHtml += "<input type='radio' value='"+page[i]+"' id='page' name='chooseKey' onclick='chooseCustomVariableValue()'>"+page[i];
				}
			}else{
				pageHtml += "无页面级自定义变量";
			}
			$("#visitVariable").html(visitHtml);
			$("#pageVariable").html(pageHtml);
			
			var radioList = $('input[name="chooseKey"]');
			if(radioList.length>0){
				radioList[0].checked = true;
			}
		}
	});
}

var customVariables = new Array();
var allCustomVariables = new Array();
var customVariablesTimes = new Array();

function customVariableInit(){
	customVariables = new Array();
	allCustomVariables = new Array();
	customVariablesTimes = new Array();
}

function chooseCustomVariableValue(){
	customVariableInit();
	var type;
	var id = 0;
	var flag = false;
	for(var i=0;i<3;i++){
		if($("#a"+i).attr('class')=='cur'){
			id = i;
			flag = true;
			break;
		}
	}
	if(!flag){
		type = $("#countTime").val().replace(new RegExp("-","gm"),"");
	}else{
		if(id==0){
			type = 1;
		}else if(id==1){
			type = 7;
		}else{
			type = 30;
		}
	}
	var chooseKey = $('input[name="chooseKey"]:checked');
	var variableKey = chooseKey.val();
	var variableType = chooseKey.attr('id');
	$.ajax({
		type: "POST",
		url: customVariableActionUrl[1],
		async: false,
		data:{'type':type,'variableKey':encodeURI(variableKey),'variableType':variableType},
		success: function(msg){
			customVariables = msg.urls;
			allCustomVariables = msg.allurls;
			var all = msg.all;
			customVariablesTimes = msg.times;
			
			loadCustomVariableTopTable(all);
			loadCustomVariableBottomTable(all);
			loadCustomVariableLine();
		}
	});
}


function loadCustomVariableData(id){
	customVariableInit();
	var chooseKey = $('input[name="chooseKey"]:checked');
	var variableKey = chooseKey.val();
	var variableType = chooseKey.attr('id');
	if(variableKey!=null&&variableKey!=""){
		if(id!="countTime"){
			$("[id^=a]").removeClass();
			$("#a"+id).attr('class','cur');
			$("#countTime").val('');
			if(id==0){
				type = 1;
			}else if(id==1){
				type = 7;
			}else{
				type = 30;
			}
			$.ajax({
				type: "POST",
				url: customVariableActionUrl[1],
				async: false,
				data:{'type':type,'variableKey':encodeURI(variableKey),'variableType':variableType},
				success: function(msg){
					customVariables = msg.urls;
					allCustomVariables = msg.allurls;
					var all = msg.all;
					customVariablesTimes = msg.times;
					
					loadCustomVariableTopTable(all);
					loadCustomVariableBottomTable(all);
					loadCustomVariableLine();
				}
			});
		}else if($("#countTime").val()!=null&&$("#countTime").val()!=""){
			$("[id^=a]").removeClass();
			var type = $("#countTime").val().replace(new RegExp("-","gm"),"");
			$.ajax({
				type: "POST",
				url: customVariableActionUrl[1],
				async: false,
				data:{'type':type,'variableKey':encodeURI(variableKey),'variableType':variableType},
				success: function(msg){
					customVariables = msg.urls;
					allCustomVariables = msg.allurls;
					var all = msg.all;
					customVariablesTimes = msg.times;
					
					loadCustomVariableTopTable(all);
					loadCustomVariableBottomTable(all);
					loadCustomVariableLine();
				}
			});
		}
	}
}

function loadCustomVariableLine(){
	$("#main").html('');
	var dataLegend = new Array();
	var lineDataValue = new Array();
	var radioType = $('input[name="choose"]:checked').val();
	
	for(var i=0;i<customVariables.length;i++){
		var dataArray = new Array();
		dataLegend.push(customVariables[i][0].variableValue);
		for(var j=0;j<customVariables[i].length;j++){
			if(radioType=="countPv"){
				dataArray.push(customVariables[i][j].countPv);
			}else if(radioType=="countVisitNum"){
				dataArray.push(customVariables[i][j].countVisitNum);
			}else if(radioType=="countUv"){
				dataArray.push(customVariables[i][j].countUv);
			}else if(radioType=="countNewUv"){
				dataArray.push(customVariables[i][j].countNewUv);
			}else if(radioType=="avgVisitPageNum"){
				dataArray.push(customVariables[i][j].avgVisitPageNum);
			}else if(radioType=="avgVisitTime"){
				dataArray.push(customVariables[i][j].avgVisitTime);
			}else if(radioType=="jumpRate"){
				dataArray.push(customVariables[i][j].jumpRate);
			}
		}
		var itemLine = new lineData(customVariables[i][0].variableValue, 'line', dataArray);
		lineDataValue.push(itemLine);
	}
	getLineReportForm(customVariablesTimes, dataLegend, lineDataValue,"main");
}

function loadCustomVariableTopTable(all){
	$("#topTable tr:not(:first)").remove();
	var topTableHtml = "";
	topTableHtml += "<tr><td>" + all.variableKey +"</td>";
	topTableHtml += "<td>" + all.countPv + "</td>";
	topTableHtml += "<td>" + all.countVisitNum + "</td>";
	topTableHtml += "<td>" + all.countUv + "</td>";
	topTableHtml += "<td>" + all.countNewUv + "</td>";
	topTableHtml += "<td>" + tranZeroToBlank(all.avgVisitPageNum) + "</td>";
	topTableHtml += "<td>" + tranTime(all.avgVisitTime) + "</td>";
	topTableHtml += "<td>" + tranRate(all.jumpRate) + "</td></tr>";
	$("#topTable").append(topTableHtml);
}

function loadCustomVariableBottomTable(all){
	$("#bottomTable tr:not(:first)").remove();
	var bottomTableHtml = "";
	for(var i=0;i<allCustomVariables.length;i++){
		bottomTableHtml += "<tr><td>" + (i+1) +"</td>";
		bottomTableHtml += "<td>" + allCustomVariables[i].variableKey +"</td>";
		bottomTableHtml += "<td>" + allCustomVariables[i].variableValue +"</td>";
		bottomTableHtml += "<td>" + allCustomVariables[i].countPv + "</td>";
		bottomTableHtml += "<td>" + allCustomVariables[i].countVisitNum + "</td>";
		bottomTableHtml += "<td>" + allCustomVariables[i].countUv + "</td>";
		bottomTableHtml += "<td>" + allCustomVariables[i].countNewUv + "</td>";
		bottomTableHtml += "<td>" + tranZeroToBlank(allCustomVariables[i].avgVisitPageNum) + "</td>";
		bottomTableHtml += "<td>" + tranTime(allCustomVariables[i].avgVisitTime) + "</td>";
		bottomTableHtml += "<td>" + allCustomVariables[i].jumpRate + "</td>";
	}
	bottomTableHtml +="<tr><td>&nbsp;</td>";
	bottomTableHtml +="<td>"+all.variableKey+"</td>";
	bottomTableHtml +="<td>汇总</td>";
	bottomTableHtml += "<td>" + all.countPv + "</td>";
	bottomTableHtml += "<td>" + all.countVisitNum + "</td>";
	bottomTableHtml += "<td>" + all.countUv + "</td>";
	bottomTableHtml += "<td>" + all.countNewUv + "</td>";
	bottomTableHtml += "<td>" + tranZeroToBlank(all.avgVisitPageNum) + "</td>";
	bottomTableHtml += "<td>" + tranTime(all.avgVisitTime) + "</td>";
	bottomTableHtml += "<td>" + tranRate(all.jumpRate) + "</td></tr>";
	$("#bottomTable").append(bottomTableHtml);
}

function loadCustomVariableDataR(){
	loadCustomVariableLine();
}
