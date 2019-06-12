<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../include/commons-include.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>直连交易接口测试</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link href="./css/base.css" rel="stylesheet" type="text/css" />
<link href="./css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/butil.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/tabs.js"></script>
<script type="text/javascript">
	$(function() {
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
		$.ajax({
			type : "POST",
			url : "MD5Test.htm",
			data : $("#form1").serialize(),
			success : function(da) {
				$("#sign").val(da);
			}
		});

	}
	function sub() {
		$("#key").attr("name","");
		$("#form1").attr("action", $("#url").val());
		$("#form1").submit();
	}
</script>
</head>

<body>
	<div style="border: 1px solid red;padding: 0px;">
		<form id="form1" method="post">
			URL：<input id="url" type="text" size="100" value="http://127.0.0.1:8080/gateway/quick/pay.htm"/> <br/><!-- http://127.0.0.1:8080/gateway/pay.htm -->
			接口编号：<input type="text" name="apiCode" value="YL-PAY" /> <br/>
	                    参数编码字符集：<input type="text" name="inputCharset" id="inputCharset" value="UTF-8" /> <br/>
                              商户编号：<input type="text" id="partner" name="partner" value="C100000" /> <br/>
	                    合作方唯一订单号：<input id="outOrderId" type="text" name="outOrderId"   size="100" /> <br/>
	                    订单金额：<input type="text" name="amount" value="10" /> <br/>
	                    币种：<input type="text" name="currency" value="CNY" /> <br/>
	                    支付方式：<input type="text" name="payType" value="QUICKPAY"/> <br/><!-- AUTHPAY -->
	                    支付接口编号：<input type="text" name="interfaceCode" value=""/>AUTHPAY_YLZF-DEBIT-CARD <br/>
	                    同一订单是否可重复提交：<input type="text" name="retryFalg" value="TRUE" /> <br/>
	                    订单提交时间：<input type="text" id="submitTime" name="submitTime" /> <br/>
	                    订单超时时间：<input type="text" id="timeout" name="timeout" value="1D"/> <br/>
	                    业务扩展参数：<input type="text" name="extParam" /> <br/>
	                    回传参数：<input type="text" name="returnParam" /> <br/>
	                    商品名称：<input type="text" name="productName" value="充值"/> <br/>
	                    支付结果页面回调URL：<input type="text" name="redirectUrl" value="http://www.baidu.com/" /> <br/>
	                    支付结果后台通知URL：<input type="text" name="notifyUrl" value="http://www.baidu.com/" /> <br/>
	                    支付方式：<input type="text" name="paymentType" value="" />ALL <br/>
	                    银行卡号：<input type="text" name="bankCardNo" value="5220010103793289"/>6225768773991953-5220010103793289 <br/>
	                    接入方式：<input type="text" name="accMode" value="INTERFACE" />INTERFACE|GATEWAY <br/>
	                    微信二维码获取方式：<input type="text" name="wxNativeType" value="PAGE" />PAGE|URL <br/>
	                  <!--  结算类型：<input type="text" name="settleType" value="FEE" /><br/>
	                   代付费率：<input type="text" name="remitFee" value="1" /><br/>
	                   快捷支付费率：<input type="text" name="quickPayFee" value="0.003" /><br/>
	                  结算金额：<input type="text" name="settleAmount" value="9.5" /><br/> -->
	                    网银用户ip：<input type="text" name="clientIP" value="" /><br/>
	                    签名方式：<input type="text" name="signType" value="MD5" /> <br/>
	                    密钥：<input type="text" id="key" name="key" value="fb59c6d069f80c85bb535d727cada9ba" />
            <input type="text" id="sign" value="" name="sign" size="100" /><br/>
            <input type="button" onclick="getSign()" value="生成sign" /><input type="button" value="提交" onclick="sub()" />
		</form>
	</div>
</body>
</html>
