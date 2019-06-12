/**
 *  校验手机号
 *  selector 选择器
 *  selectorType 选择器类型
 */
function checkMobile(selector,selectorType){
	var flag = true;
	if(selectorType == 'id'){
		if(!(/^1[3|4|5|8]\d{9}$/.test($("#"+selector).val()))){ 
    		flag = false;
    	} 
	}else if(selectorType == 'class'){
		$("."+selector).each(function() {
	    	if(!(/^1[3|4|5|8]\d{9}$/.test($(this).val()))){ 
	    		flag = false;
	    	} 
		});
	}
	return flag;
}

/**
 *  校验电话号码
 *  selector 选择器
 *  selectorType 选择器类型
 */
function checkPhone(selector,selectorType){
	var flag = true;
	var pattern =/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
	if(selectorType == 'id'){
		if(!(pattern.test($("#" +selector).val()))){ 
    		flag = false;
    	} 
	}else if(selectorType == 'class'){
		 $("." +selector).each(function() {
			 if(!pattern.test($(this).val())){
				flag = false;
			 } 
		 }); 
	}
	return flag;
}

/**
 *  校验邮箱
 *  selector 选择器
 *  selectorType 选择器类型
 */
function checkEmail(selector,selectorType){
	var flag = true;
	var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
	if(selectorType == 'id'){
		if(!(pattern.test($("#" +selector).val()))){ 
    		flag = false;
    	} 
	}else if(selectorType == 'class'){
		 $("." +selector).each(function(){
			 if(!pattern.test($(this).val()))    
			 {    
				 flag = false;    
			 }    
		 });
	}
	return flag;
}

/*
 * selector 选择器
 * name 属性名
 * url 
 * targetElement 目标元素(可选，不存放查询结果；有结果返回TRUE，无结果返回FALSE)
 * datasType 返回参数类型(可选，默认text方式)
 * sendType 请求类型(可选，默认POST方式)
 * sendDataType 请求参数类型(可选，默认text方式)
 * targetElementAttr 目标元素属性(可选，默认存value属性)
 * delimiter 多参数分隔符(可选，默认为 ",")
 * callbackFunction 回调函数(datasType为json时必传)
 */
function getInfo(selector,name,url,targetElement,datasType,sendType,sendDataType,targetElementAttr,delimiter,callbackFunction){
	// selector name url targetElement 校验
	if(selector == '' || selector == undefined || name == '' || name == undefined || url=='' || url == undefined)
		return false;
	
	
	if(datasType == '' || datasType == undefined)
		datasType = 'text';
	
	if(sendType == '' || sendType == undefined)
		sendType = 'post';
	
	if(sendDataType == '' || sendDataType == undefined)
		sendDataType = 'text';
	
	if(targetElementAttr == '' || targetElementAttr == undefined)
		targetElementAttr = 'value';
	
	if(delimiter == '' || delimiter == undefined)
		delimiter = ',';
	
	var tmpVal,datas,resulet;
	
	// 根据  selector 组装请求参数值
	$(""+selector).each(function(i){
		if(i==0){
			tmpVal = $(this).val();
		}else{
			tmpVal += ","+$(this).val();
		}
	});
	
	if(tmpVal !='' && tmpVal != undefined)
		// 请求方式为 json , 转json格式
		if(sendType == 'json'){
			var tmpJsonDatas = '{"' + name + '":"' + datas + '"}';
			datas = $.parseJSON(tmpJsonDatas);
		}else{
			datas = name + '=' + tmpVal;
		}

	$.ajax({
		type : sendType,
		async : false,
		data : datas,
		dataType : datasType,
		url : contextPath + url,
		success : function(resData) {
			
			if(resData == null || resData == 'FAIL')
				return false;
			
			resulet = resData;
		}
	});
	
	if(resulet != '' && resulet != undefined){
		if(callbackFunction != '' && callbackFunction != undefined){
			callbackFunction(resulet);
		}else{
			if(resulet.indexOf(delimiter)>-1){
				var resultDatas = resulet.split(delimiter);
				for(var i = 0; i < resultDatas.length; i++){
					var tmpVal = resultDatas[i];
					$(""+targetElement).each(function(s){
						if(i == s){
							if(targetElementAttr != '' && targetElementAttr != undefined){
								if(targetElementAttr.indexOf(",")>-1){
									var targetElementAttrs = targetElementAttrStr.split(delimiter);
									for(var m=0;m<targetElementAttrs.length;m++){
										if(targetElementAttrs[m]=='value'){
											$(this).val(tmpVal);
										}else if(targetElementAttrs[m]=='html'){
											$(this).html(tmpVal);
										}else if(targetElementAttrs[m]=='text'){
											$(this).text(tmpVal);
										}else{
											$(this).attr(targetElementAttrs[m],tmpVal);
										}
									}
								}else{
									if(targetElementAttr=='value'){
										$(this).val(tmpVal);
									}else if(targetElementAttr=='html'){
										$(this).html(tmpVal);
									}else if(targetElementAttr=='text'){
										$(this).text(tmpVal);
									}else{
										$(this).attr(targetElementAttr,tmpVal);
									}
								}
							}else{
								$(this).val(tmpVal);
							}
						}
					});
				}
			}else{
				$(""+targetElement).each(function(s){
					if(targetElementAttr != '' && targetElementAttr != undefined){
						if(targetElementAttr.indexOf(",")>-1){
							var targetElementAttrs = targetElementAttrStr.split(delimiter);
							for(var m=0;m<targetElementAttrs.length;m++){
								if(targetElementAttrs[m]=='value'){
									$(this).val(resulet);
								}else if(targetElementAttrs[m]=='html'){
									$(this).html(resulet);
								}else if(targetElementAttrs[m]=='text'){
									$(this).text(resulet);
								}else{
									$(this).attr(targetElementAttrs[m],resulet);
								}
							}
						}else{
							if(targetElementAttr=='value'){
								$(this).val(resulet);
							}else if(targetElementAttr=='html'){
								$(this).html(resulet);
							}else if(targetElementAttr=='text'){
								$(this).text(resulet);
							}else{
								$(this).attr(targetElementAttr,resulet);
							}
						}
					}else{
						$(this).val(resulet);
					}
				});
			}
		}
	}else{
		return false;
	}
	return true;
}

function showDialog(id){			
	var settings= { 
            message: $("#"+id), 
            css: {
            top:'5%',
            left:'18%',
            textAlign:'left',
            border:	'0px solid #f00',
            marginLeft:'0px',
            marginTop: '0px', 
            width: '800px',
            height:'560px', 
            background:'none',
            cursor:'wait'
        } 
    }
	$.blockUI(settings);
}

function showDialogFreedom(settings){		
    $.blockUI(settings);	
}

function closeDialog(){
	$.unblockUI();
}

function showImages(imageId, url){
	$("#" + imageId).attr("src" ,url);
}

function WinOpen(id) {
	var src = $("#"+id).attr("src");
	mesg=window.open("showimg.jsp","DisplayWindow");
	mesg.document.write("<HEAD><TITLE>图片预览</TITLE></HEAD>");
	mesg.document.write("<body><img src='" + src + "'></body>");
}

//鼠标滑轮改变图片大小
function bbimg(o){
	var zoom=parseInt(o.style.zoom, 10)||100;
	zoom+=event.wheelDelta/12;
	if(zoom>300){
		zoom=300;
	}
	if(zoom<50){
		zoom=50;
	}
	o.style.zoom=zoom+'%';
	return false;
}

//图片旋转
var s=0;
var si=0;
function rotateImg(img,i){
	if(i=='l'){
		s+=90;
		si+=1;
	}else{
		s-=90;
		si-=1;
	}
	s%=360;
	if(document.all){
		$("#"+img).css("filter","progid:DXImageTransform.Microsoft.BasicImage(rotation="+si+")");
	}else{
		$("#"+img).css("-webkit-transform","rotate("+s+"deg)");
		$("#"+img).css("-moz-transform","rotate("+s+"deg)");
		$("#"+img).css("-o-transform","rotate("+s+"deg)");
	}
}
/**
 * 校验银行卡号
 * @param cardNo 卡号
 * @return boolean 有效卡号：TRUE;无效卡号：FALSE
 */
function checkCard(cardNo){
	if(isNaN(cardNo))
		return false;
	
	var nums = cardNo.split("");
	var sum = 0;
	var index = 1;
	for(var i = 0 ; i < nums.length; i++){
		if((i+1)%2==0){
			var tmp = Number(nums[nums.length-index])*2;
			if(tmp >= 10){
				var t = tmp+"".split("");
				tmp = Number(t[0]) + Number(t[1]);
			}
			sum+=tmp;
		}else{
			sum+=Number(nums[nums.length-index]);
		}
		index ++;
	}
	if(sum%10 != 0){
		return false;
	}
	return true;
}