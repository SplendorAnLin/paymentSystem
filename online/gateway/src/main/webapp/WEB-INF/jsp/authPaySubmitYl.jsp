<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body onload="sub()">
	<div style="display: none;">
		<input type="hidden" id="urlStr" value="${auth_result.pay_url}" />
		<form id="ylForm" action="${auth_result.pay_url}" method="post">
		<input name="pay_amount" value="${auth_result.pay_amount}" />
		<input name="pay_applydate" value="${auth_result.pay_applydate}" />
		<input name="pay_bankcode" value="${auth_result.pay_bankcode}" />
		<input name="pay_callbackurl" value="${auth_result.pay_callbackurl}" />
		<input name="pay_memberid" value="${auth_result.pay_memberid}" />
		<input name="pay_notifyurl" value="${auth_result.pay_notifyurl}" />
		<input name="pay_orderid" value="${auth_result.pay_orderid}" />
		<input name="wxreturntype" value="${auth_result.wxreturntype}" />
		<input name="tongdao" value="${auth_result.tongdao}" />
 		<input name="pay_productname" value="${auth_result.pay_productname}" />
		<input name="pay_md5sign" value="${auth_result.pay_md5sign}" />
		<input name="pay_reserved3" value="${auth_result.pay_reserved3}" />
		<input name="description" value="${auth_result.description}" />
		<input name="card_no" value="${auth_result.card_no}" />
		<%-- <input name="interfaceInfoCode" value="${auth_result.interfaceInfoCode}" />
		<input name="interfaceRequestID" value="${auth_result.interfaceRequestID}" /> --%>
		</form>
	</div>
</body>
<script type="text/javascript">
	function sub() {
		document.forms[0].submit();
	}
</script>
</html>
