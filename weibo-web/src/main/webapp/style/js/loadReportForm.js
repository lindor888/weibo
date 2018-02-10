/**
 * 获得线形图
 */
function getLineReportForm(xArray,dataLegend,dataSeries,id){
	require.config({
        paths:{ 
            echarts:'/weibo-studio/style/js/echarts',
            'echarts/chart/bar' : '/weibo-studio/style/js/echarts',
            'echarts/chart/line': '/weibo-studio/style/js/echarts'
        }
    });
	
	require(
	        [
	            'echarts',
	            'echarts/chart/bar',
	            'echarts/chart/line'
	        ],
	        function(ec) {
	            var myChart = ec.init(document.getElementById(id));
	            var option = {
	                tooltip : {
	                    trigger: 'axis'
	                },
	                legend: {
	                    data:dataLegend
	                },
	                toolbox: {
	                    show : true,
	                    feature : {
	                        mark : true,
	                        dataView : {readOnly: false},
	                        magicType:['line', 'bar'],
	                        restore : true,
	                        saveAsImage : true
	                    }
	                },
	                calculable : true,
	                xAxis : [
	                    {
	                        type : 'category',
	                        data : xArray
	                    }
	                ],
	                yAxis : [
	                    {
	                        type : 'value',
	                        splitArea : {show : true}
	                    }
	                ],
	                series : dataSeries
	            };
	            myChart.setOption(option);
	        }
	    );
}

/**
 * 线形图对象
 * @param name
 * @param type
 * @param data
 * @returns
 */
function lineData(name,type,data){
	this.name = name;
	this.type = type;
	this.data = data;
}

/**
 * 获得饼形图
 */
function getPieReportForm(dataName,dataValue,id){
	require.config({
        paths:{ 
            echarts:'/weibo-studio/style/js/echarts',
            'echarts/chart/pie' : '/weibo-studio/style/js/echarts'
        }
    });
	require(
	        [
	            'echarts',
	            'echarts/chart/pie'
	        ],
	        function(ec) {
	            var myChart = ec.init(document.getElementById(id));
	            var option = {
	            	    title : {
	            	        text: '',
	            	        subtext: '',
	            	        x:'center'
	            	    },
	            	    tooltip : {
	            	        trigger: 'item',
	            	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	            	    },
	            	    legend: {
	            	        orient : 'vertical',
	            	        x : 'left',
	            	        data:dataName
	            	    },
	            	    toolbox: {
	            	        show : true,
	            	        feature : {
	            	            mark : true,
	            	            dataView : {readOnly: false},
	            	            restore : true,
	            	            saveAsImage : true
	            	        }
	            	    },
	            	    calculable : true,
	            	    series : [
	            	        {
	            	            name:'',
	            	            type:'pie',
	            	            radius : [0, 110],
	            	            center: [,225],
	            	            data:dataValue
	            	        }
	            	    ]
	            	};
	            myChart.setOption(option);
	        }
	    );
}

/**
 * 饼形图对象
 * @param name
 * @param value
 * @returns
 */
function pieData(name,value){
	this.name = name;
	this.value = value;
}


function tranNullToZero(value){
	if(value==null){
		value = 0;
	}
	return value;
}

function tranNullToBlank(value){
	if(value==null){
		value = "-";
	}
	return value;
}

function tranTime(time){
	if(time==null||time==""){
		return "-";
	}
	var minute = parseInt(time/60);
	var secend = parseInt(time%60);
	return minute+"分"+secend+"秒";
}

function tranRate(value){
	if(value==null){
		return "-";
	}else{
		return value*100 + "%";
	}
}

function tranZeroToBlank(value){
	if(value==0||value=="0"){
		value = "-";
	}
	return value;
}

$(function() {
	$("#main").mousemove(function(){
		var obj = $("#main div div");
		var len = obj.length;
		if(len>3){
			for(var i=2;i<(len-1);i++){
				obj[i].remove();
			}
		}
	});
	$("#mainPie").mousemove(function(){
		var obj = $("#mainPie div div");
		var len = obj.length;
		if(len>3){
			for(var i=2;i<(len-1);i++){
				obj[i].remove();
			}
		}
	});
	$("#mainPie1").mousemove(function(){
		var obj = $("#mainPie1 div div");
		var len = obj.length;
		if(len>3){
			for(var i=2;i<(len-1);i++){
				obj[i].remove();
			}
		}
	});
});
