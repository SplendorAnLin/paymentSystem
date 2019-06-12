<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/commons-include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<title>跳转中</title>
<script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/jquery-1.8.3.js"></script>
</head>
<body>
<script>
	$(function() {
		var day = new Date();
		var Year = 0;
		var Month = 0;
		var Day = 0;
		var CurrentDate = "";
		Year = day.getFullYear();//支持IE和火狐浏览器.
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
	});
	
	function getSign() {
		if ($("#key").attr("name") == '') {
			$("#key").attr("name", "key");
		}
		$.ajax({
			type : "POST",
			url : "MD5Test.htm",
			data : $("#form1").serialize(),
			success : function(da) {
				$("#sign").val(da);
				$("#key").attr("name", '');
				$("#form1").submit();
			}
		});

	}
	
</script>
<body>
	<form id="form1" action="pay.htm" style="margin-top: 1rem">
		<center>
		<table style="border: 1px solid #ddd;font-size: 0.8rem;border-radius: 0.4em;max-width: 33rem;width: 100%" cellspacing="10">
			<tr><td><b>请输入银行卡号:</b></td></tr>
			<tr><td><input placeholder="请输入银行卡号(单笔限额2W)" style="padding: 0 8px;border: 1px solid #d3d3d3;border-radius: 0.3em;height: 2.3rem;width: 100%" type="text" name="bankCardNo" value=""/></td></tr>
			<tr><td><input type="button" value="提交" onclick="getSign()" style="-webkit-appearance: none;font-size:14px;height: 2.3rem;width: 100%;margin-top: 0.5rem;border: 1px solid #03a9f4;background-color: white;color: #03a9f4;border-radius: 0.3em"/></td></tr>
		</table>
		</center>
		<input style='display:none' />
		<input type="hidden" name="apiCode" value="${partnerRequest.apiCode }"/>
		<input type="hidden" name="signType" value="${partnerRequest.signType }"/>
		<input type="hidden" name="submitTime" id="submitTime" />
		<input type="hidden" name="amount" value="${partnerRequest.amount }"/>
		<input type="hidden" name="inputCharset" value="${partnerRequest.inputCharset }"/>
		<input type="hidden" name="accMode" value="${partnerRequest.accMode }"/>
		<input type="hidden" name="payType" value="${partnerRequest.payType }"/>
		<input type="hidden" name="partner" value="${partnerRequest.partner }"/>
		<input type="hidden" name="notifyUrl" value="${partnerRequest.notifyUrl }"/>
		<input type="hidden" name="clientIP" value="${clientIP }"/>
		<input type="hidden" name="redirectUrl" value="${partnerRequest.pageNotifyUrl }"/>
		<input type="hidden" name="outOrderId" value="${partnerRequest.outOrderId }"/>
		<input type="hidden" name="interfaceCode" value="${partnerRequest.interfaceCode }"/>
		<input type="hidden" name="returnParam" value="${partnerRequest.returnParam }"/>
		<%-- <input type="hidden" name="product" value="${partnerRequest.product }"/> --%>
		<input type="hidden" name="sign" id="sign" value=""  size="100" />
		<input type="hidden" id="key" name="key" value="${key }" />
		<input type="hidden" name="retryFalg" value="TRUE">
		
	</form>
</body>
</body>
</html>