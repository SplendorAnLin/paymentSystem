<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<link href="${pageContext.request.contextPath}/css/df.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/df.js" ></script>


<style type="text/css">
span{
font-size: 12px
}
.tooltip div .span1{

	width: 280px;
}

</style>
<script type="text/javascript">

jQuery.ajaxSetup({cache:false});

//去审核按钮 继续发起按钮
function goAudit(goPage){
	if(goPage=='audit'){
			$("a[target=main][class='m2_active']",window.top.document).attr("class","");
			$("a[target=main][href*='toDfAuditQuery']",window.top.document).attr("class","m2_active");
		$("#toDfAuditQuery").submit();
	}else if(goPage='apply'){
		$("#toDfSingleApply").submit();
	}
}
function showInfoDialog(id,data,closefn){
	$("#"+id+" .data").html(data);
	var title="发起代付";
	$("#"+id+"").show().dialog({
		title : title,
		resizable : false,
		modal : true,
		width : 400,
		height : 260,
		position : [ "center", "center"],
		bgiframe : true,
		show: { effect: "blind", duration: 500 },
	    hide: { effect: "explode", duration: 500 },
	    close : function() { //关闭后事件
			$(this).dialog("destroy");
		},
		open:function (){
			var length= title.length;
			var str =194-6*length;
			$(".ui-dialog-title").css("padding","0 "+str+"px");
			$("#"+id+" input").blur();
		},
		beforeclose:function(){
			if(closefn!=null&&closefn!=undefined){
	    		closefn();
				$(this).dialog("destroy");
	    	}else {
	    		$(this).dialog("destroy");
	    	}
		}

	});

}

function alertDialog(title,info,refresh) {


	$("#alertInfo").html(info);
	$("#alertInfoDiv").dialog({
		title : title,
		resizable : false,
		height : 260,
		width : 400,
		modal : true,
		position : [ "center", "center"],
		close : function() { //关闭后事件
			$(this).dialog("destroy");
			unloading();
			if(refresh!=null){
				refresh();
			}
		},
		open:function (){
			var length= title.length;
			var str =194-6*length
			$(".ui-dialog-title").css("padding","0 "+str+"px");
			$("#alertInfoDiv input").blur();
		}
	});
}

	$().ready(function(){
		$("#accountName").focus();

		$("#blank").height($(document).height()-490);
		$("#blank").width(10);
	});

		var int="";
		function style() {
			var obj=$(".myclass");
			for (var i = 0; i < obj.length; i++) {
				var str = "0123456789abcdef";
				var t = "#";
				for (var j = 0; j < 6; j++) {
					t = t + str.charAt(Math.random() * str.length);
				}
				obj[i].style.color = t;
			}
		}
		function init(){
			int=self.setInterval("style()", 300);
		}
		$(function() {
			init();
			$("#cnapsNameWord").autocomplete(
					{
						source : function(request, response) {
							$.ajax({
								url : "toCachecenterCnaps.action",
								type : "POST",
								dataType : "json",
								data : "word="+encodeURI(encodeURI($("#cnapsNameWord").val()))+"&providerCode="+$("#bankCode").val()+"&clearBankLevel="+$("#sabkBankFlag").val(),
								success : function(data) {
									response($.map(data, function(item){
										return{
											label: item.name,
											value: item.name,
											code: item.code,
											sabkCode:item.clearingBankCode,
											bankCode:item.providerCode
										};
									}));
								}
							});
						},
						minLength : 0,
						select : function(event, ui) {
							var sabkCode = "";
							removeErrorMessage("openBankName");
							$.ajax({
								url : "toCachecenterCnaps.action",
								type : "POST",
								dataType : "json",
								async : false,
								data : "word=&providerCode="+ui.item.bankCode+"&clearBankLevel=1",
								success : function(data) {
									sabkCode = data[0].clearingBankCode;
								}
							});
							if(sabkCode != ''){
								$("#bankCode").val(ui.item.bankCode);
								$("#cnapsCode").val(ui.item.code);
								$("#cnapsName").val(ui.item.label);
								$("#sabkCode").val(sabkCode);
								$("#openBank").val(ui.item.label);
								removeErrorMessage("cnapsNameWord");
							}else{
								addErrorMessage("openBankName","获取开户行信息异常，请重试！");
							}
						},
						open : function() {
							$(this).removeClass("ui-corner-all").addClass(
									"ui-corner-top");
						},
						close : function() {
							$(this).removeClass("ui-corner-top").addClass(
									"ui-corner-all");
						}
					});
		});

		/**
		 * 校验银行卡号
		 * @param cardNo 卡号
		 * @return boolean 有效卡号：TRUE;无效卡号：FALSE
		 */
		function checkCard(cardNo){
			if(isNaN(cardNo))
				return false;
			if(cardNo.length < 12){
				return false;
			}
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

		//仅仅校验，不改变值
		 function checkAccountNoValidate(){

				if($("select[name='dfSingle.accountType']").find("option:selected").val() == 'OPEN'){
					return true;
				}
				//为空 在dovalidate中判断
				var cardNoStr = $("#accountNo").val().replace(/\s+/g,"");
				var ajaxFlag;
				if(checkCard(cardNoStr) == true){
					$.ajax({
						url : "toCachecenterIin.action",
						type : "POST",
						dataType : "json",
						async:false,
						data : "cardNo="+cardNoStr,
						success : function(data) {
							var iin = eval(data);
							if(iin.providerCode == null){
								addErrorMessage("accountNo","收款账号无效，请重新输入！");
								ajaxFlag= false;
							}else{
								ajaxFlag= true;
							}
						}
					});
				}else{
					addErrorMessage("accountNo","收款账号无效，请重新输入！");
					return false;
				}
				return ajaxFlag;
		 }
		function checkAccountNo(obj){
			var cardNoStr = obj.value.replace(/\s+/g,"");
			if(cardNoStr == ''){
				//addErrorMessage("accountNo","请输入收款账号。");
				return false;
			}
			if($("select[name='dfSingle.accountType']").find("option:selected").val() == 'OPEN'){
				$("#cnapsNameWord").attr("disabled","");
				//document.getElementById('cnapsNameWord').focus();
				$("#cnapsNameWord").val('');
				return false;
			}
			if(checkCard(cardNoStr) == true){
				$.ajax({
					url : "toCachecenterIin.action",
					type : "POST",
					dataType : "json",
					data : "cardNo="+cardNoStr,
					success : function(data) {
						var iin = eval(data);
						if(iin.providerCode == null){
							addErrorMessage("accountNo","收款账号无效，请重新输入！");
							cardNoStr = '';
							$("#cnapsNameWord").val('请输入地区、分行支行名称等关键词进行匹配');
							$("#cnapsNameWord").attr("disabled","disabled");
						}else{
							$("#bankCode").val(iin.providerCode);
							$("#cnapsNameWord").attr("disabled","");
							$("#cnapsNameWord").val('');
							//document.getElementById('cnapsNameWord').focus();
							//var WshShell = new ActiveXObject('WScript.Shell');
					        //WshShell.SendKeys('{A}');
						}
					}
				});
			}else{
				addErrorMessage("accountNo","收款账号无效，请重新输入！");
				$("#cnapsNameWord").val('请输入地区、分行支行名称等关键词进行匹配');
				cardNoStr = '';
				$("#cnapsNameWord").attr("disabled","disabled");
			}
			$("#sabkCode").val('');
			$("#cnapsCode").val('');
			$("#cnapsName").val('');
			$("#bankCode").val('');
		}


		/**重置表单**/
		function doReset(){

		}

		/**提交表单信息**/
		function doSubmit(){
			//清空默认提示
			$("#accountName,#amount,#accountNo,#cnapsNameWord,#submitApply").each(function(){
				removeErrorMessage($(this).attr("id"));
			});
			removeErrorMessage("accountName");

			var submitFlag=true;
			if(!doValidate()){
				focusaftererror("#accountName,#amount,#accountNo,#cnapsNameWord");
				return;
			}

			$("#cbs",window.parent.document).val("");
			var fn=function(){
				$("#queryForm",window.parent.document).submit();
			};

			var singleAmount = 0;

			var amount = $("#amount").val();
			$.ajax({
				url:"getOrderSingleAmount.action",
				type:"post",
				dataType:"json",
				async:false,	//同步
				success:function(data){
					var re=eval(data);
					singleAmount = re.msg;
				}
			});
			if(singleAmount != 'null' && Number(singleAmount) < Number(amount)){
				dialogNoMsg2("单笔限额为 "+parseInt(singleAmount)+"元",fn);
				submitFlag=false;
				return ;
			}
			loading()
			$("#bankCardNo").val($("#accountNo").val().replace(/\s+/g,""));
			var params=$("#singleForm").serialize();
			$.ajax({
				url:"dfSingleApply.action",
				type:"post",
				dataType:"json",
				data:params,
				success:function(data){
					unloading();
					var re=eval(data);
					var fn=function(){
						window.location.href="toDfSingleApply.action";
					};
					if(re.msg=='成功'){

						var dataStr ="共:"+amount+" 元,1 笔";
						showInfoDialog("noErrList",dataStr,function(){
											 goAudit('apply');
										 });
						//dialogMsg2("单笔代付发起成功。",fn);
					}else{
						dialogMsg2("单笔代付发起失败。",fn);
					}
				}
			});
		}

		/**alert提示框**/
		function dialogNoMsg2(info,fn) {
			alertDialog("提示信息",info,null);
		}

		/**表单验证**/
		function doValidate(){
			var flag=true;

			var reg1=/\S/;	//非空验证reg1.test(str)==false说明是空串或仅包含空白字符的串
			var reg2_1=/((^[1-9]\d*)|(^0))$/;	//自然数
			var reg2_2=/^[1-9]\d*\.\d+$/;		//正小数>=1
			var reg2_3=/^0\.\d+$/;				//正小数<1

			var dom_amount=$("#amount");
			var dom_accountName=$("#accountName");
			var dom_accountNo=$("#accountNo");

			var sabkCode=$("#sabkCode").val();
			var cnapsCode=$("#cnapsCode").val();
			var cnapsName=$("#cnapsName").val();

			var fn=function(){
				$("#queryForm",window.parent.document).submit();
			};
			if(!reg1.test(sabkCode) || !reg1.test(cnapsCode) || !reg1.test(cnapsName)){
				$("#cbs",window.parent.document).val("");
				addErrorMessage("cnapsNameWord","请输入开户行信息。");
				flag= false;
			}

			var accountName=dom_accountName.val();
			var accountNo=dom_accountNo.val();
			var amount=dom_amount.val();

			if(!checkAccountName()){
				flag=false;
			}
			if(!reg1.test(accountNo)){
				addErrorMessage("accountNo","请输入收款账号。");
				flag= false;
			}else{
				if(!checkAccountNoValidate()){
					flag=false;
				}
			}
			flag=checkAmount()&&flag;
			return flag;
		}

		/**alert提示框**/
		function dialogMsg2(info,fn) {
			alertDialog("提示信息",info,fn);
		}

		function dialogMsgFail(info,fn) {
			alertDialog("提示信息",info,fn);
		}



		function setCursorPosition(el, pos) {

        	el.focus();
        	if ( el.setSelectionRange )
        		el.setSelectionRange(pos, pos);
        		else if ( el.createTextRange )
        		{
        			range=el.createTextRange();
        			range.collapse(true);
        			range.moveEnd('character', pos);
        			range.moveStart('character', pos) ;
        			range.select();
        		}
        }

		function getCursorPosition(){
            var oTxt1 = document.getElementById("accountNo");
            var cursurPosition=-1;
            if(oTxt1.selectionStart){//非IE浏览器
                cursurPosition= oTxt1.selectionStart;
            }else{//IE
                var range = document.selection.createRange();
                range.moveStart("character",-oTxt1.value.length);
                cursurPosition=range.text.length;
            }
            return cursurPosition;
        }



		function bankChange(obj){
			if($("#cnapsName").val() != (obj.value).replace(" ","")){
				$("#sabkCode").val('');
				$("#cnapsCode").val('');
				$("#cnapsName").val('');
			}
		}





		function checkCnapsNameWord(){
			removeErrorMessage("cnapsNameWord");
			var reg1=/\S/;	//非空验证reg1.test(str)==false说明是空串或仅包含空白字符的串
			var sabkCode=$("#sabkCode").val();
			var cnapsCode=$("#cnapsCode").val();
			var cnapsName=$("#cnapsName").val();
			var cnapsNameWord =$("#cnapsNameWord").val()

			if($("#cnapsNameWord").val()!='' ){
				if(!reg1.test(sabkCode) || !reg1.test(cnapsCode) || !reg1.test(cnapsName)){
					addErrorMessage("cnapsNameWord","请输入开户行信息。");
				}
			}
		}

		function removeErrMsg(){
			removeErrorMessage('accountNo');
			document.getElementById('cnapsNameWord').disabled = false;
			removeErrorMessage('cnapsNameWord');
			document.getElementById('cnapsNameWord').disabled = true;
		}

	</script>
</head>

<body>
	<h2 class="myh2">单笔代付</h2>
	<form id="singleForm">
		<input type="hidden" name="openBank.sabkCode" id="sabkCode"/>
		<input type="hidden" name="openBank.cnapsCode" id="cnapsCode"/>
		<input type="hidden" name="openBank.cnapsName" id="cnapsName"/>
		<input type="hidden" name="openBank.bankCode" id="bankCode"/>
		<input type="hidden" name="openBank.openBankName" id="openBank"/>

		<input type="hidden" name="openBank.sabkName" id="sabkName"/>
		<input type="hidden" name="openBank.province" id="province"/>
		<input type="hidden" name="openBank.city" id="city"/>
		<input type="hidden" name="openBank.lefuAreaCode" id="lefuAreaCode"/>
		<input type="hidden" name="openBank.lefuProvinceCode" id="lefuProvinceCode"/>
		<input type="hidden" name="dfSingle.feeType" value="PAYER">
		<table class="df_table" cellpadding="0" cellspacing="0">

			<tr>
				<th><span class="star">*</span>收款账户类型：</th>
				<td>
					<select id="accountType" class="middle" style="width: 204px" name="dfSingle.accountType" onchange="changeFocus();">
						<option value="INDIVIDUAL">对私（个人）</option>
						<option value="OPEN">对公（企业）</option>
					</select>
				</td>
			</tr>
			<tr>
				<th width="13%"><span class="star">*</span>收款人姓名：</th>
				<td >
					<input  type="text" class="middle" name="dfSingle.accountName" id="accountName"  onfocus="removeErrorMessage('accountName')" onblur="accountNameBlur(this)"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="checkbox" type="checkbox" value="true" name="dfSingle.saveAccount"/><span class="hand">保存收款人信息</span>
					<label class="tooltip">
						    <div style="display:inline"><div class="img1"><img  src="image/info.png"  /></div><span class="span1"  ban="0">保存后，可在【代付收款人管理】中发起代付。</span></div>
					</label>
				</td>

			</tr>
			<tr>
				<th><span class="star">*</span>金额：</th>
				<td>
					<input class="middle" type="text" name="dfSingle.amount" id="amount" onblur="checkAmountBlur('#amount')" onfocus="removeErrorMessage('amount')"/> 元
				</td>
			</tr>
			<tr>
				<th ><span class="star">*</span>收款账号：</th>
				<td >
					<input class="big" style=" font-weight:bold;" type="text"  id="accountNo" onkeyup="formatNo(this,event);"  autocomplete="off" onblur="checkAccountNo(this);" onfocus="removeErrorMessage('accountNo');removeErrorMessage('cnapsNameWord');"/>
					<input type="hidden" name="dfSingle.accountNo" id="bankCardNo"/>
				</td>
			</tr>
			<tr>
				<th><span class="star">*</span>开户行信息：</th>
				<td >
					<input type="text" id="cnapsNameWord" value="请输入地区、分行支行名称等关键词进行匹配" onkeyup="bankChange(this);" onclick="removeDefaultValue(this)" onfocus="removeErrorMessage('cnapsNameWord')" onblur="checkCnapsNameWord()" class="big"  disabled="disabled">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input class="checkbox"  type="checkbox" onclick="changeFlag(this)" id="sabkBankFlag" value="0" ><span class="hand">总行</span>
					<label class="tooltip">
						    <div style="display:inline"><div class="img2"><img  src="image/info.png"  /></div><span class="span2"  ban="0">开户行信息不确定可勾选总行。为保障到账成功，请输入准确的开户行信息。</span></div>
					</label>
				</td>
			</tr>
			<tr>
				<th>打款原因：</th>
				<td>
					<input class="big" name="dfSingle.description"></input>
				</td>

			</tr>
			<tr>
				<th></th>
				<td>
					<input type="button" class="global_btn" id="submitApply" value="提 交" onclick="doSubmit();"/>
				</td>
			</tr>
		</table>
	</form>

	<script type="text/javascript">
		function selectFile(){
			$(".errorMsg1").html("");
			$("#filePath").html("上传信息大于200条请分批上传");
		}
		function checkfile(){
			selectFile();
		    var file=$("#excel").val();
		    var filename=file.replace(/.*(\/|\\)/, "");
		    var fileExt=(/[.]/.exec(filename)) ? /[^.]+$/.exec(filename.toLowerCase()) : '';
		    if(fileExt=='xls'||fileExt=='xlsx'){
		    	loading();
		    	$("#filePath").html(filename);
		    	setTimeout(function(){
		    		$("#uploadForm").submit();
		    	},10);

		    	return true;

		    }else{
		    	$(".errorMsg1").html("请上传模板格式文件。");
		    }
		    return true;

		}
		function download(){
			window.location.href = "${pageContext.request.contextPath}/jsp/template/收款人模版.xlsx";
		}

	</script>
	<hr>
	<h2 class="myh2">批量代付</h2>
	<div id="text"  ></div>
	<form action="dfPayeeUpload.action" id="uploadForm"  enctype="multipart/form-data" method="post">
		<table align="center" class="df_table"  cellpadding="0" cellspacing="0" style="margin-top: 0px">
			<tr >
				<th style="visibility: hidden;">
				</th>

				<td>
					<div class="file_global_btn">
						<input class="selectfile" type="file" name="excel" id="excel" onchange="checkfile()"  title="选择后，文件将直接上传。" style="width:80px;"/></div>

				<div id="filePath" class="filePath" >
					<%
						if(request.getAttribute("excelFileName")!=null){
					%> <%=request.getAttribute("excelFileName") %> <%
					}else
					{%> 上传信息大于200条请分批上传 <%}%>
				</div>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
					href="javascript:download();"><span style="vertical-align: bottom;">下载模板</span></a></td>
			</tr>
			<tr align="center">
				<th>
				</th>
				<td class="errorMsg1"  align="left" style="color: #F00;	font-size: 13px;height: 15px;padding-left: 95px" >


					<%
						if(request.getAttribute("errorMsg")!=null){
					%> <%=request.getAttribute("errorMsg") %> <%
					}%>

				</td>
			</tr>
		</table>
  	</form>
  	<hr>
  	<br/><br/>
  	<div style="font-size:13px; color:#999;  margin-left: 20px;">
		<ul>
			<li>交易提示：</li>
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、代付业务出款时间：当日16:00以前发起的代付当日出款，当日16:00以后发起的代付次日出款。</li>
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、在开户行输入框中，请输入地区、分行支行名称等关键词进行匹配。</li>
			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、如果未找到开户行，请勾选总行，在开户行输入框中输入银行名称等关键词 ，选中匹配中的银行。</li>
		</ul>
	</div>

	<div id="alertInfoDiv"
		style="display: none; margin: auto; text-align: center">
		<center>
			<div id="alertInfo"
				style="font-size: 13px;  margin-top: 60px"></div>
		</center>
		<center>
			<div id="alertInfo" style="font-size: 13px; margin-top: 40px">
				<input type="button" value="返 回" class="global_btn"
					onclick="$('#alertInfoDiv').dialog('close');" />
			</div>
		</center>
	</div>

	<div id="background" class="background" style="display: none; "></div>
 	<div id="progressBar" class="progressBar" style="display: none; ">数据加载中，请稍等...</div>

 	<div id="noErrList" style="display:none">
		<table style="width: 100% ;margin-top: 50px;font-size: 15px;">
		<tr ><td align="center" ><span style="line-height: 20px">代付订单发起成功</span></td></tr>
		<tr ><td align="center"><span class="data" style="line-height: 20px"></span></td></tr>
		</table>
		<center style="margin-top: 40px">
				<input type="button" class="global_btn" id="111" value="去 审 核" onclick="goAudit('audit');"/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="global_btn"  value="继续发起" onclick="goAudit('apply');"/>
		</center>
	</div>

	<form id="toDfAuditQuery" action="toDfAuditQuery.action?refresh=true" method="post">
		<input name="refresh" value="true" type="hidden">
	</form>
	<form id="toDfSingleApply" action="toDfSingleApply.action"></form>

	<div id="blank" ></div>
</body>