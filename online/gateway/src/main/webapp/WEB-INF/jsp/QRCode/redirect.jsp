<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta charset="UTF-8">
<title>跳转中...</title>
</head>
<body onload="sub()">
	<div style="display: none;">
		<form id="form1" action="${payUrl }" method="POST">
			接口编号：<input type="text" name="apiCode" value="YL-PAY" /> <br/>
			参数编码字符集：<input type="text" name="inputCharset" id="inputCharset" value="UTF-8" /> <br/>
			商户编号：<input type="text" id="partner" name="partner" value="${partner }" /> <br/>
			合作方唯一订单号：<input id="outOrderId" type="text" name="outOrderId" value="${outOrderId }" size="100" /> <br/>
			订单金额：<input type="text" name="amount" value="${amount }" /> <br/>
			币种：<input type="text" name="currency" value="CNY" /> <br/>
			支付方式：<input type="text" name="payType" value="${payType }"/> <br/>
			支付接口编号：<input type="text" name="interfaceCode" value=""/>AUTHPAY_YLZF-DEBIT-CARD <br/>
			同一订单是否可重复提交：<input type="text" name="retryFalg" value="TRUE" /> <br/>
			订单提交时间：<input type="text" id="submitTime" value="${submitTime }" name="submitTime" /> <br/>
			订单超时时间：<input type="text" id="timeout" name="timeout" value="1D"/> <br/>
			业务扩展参数：<input type="text" name="extParam" value='${extParam }'/> <br/>
			回传参数：<input type="text" name="returnParam" /> <br/>
			商品名称：<input type="text" name="productName" value="${productName }"/> <br/>
			支付结果页面回调URL：<input type="text" name="redirectUrl" value="${redirectUrl }" /> <br/>
			支付结果后台通知URL：<input type="text" name="notifyUrl" value="${notifyUrl }" /> <br/>
			支付方式：<input type="text" name="paymentType" value="" />ALL <br/>
			银行卡号：<input type="text" name="bankCardNo" value=""/> <br/>
			接入方式：<input type="text" name="accMode" value="GATEWAY" />INTERFACE|GATEWAY <br/>
			微信二维码获取方式：<input type="text" name="wxNativeType" value="PAGE" />PAGE|URL <br/>
			网银用户ip：<input type="text" name="clientIP" value="" /><br/>
			签名方式：<input type="text" name="signType" value="MD5" /> <br/>
			密钥：<input type="text" id="key" value="" />
			<input type="text" id="sign" value="${partnerString }" name="sign" size="100" /><br/>
			<input type="button" value="提交" onclick="sub()" />
		</form>
	</div>
</body>
<script type="text/javascript">
	function sub() {
		document.forms[0].submit();
	}
</script>
</html>