<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<html>
<head>
	<script type="text/javascript" src="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/js/date.js"></script>
	<script type="text/javascript" src="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/js/accountInfo/accountManagement.js"></script>
	<script type="text/javascript">
		
		function createOrderCode(){
			var chars = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
					'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
					'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' ];
			var val = "";
			for ( var i = 0; i < 16; i++) {
				var v = Math.random() * 10;
				var g = Math.floor(v);
				val += chars[g];
			}
			$("#outOrderId").val(val);
		}
		
		function doSubmit(){
			var amount = $("#amount").val();
			var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		    if(!exp.test(amount)){
		    	alert('请填写正确的金额！');
		    	return;
		    }
		    createOrderCode();
		    $("#transAmount").val(amount);
		    $("#payType").val($("input[name='interfaceType']:checked").val());
		    var day = new Date();
			var Year = 0;
			var Month = 0;
			var Day = 0;
			var CurrentDate = "";
			Year = day.getFullYear();
			Month = day.getMonth() + 1;
			Day = day.getDate();
			h = day.getHours();
			m = day.getMinutes();
			s = day.getSeconds();
			CurrentDate += Year;
			if (Month >= 10) {
				CurrentDate += Month;
			} else {
				CurrentDate += "0" + Month;
			}
			if (Day >= 10) {
				CurrentDate += Day;
			} else {
				CurrentDate += "0" + Day;
			}
			if (h >= 10) {
				CurrentDate += h;
			} else {
				CurrentDate += "0" + h;
			}
			if (m >= 10) {
				CurrentDate += m;
			} else {
				CurrentDate += "0" + m;
			}
			if (s >= 10) {
				CurrentDate += s;
			} else {
				CurrentDate += "0" + s;
			}
			$("#submitTime").val(CurrentDate);
			
		    $.ajax({
				type : "POST",
				url : "rechargeSign.action",
				data : $("#tradeForm").serialize(),
				dataType: "json",
				async: false,
				success : function(data) {
					
					if(data.status == 'FAILED'){
						alert(data.msg);
						return;
					}
					
					$("#sign").val(data.sign);
					$("#partner").val(data.partner);
					
					$("#tradeForm").attr("action", data.payUrl);
					$("#tradeForm").submit();
				}
			});
			
		}
	</script>
</head>
<body>
	<div class="pop_tit"><h2>账户充值</h2></div>
	<form>
		<table class="global_table" align="center" cellpadding="0" cellspacing="1">
			<tr>
				<th>金额</th>
				<td>
					<input id="amount" class="" name="amount" type="text"/>
				</td>
			</tr>
			<!-- <tr>
				<th>支付方式</th>
				<td>
					<input id="interfaceType" class="" name="interfaceType" checked="checked" type="radio" value="WXNATIVE"/>微信支付
					<input id="interfaceType" class="" name="interfaceType" type="radio" value="AUTHPAY"/>快捷支付
				</td>
			</tr> -->
			<tr>
				<th></th>
				<td>
					<input type="button" class="global_btn" id="submitApply" value="提 交" onclick="doSubmit();"/>
				</td>
			</tr>
		</table>
	</form>
	<form id="tradeForm" method="post" target="_blank">
			<input type="hidden" name="apiCode" value="YL-PAY" />
			<input type="hidden" name="inputCharset" id="inputCharset" value="UTF-8" />
			<input type="hidden" id="partner" name="partner" value="${sessionScope.auth.customerno}"/>
			<input id="outOrderId" type="hidden" name="outOrderId"/>
			<input type="hidden" name="amount" id="transAmount"/>
			<input type="hidden" name="currency" value="CNY" />
			<input type="hidden" name="paymentType" value="ALL"/>
			<input type="hidden" name="retryFalg" value="TRUE" />
			<input type="hidden" id="submitTime" name="submitTime" />
			<input type="hidden" id="timeout" name="timeout" value="1D"/>
			<input type="hidden" name="productName" value="充值"/>
			<input type="hidden" name="payType" id="payType"/>
			<input type="hidden" name="bankCardNo"/>
			<input type="hidden" name="accMode" value="INTERFACE" /><br/>
			<input type="hidden" name="wxNativeType" value="PAGE" />
			<input type="hidden" name="signType" value="MD5" />
			<input type="hidden" id="sign" name="sign" />
		</form>
</body>
</html>