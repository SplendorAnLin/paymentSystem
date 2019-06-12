<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>	
	<script type="text/javascript">
	
		function init(){
			var accountType = $("[name = 'requestBean.accountType']").find("option:selected").val();
			var cerType = $("[name = 'requestBean.cerType']").find("option:selected").val();
			var cardType = $("[name = 'requestBean.cardType']").find("option:selected").val();
			
			if(accountType == 'OPEN'){
				$("#cardType").attr("disabled",true);
			}
			
			if(cerType == 'NAME'){
				$("#cerNo").attr("disabled",true);
			}
			
			if(cardType != 'CREDIT'){
				$("#validity").attr("disabled",true);
				$("#cvv").attr("disabled",true);
			}
		}
		
		function accountTypeChange(){
			var accountType = $("[name = 'requestBean.accountType']").find("option:selected").val();
			if(accountType == 'OPEN'){
				$("#cardType").val("");
				$("#cardType").attr("disabled",true);
			}else{
				$("#cardType").attr("disabled",false);
			}
		}
		
		function cerTypeChange(){
			var cerType = $("[name = 'requestBean.cerType']").find("option:selected").val();
			if(cerType == 'NAME'){
				$("#cerNo").val("");
				$("#cerNo").attr("disabled",true);
			}else{
				$("#cerNo").attr("disabled",false);
			}
		}
		
		function cardTypeChange(){
			var cardType = $("[name = 'requestBean.cardType']").find("option:selected").val();
			if(cardType != 'CREDIT'){
				$("#validity").val("");
				$("#cvv").val("");
				$("#validity").attr("disabled",true);
				$("#cvv").attr("disabled",true);
			}else{
				$("#validity").attr("disabled",false);
				$("#cvv").attr("disabled",false);
			}
		}
		
		function doSubmit(){
			var accountName = $("#accountName").val().trim();
			var accountNo = $("#accountNo").val().trim();
			var amount = $("#amount").val().trim();
			var accountType = $("[name = 'requestBean.accountType']").find("option:selected").val();
			var bankCode = $("[name = 'requestBean.bankCode']").find("option:selected").val();
			var cerType = $("[name = 'requestBean.cerType']").find("option:selected").val();
			var cerNo = $("#cerNo").val().trim();
			var cardType = $("[name = 'requestBean.cardType']").find("option:selected").val();
			var validity = $("#validity").val().trim();
			var cvv = $("#cvv").val().trim();
			
			if(accountName == ''){
				alert('账户名称不能为空，请重新输入！'); 
			    return false; 
			}
			
			if(accountNo == ''){
				alert('账户编号不能为空，请重新输入！'); 
			    return false; 
			}
			
			if(amount == ''){
				alert('金额不能为空，请重新输入！'); 
			    return false;
			}
			
			if(isNaN(amount)){
				alert('金额输入有误，请重新输入！'); 
			    return false;
			}
			
			if(accountType == 'OPEN'){
				if(bankCode == ''){
					alert('对公账户必须选择银行，请重新输入！'); 
				    return false;
				}
			}
			
			if(cerType != 'NAME'){
				if(cerNo == ''){
					alert('非账户名称认证必须填写认证号码，请重新输入！'); 
				    return false;
				}
			}
			
			if(cardType == 'CREDIT'){
				if(validity == ''){
					alert('信用卡必须输入有效期，请重新输入！'); 
				    return false;
				}
				if(cvv == ''){
					alert('信用卡必须输入cvv安全码，请重新输入！'); 
				    return false;
				}
			}
			
			update();
			
		}
		
		function update(){
			$.ajax({
				url : "reRequest.action",
				type : "POST",
				//dataType : "json",
				async:false,
				data : $("#formID").serialize(),
				success : function(data) {
					if(data == 'SUCCESS'){
						dialogMsg("代付成功!");
					}else if(data == 'SYSERR'){
						dialogMsg("系统异常!");
					}else{
						dialogMsg("代付失败!");
					}
				}
			});
		}
		

		/**alert提示框**/
		function dialogMsg(info) {
			var fn=function(){
				window.parent.myrefresh();
			};
			alertDialog("代付审核",info,fn);
		}
		
		function alertDialog(title,info,refresh) {
			$("#alertInfo").html(info);
			$("#alertInfoDiv").dialog({
				title : title,
				resizable : false,
				height : 250,
				width : 300,
				modal : true,
				position : [ "center", "center"],
				close : function() { //关闭后事件
					//unloading();
					$(this).dialog("destroy");
					if(refresh!=null){
						refresh();
					}
				},
				open:function (){
					var length= title.length;
					$("#alertInfoDiv input").blur();
				}
			});

		}
		function gobackButton(){
			$("#alertInfoDiv").dialog("close");
			//unloading();
		}
		
	</script>
</head>
<body onload="init();">
		
		<div class="detail_tit"><h2>修改代付订单</h2></div>
		
			<br><br><br>
		<form method="post" id="formID">
			<input type="hidden" value="${requestBean.flowNo}" name="requestBean.flowNo"/>
			<table class="global_table" cellpadding="0" cellspacing="0">
				<tr>
					<th width="20%">收款人姓名：</th>
					<td width="30%">
						<input type="text" id= "accountName" name="requestBean.accountName" value="${requestBean.accountName}" class="inp" />
					</td>
					<th width="20%">收款账号：</th>
					<td width="30%">
						<input type="text" id= "accountNo" name="requestBean.accountNo" value="${requestBean.accountNo}" class="inp" />
					</td>
				</tr>
				<tr>
					<th width="20%">金额：</th>
					<td width="30%">
						<input type="text" id= "amount" name="requestBean.amount" value="${requestBean.amount}" class="inp" 
						onKeyPress="if((event.keyCode<48 || event.keyCode>57) && event.keyCode!=46 || /\.\d\d$/.test(value))event.returnValue=false" />
					</td>
					<th>账户类型：</th>
					<td>
						<select onchange="accountTypeChange();" name="requestBean.accountType" style="width: 150px;">
							<option value="OPEN" <c:if test="${requestBean.accountType eq 'OPEN'}">selected="selected"</c:if>>对公</option>
							<option value="INDIVIDUAL"<c:if test="${requestBean.accountType eq 'INDIVIDUAL'}">selected="selected"</c:if>>对私</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>银行编码：</th>
					<td>
						<select name="requestBean.bankCode" style="width: 150px;">
							<option></option>
							<option value="ICBC" <c:if test="${requestBean.bankCode eq 'ICBC'}">selected="selected"</c:if>>工商银行</option>
							<option value="ABC" <c:if test="${requestBean.bankCode eq 'ABC'}">selected="selected"</c:if>>农业银行</option>
							<option value="BOC" <c:if test="${requestBean.bankCode eq 'BOC'}">selected="selected"</c:if>>中国银行</option>
							<option value="CCB" <c:if test="${requestBean.bankCode eq 'CCB'}">selected="selected"</c:if>>建设银行</option>
							<option value="BCM" <c:if test="${requestBean.bankCode eq 'BCM'}">selected="selected"</c:if>>交通银行</option>
							<option value="CITICB" <c:if test="${requestBean.bankCode eq 'CITICB'}">selected="selected"</c:if>>中信银行</option>
							<option value="CEB" <c:if test="${requestBean.bankCode eq 'CEB'}">selected="selected"</c:if>>工商银行</option>
							<option value="HXB" <c:if test="${requestBean.bankCode eq 'HXB'}">selected="selected"</c:if>>华夏银行</option>
							<option value="CMBC" <c:if test="${requestBean.bankCode eq 'CMBC'}">selected="selected"</c:if>>民生银行</option>
							<option value="CGB" <c:if test="${requestBean.bankCode eq 'CGB'}">selected="selected"</c:if>>工商银行</option>
							<option value="PAB" <c:if test="${requestBean.bankCode eq 'PAB'}">selected="selected"</c:if>>工商银行</option>
							<option value="CMB" <c:if test="${requestBean.bankCode eq 'CMB'}">selected="selected"</c:if>>工商银行</option>
							<option value="CIB" <c:if test="${requestBean.bankCode eq 'CIB'}">selected="selected"</c:if>>工商银行</option>
							<option value="SPDB" <c:if test="${requestBean.bankCode eq 'SPDB'}">selected="selected"</c:if>>工商银行</option>
							<option value="CBHB" <c:if test="${requestBean.bankCode eq 'CBHB'}">selected="selected"</c:if>>工商银行</option>
							<option value="PSBC" <c:if test="${requestBean.bankCode eq 'PSBC'}">selected="selected"</c:if>>工商银行</option>
							<option value="SHB" <c:if test="${requestBean.bankCode eq 'SHB'}">selected="selected"</c:if>>上海银行</option>
							<option value="BOB" <c:if test="${requestBean.bankCode eq 'BOB'}">selected="selected"</c:if>>北京银行</option>
						</select>
					</td>
					<th>卡类型：</th>
					<td>						
						<select id="cardType" onchange="cardTypeChange();" name="requestBean.cardType" style="width: 150px;">
							<option></option>
							<option value="DEBIT" <c:if test="${requestBean.cardType eq 'DEBIT'}">selected="selected"</c:if>>借记卡</option>
							<option value="CREDIT"<c:if test="${requestBean.cardType eq 'CREDIT'}">selected="selected"</c:if>>信用卡</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>有效期：</th>
					<td>						
						<input type="text" id= "validity" name="requestBean.validity" value="${requestBean.validity}" class="inp" />
					</td>
					<th>CVV：</th>
					<td>						
						<input type="text" id= "cvv" name="requestBean.cvv" value="${requestBean.cvv}" class="inp" />
					</td>
				</tr>
				<tr>
					<th>认证类型：</th>
					<td>
						<select name="requestBean.cerType" onchange="cerTypeChange();" style="width: 150px;">
							<option value="ID"<c:if test="${requestBean.cerType eq 'ID'}">selected="selected"</c:if>>身份证</option>
							<option value="NAME"<c:if test="${requestBean.cerType eq 'NAME'}">selected="selected"</c:if>>账户名称</option>
							<option value="PROTOCOL"<c:if test="${requestBean.cerType eq 'PROTOCOL'}">selected="selected"</c:if>>协议</option>
						</select>
					</td>
					<th>认证号码：</th>
					<td>
						<input type="text" id= "cerNo" name="requestBean.cerNo" value="${requestBean.cerNo}" class="inp" />
					</td>
				</tr>
				<tr>
					<th>修改原因：</th>
					<td>
						<input type="text" id="reason" name="requestBean.reason" value="" class="inp" />
					</td>
					<th></th>
					<td>
					</td>
				</tr>
			</table>
							
			<br>		
			<center>
				<input type="button" value="提交" class="global_btn" onclick="doSubmit();"/> 
			  	<input type="reset"  value="重 置" class="global_btn"/>
			<center>
			<br><br><br><br><br>
			<div>
  				<font color="blue">提示：1、提交后会修改订单，并且再次发起出款，慎重操作！</font></br>
			</div>
			
		</form>
	</div>

</body>
</html>
<div id="alertInfoDiv"
	style="display: none; text-align: center">
	<center>
		<div id="alertInfo"
			style="font-size: 13px;  margin-top: 60px;line-height: 25px"></div>
	</center>
	<center>
		<div  style="font-size: 13px; margin-top: 40px">
			<input type="button" value="返 回" class="global_btn"
				onclick="gobackButton()" />
		</div>
	</center>
</div>
