jQuery.extend(jQuery.validator.messages, {
	required: "必选字段",
	remote: "请修正该字段",
	email: "请输入正确格式的电子邮件",
	url: "请输入合法的网址",
	date: "请输入合法的日期",
	dateISO: "请输入合法的日期 (ISO).",
	number: "请输入合法的数字",
	digits: "只能输入正整数",
	creditcard: "请输入合法的信用卡号",
	equalTo: "请再次输入相同的值",
	accept: "请输入拥有合法后缀名的字符串",
	maxlength: jQuery.validator.format("请输入一个长度最多是 {0} 的字符串"),
	minlength: jQuery.validator.format("请输入一个长度最少是 {0} 的字符串"),
	rangelength: jQuery.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"),
	range: jQuery.validator.format("请输入一个介于 {0} 和 {1} 之间的值"),
	max: jQuery.validator.format("请输入一个最大为 {0} 的值"),
	min: jQuery.validator.format("请输入一个最小为 {0} 的值")
});

jQuery.validator.addMethod("validate_integer", function(value, element,param) {
    return this.optional(element) || /^[-+]?[1-9]\d*$|^0$/.test(value);   
}, "只能是正负整数"); 

jQuery.validator.addMethod("validate_digit", function(value, element,param) {
    return this.optional(element) || /^[0-9]*[1-9][0-9]*$/.test(value);   
}, "只能输入正整数"); 

jQuery.validator.addMethod("special", function(value, element,param) {//特殊字符
    return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);       
}, "只能包括中文字、英文字母、数字和下划线"); 
//class="{required:true,decimal:[17,2]}"
jQuery.validator.addMethod("decimal", function(value, element,param) {//小数
	var patt = new RegExp("^[-0-9]{1,"+param[0]+"}(\\.[0-9]{1,"+param[1]+"})?$","g");
    return this.optional(element) || patt.test(value);  
}, "整数位最大{0}位，小数最大{1}位"); 

jQuery.validator.addMethod("lessThan", function(value, element,param) {//小于
	function checkLessThan(value){
		try{
		var m = parseFloat(jQuery(param).val());
		return m>parseFloat(value);
		}catch(e){return false;};
	};
    return this.optional(element) || /^[.-0-9]*$/.test(value)||checkLessThan(value);  
}, "必须小于{0}"); 
//class="{required:true,lessThan:'#maoreThen'}"
jQuery.validator.addMethod("moreThan", function(value, element,param) {//大于
	function checkMoreThan(value){
		try{
		var l = parseFloat(jQuery(param).val());
		return l<parseFloat(value);
		}catch(e){return false;};
	};
    return this.optional(element) || /^[.-0-9]*$/.test(value)||checkMoreThan(value);  
}, "必须大于{0}"); 
//class="{required:true,dateFmt:'yyyy'}"
jQuery.validator.addMethod("dateFmt", function(value, element,param) {//日期格式
	function isValidDate(year, month, day) {
		year = parseInt(year, 10);
		month = parseInt(month, 10);
		day = parseInt(day, 10);
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (day < 1 || day > 30) {// alert( "日期在1 - 30之间" );
				return (false);
			}
		} else {
			if (month != 2) {
				if (day < 1 || day > 31) {// alert( "日期在1 - 31之间" );
					return (false);
				}
			} else { // month == 2
				if ((year % 100) != 0 && (year % 4 == 0) || (year % 100) == 0
						&& (year % 400) == 0) {
					if (day < 1 || day > 29) {// alert( "日期在1 - 29之间" );
						return (false);
					}
				} else {
					if (day < 1 || day > 28) {// alert( "日期在1 - 28之间" );
						return (false);
					}
				}
			}
		}
		
		return (year>=1900 && year<=2999)&&(month>=1 && month<=12)&&(true);
	}
	var year = '1900';
	var month = '01';
	var day = '01';
	var reg ;
	if(param=='yyyy'){
		year=value;
		reg=/^\d{4}$/;
	}else if(param=='yyyyMM'){
		year=value.substring(0,4);
		month = value.substring(4,6);
		reg=/^\d{6}$/;
	}else if(param=='yyyyMMdd'){
		year=value.substring(0,4);
		month = value.substring(4,6);
		day = value.substring(6,8);
		reg=/^\d{8}$/;
	}else{
		alert('fmt格式错误...');
	}
	//alert(year+":"+month+":"+day);
	//alert(reg.test(value)+":"+isValidDate(year,month,day));
    return this.optional(element) || reg.test(value)&&isValidDate(year,month,day);  
}, "日期输入错误，格式应该是{0}"); 

jQuery.validator.addMethod("int_range", function(value,element,param) {//excle文件
	//var str=param.substring(1,param.length-1);
	//var strs=str.split(",");
	//alert(param[0]+","+param[1]);
	return this.optional(element)||(value>=parseInt(param[0],10)&&value<=parseInt(param[1],10)&&/^[-+]?[1-9]\d*$|^0$/.test(value));
}, "必须是{0}到{1}一个整数");

jQuery.validator.addMethod("float_range", function(value,element,param) {//excle文件
	return this.optional(element)||(value>=parseInt(param[0],10)&&value<=parseInt(param[1],10)&&/^(-?\d+)(\.\d+)?$/.test(value)&&!/^[-+]?[1-9]\d*$|^0$/.test(value));
}, "必须是{0}到{1}一个浮点数");

jQuery.validator.addMethod("validate_file_xls", function(value, element,param) {//excle文件
    return this.optional(element) || /.+\.xls$/.test(value)||/.+\.xlsx$/.test(value);       
}, "请选择excel文件，后缀名称是.xls或者.xlsx");

jQuery.validator.addMethod("validate_file_zip", function(value, element,param) {//excle文件
    return this.optional(element) || /.+\.zip$/.test(value);       
}, "请选择zip文件，后缀名称是.zip");

jQuery.validator.addMethod("validate_ip", function(value, element,param) {//特殊字符
    return this.optional(element) || /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/.test(value);     
}, "只能输入有效的IP地址");

jQuery.validator.addMethod("validate_phone", function(value, element,param) {//特殊字符
    return this.optional(element) || /^((0[1-9]{3})?(0[12][0-9])?[-])?\d{6,8}$/.test(value);     
}, "只能输入有效的电话(仅适用于中国)");

jQuery.validator.addMethod("validate_mobile_phone", function(value, element,param) {//特殊字符
    return this.optional(element) || /^(13[0-9]|14[0-9]|15[0-9]|18[0-9])\d{8}$/.test(value);     
}, "只能输入有效的手机号码(13,14,15,18开头)");

jQuery.validator.addMethod("validateCh", function(value, element,param) {//特殊字符
    return this.optional(element) || /^[\u4e00-\u9fa5]+$/.test(value);     
}, "只能输入中文");

jQuery.validator.addMethod("validateEn", function(value, element,param) {//特殊字符
    return this.optional(element) || /^[a-zA-Z]+$/.test(value);     
}, "只能输入字母");

jQuery.validator.addMethod("validateEnNu", function(value, element,param) {//特殊字符
    return this.optional(element) || /^(\d|[a-zA-Z])+$/.test(value);     
}, "只能输入字母和数字");

jQuery.validator.addMethod("required_select", function(value, element,param) {//特殊字符
    return value.length>0 || /^全部$/.test($(element).find('option:selected').text()+'') ;    
}, "必填字段");

// 手机号码验证
jQuery.validator.addMethod("mobile", function(value, element) {
var length = value.length;
return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1}))+\d{8})$/.test(value));
}, "手机号码格式错误!");

// 电话号码验证
jQuery.validator.addMethod("phone", function(value, element) {
var tel = /^(\d{3,4}-?)?\d{7,9}$/g;
return this.optional(element) || (tel.test(value));
}, "电话号码格式错误!");