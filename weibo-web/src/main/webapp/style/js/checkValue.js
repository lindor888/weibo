//身份证号码校验,并从中拆分出出生年月日和性别
var yyyy;
var mm;
var dd;
var birthday;
var sex;
function getYear() {
	return yyyy;
}
function getMonth() {
	return mm;
}
function getDate() {
	return dd;
}
function getBirthday() {
	return birthday;
}
function getSex() {
	return sex;
}
function getAge() {
	var mm=getMonth();
	if(mm<10)
	mm=mm.substring(1,2);
	return Math.floor((parseInt(_getYear())*12+parseInt(_getMonth())-parseInt(getYear())*12-parseInt(mm))/12);
}
//判断是否大龄,男50,女40
function isBigAge() {
	if(parseInt(getAge())>=40&&parseInt(getSex())==2)
	return "1";
	if(parseInt(getAge())>=50&&parseInt(getSex())==1)
	return "1";
	return "0";
}
//校验身份证号码
function CheckIdCard(obj) {
	var id=obj.val();
	var objT = obj;
	var id_length=id.length;
	if(id_length==0) {
		alert("请输入身份证号码!");
		objT.focus();
		return false;
	}
	if(id_length!=15&&id_length!=18) {
		alert("身份证号长度应为15位或18位！");
		objT.focus();
		return false;
	}
	if(id_length==15) {
		yyyy="19"+id.substring(6,8);
		mm=id.substring(8,10);
		dd=id.substring(10,12);
		if(mm>12||mm<=0) {
			alert("输入身份证号,月份非法！");
			objT.focus();
			return false;
		}
		if(dd>31||dd<=0) {
			alert("输入身份证号,日期非法！");
			objT.focus();
			return false;
		}
		birthday=yyyy+"-"+mm+"-"+dd;
		if("13579".indexOf(id.substring(14,15))!=-1) {
			sex="1";
		}else {
			sex="2";
		}
	}else if(id_length==18) {
		if(id.indexOf("X")>0&&id.indexOf("X")!=17||id.indexOf("x")>0&&id.indexOf("x")!=17) {
			alert("身份证中\"X\"输入位置不正确！");
			objT.focus();
			return false;
		}
		yyyy=id.substring(6,10);
		if(yyyy>2200||yyyy<1900) {
			alert("输入身份证号,年度非法！");
			objT.focus();
			return false;
		}
		mm=id.substring(10,12);
		if(mm>12||mm<=0) {
			alert("输入身份证号,月份非法！");
			objT.focus();
			return false;
		}
		dd=id.substring(12,14);
		if(dd>31||dd<=0) {
			alert("输入身份证号,日期非法！");
			objT.focus();
			return false;
		}
		/*if(id.charAt(17)=="x"||id.charAt(17)=="X")
		{
			if("x"!=GetVerifyBit(id)&&"X"!=GetVerifyBit(id)) {
				alert("身份证校验错误，请检查最后一位！");
				objT.focus();
				return false;
			}
		}else {
			if(id.charAt(17)!=GetVerifyBit(id)) {
				alert("身份证校验错误，请检查最后一位！");
				objT.focus();
				return false;
			}
		}*/
		birthday=id.substring(6,10)+"-"+id.substring(10,12)+"-"+id.substring(12,14);
		if("13579".indexOf(id.substring(16,17))>-1) {
			sex="1";
		}else {
			sex="2";
		}
	}
	//转成大写字母
	obj.val(id.toUpperCase());
	return true;
}
//15位转18位中,计算校验位即最后一位
function GetVerifyBit(id) {
	var result;
	var nNum=eval(id.charAt(0)*7+id.charAt(1)*9+id.charAt(2)*10+id.charAt(3)*5+id.charAt(4)*8+id.charAt(5)*4+id.charAt(6)*2+id.charAt(7)*1+id.charAt(8)*6+id.charAt(9)*3+id.charAt(10)*7+id.charAt(11)*9+id.charAt(12)*10+id.charAt(13)*5+id.charAt(14)*8+id.charAt(15)*4+id.charAt(16)*2);
	nNum=nNum%11;
	switch(nNum) {
		case 0:
		result="1";
		break;
		case 1:
		result="0";
		break;
		case 2:
		result="X";
		break;
		case 3:
		result="9";
		break;
		case 4:
		result="8";
		break;
		case 5:
		result="7";
		break;
		case 6:
		result="6";
		break;
		case 7:
		result="5";
		break;
		case 8:
		result="4";
		break;
		case 9:
		result="3";
		break;
		case 10:
		result="2";
		break;
	}
	//document.write(result);
	return result;
}
//15位转18位
function Get18(idCard) {
	if(CheckValue(idCard)) {
		var id=idCard;
		var id18=id;
		if(id.length==0) {
			alert("请输入15位身份证号！");
			objT.focus();
			return false;
		}
		if(id.length==15) {
			if(id.substring(6,8)>20) {
				id18=id.substring(0,6)+"19"+id.substring(6,15);
			}else {
				id18=id.substring(0,6)+"20"+id.substring(6,15);
			}
			id18=id18+GetVerifyBit(id18);
		}
		return id18;
	}else {
		return false;
	}
}

//验证手机号码(obj:jquery对象)
function checkMobile(obj) {
	var mobileReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	if(!mobileReg.test(obj.val())) {
		alert("请输入正确的手机号码！");
		obj.focus();
		return false;
	}
	return true;
}
//验证固话(obj:jquery对象)
function checkPhone(obj) {
	var phoneReg = /^((0\d{2,3})-)(\d{7,8})$/;
	if(!phoneReg.test(obj.val())) {
		alert("请输入正确的座机号码！");
		obj.focus();
		return false;
	}
	return true;
}

//验证邮箱
function checkEmail(obj) {
	var emailReg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z]+$/;
	var email = obj.val();
	if(!emailReg.test(email)) {
		alert("请输入正确邮箱！");
		obj.focus();
		return false;
	}
	return true;
}

function checkUrl(name,obj) {
	var urlReg = /^(www|wap|[a-z]{1,}){1}\.([0-9a-z]){1,}\.(com|cn|net|org|com.cn){1}(\/[0-9a-z]{1,}(\.[a-z]{1,})*)*$/;
	var url = obj.val();
	if(!urlReg.test(url)) {
		//格式为www.ctvit.com.cn或sub.ctvit.com.cn或www.ctvit.com.cn/index.html或wap.ctvit.com
		alert(name+"格式不正确，请重新输入！");
		obj.focus();
		return false;
	}
	return true;
}

function checkIp(obj) {
	var ipReg = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.)(?:(?:25[0-5]|\*|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){2}(?:25[0-5]|2[0-4][0-9]|\*|[01]?[0-9][0-9]?)$/;
	var ip = obj.val();
	if(!ipReg.test(ip)) {
		alert("请输入正确的IP！");
		obj.focus();
		return false;
	}
	return true;
}

//验证特殊字符
function checkSpecialVal(name,obj) {
	/*
	if(obj.indexOf('<') > -1 || obj.indexOf('>') > -1 || obj.indexOf(' ') > -1 ||obj.indexOf('~') > -1 
		|| obj.indexOf('·') > -1 || obj.indexOf('!') > -1 || obj.indexOf('！') > -1 || obj.indexOf('(') > -1
		|| obj.indexOf(')') > -1 || obj.indexOf('（') > -1 || obj.indexOf('）') > -1
		|| obj.indexOf('@') > -1 || obj.indexOf('#') > -1 || obj.indexOf('$') > -1 || obj.indexOf('￥') > -1
		|| obj.indexOf('%') > -1 || obj.indexOf('……') > -1 || obj.indexOf('^') > -1 || obj.indexOf('&') > -1
		|| obj.indexOf('*') > -1 || obj.indexOf('-') > -1 || obj.indexOf('——') > -1 || obj.indexOf('_') > -1
		|| obj.indexOf('+') > -1 || obj.indexOf('=') > -1) {
		alert(name + "包含特殊字符，请重新输入！");
		return false;
	}
	*/

	var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$"; 
	var re = new RegExp(regu); 
	if (!re.test(obj)) { 
		alert(name+'只能输入汉字、英文字母、数字!');
		return false; 
	} 
	return true;
}

function checkDate(format) {
	var startDate = $("#startDate");
	var start = startDate.val();
	var endDate = $("#endDate");
	var end = endDate.val();
	var now = date2str(new Date(),format);
	
	if(start != undefined && start != "") {
		if(start > now) {
			alert("开始时间不能大于当前时间！");
			return false;
		} 
	}
	if(end != undefined && end != "") {
		if(end > now) {
			alert("结束时间不能大于当前时间！");
			return false;
		} 
	}
	if(start != undefined && start != "" && end != undefined && end != "") {
		if(start > end) {
			alert("开始时间不能大于结束时间！");
			return false;
		}
	}
	return true;
}

function checkDateTwo() {
	var startDate = $("#startDate");
	var start = startDate.val();
	var endDate = $("#endDate");
	var end = endDate.val();
	if(start != undefined && start != "" && end != undefined && end != "") {
		if(start > end) {
			alert("开始时间不能大于结束时间！");
			return false;
		}
	}
	return true;
}

function date2str(x,y) { 
	var z = {y:x.getFullYear(),M:x.getMonth()+1,d:x.getDate(),h:x.getHours(),m:x.getMinutes(),s:x.getSeconds()}; 
	return y.replace(/(y+|M+|d+|h+|m+|s+)/g,function(v) {return ((v.length>1?"0":"")+eval('z.'+v.slice(-1))).slice(-(v.length>2?v.length:2))}); 
}

function isNotNull(str) {
	return (str != undefined && str != null && str != '');
}
function isNull(str) {
	return (str == undefined || str == null || str == '');
}