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

<title>网关交易接口测试</title>
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
<p style="font-size:200%">当前页面为测试页面，商户可根据实际情况选择性使用。</p>
<p style="font-size:200%; color:red">&nbsp;&nbsp;&nbsp;&nbsp;注意事项：</p>
<p style="font-size:150%">&nbsp;&nbsp;1.key字段为商户密钥  正式调用时无需上送，这里只作为测试使用。</p>
<p style="font-size:150%">&nbsp;&nbsp;2.本页面只提供测试之用，一切以文档为准！</p>
	<div>
        <br>
        <br>
		<form id="form1" method="post">
            <table border="1" cellspacing="1" style="width:45%;">
                <thead>
                    <tr>
                        <th style="width:15%">参数名</th>
                        <th style="width:40%">说明</th>
                        <th style="width:45%">测试值（表格内直接输入）</th>
                    </tr>
                    <tr>
                        <td>URL</td>
                        <td>公网地址（请求地址）</td>
                        <td><input id="url" type="text" size="100" style="width: 100%" value="http://pay.feiyijj.com/gateway/pay"/></td>
                    </tr>
                    <tr>
                        <td>apiCode</td>
                        <td>接口编号（默认：YL-PAY）</td>
                        <td><input type="text" name="apiCode" style="width: 100%" value="YL-PAY" /></td>
                    </tr>
                    <tr>
                        <td>inputCharset</td>
                        <td>参数编码字符集（只支持UTF-8）</td>
                        <td><input type="text" name="inputCharset" style="width: 100%" id="inputCharset" value="UTF-8" /></td>
                    </tr>
                    <tr>
                        <td>partner</td>
                        <td>商户编号</td>
                        <td><input type="text" style="width: 100%" id="partner" name="partner" value="C100009" /></td>
                    </tr>
                    <tr>
                        <td>outOrderId</td>
                        <td>合作方唯一订单号</td>
                        <td><input type="text" style="width: 100%" id="outOrderId" name="outOrderId" /></td>
                    </tr>
                    <tr>
                        <td>amount</td>
                        <td>订单金额（单位：元 小数点后保留两位）</td>
                        <td><input type="text" style="width: 100%" name="amount" value="100" /></td>
                    </tr>
                    <tr>
                        <td>currency</td>
                        <td>币种（只支持CNY：人民币）</td>
                        <td><input type="text" style="width: 100%" name="currency" value="CNY" /></td>
                    </tr>
                    <tr>
                        <td>payType</td>
                        <td>支付方式</td>
                        <td>
                            <select name="payType" style="width: 100%">
                                <option value="ALL">所有</option>
                                <option value="AUTHPAY">认证支付</option>
                                <option value="B2C">个人网银</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>interfaceCode</td>
                        <td>支付接口编号</td>
                        <td><input type="text" style="width: 100%" name="interfaceCode" value="" /></td>
                    </tr>
                    <tr>
                        <td>retryFalg</td>
                        <td>同一订单是否可重复提交</td>
                        <td><input type="text" style="width: 100%" name="retryFalg" value="TRUE" /></td>
                    </tr>
                    <tr>
                        <td>submitTime</td>
                        <td>订单提交时间（yyyyMMddHHmmss）</td>
                        <td><input type="text" style="width: 100%" id="submitTime" name="submitTime" /></td>
                    </tr>
                    <tr>
                        <td>timeout</td>
                        <td>订单超时时间（默认1D：一天）</td>
                        <td><input type="text" style="width: 100%" id="timeout" name="timeout" value="1D" /></td>
                    </tr>
                    <tr>
                        <td>extParam</td>
                        <td>业务扩展参数</td>
                        <td><input type="text" style="width: 100%" name="extParam" /></td>
                    </tr>
                    <tr>
                        <td>returnParam</td>
                        <td>回传参数</td>
                        <td><input type="text" style="width: 100%" name="returnParam" /></td>
                    </tr>
                    <tr>
                        <td>product</td>
                        <td>商品名称</td>
                        <td><input type="text" style="width: 100%" name="product" value="小李飞刀" /></td>
                    </tr>
                    <tr>
                        <td>redirectUrl</td>
                        <td>支付结果页面回调地址</td>
                        <td><input type="text" style="width: 100%" name="redirectUrl" value="https://www.baidu.com" /></td>
                    </tr>
                    <tr>
                        <td>notifyUrl</td>
                        <td>异步通知地址</td>
                        <td><input type="text" style="width: 100%" name="notifyUrl" value="https://www.baidu.com" /></td>
                    </tr>
                    <tr>
                        <td>bankCardNo</td>
                        <td>银行卡号</td>
                        <td><input type="text" style="width: 100%" name="bankCardNo" value="" /></td>
                    </tr>
                    <tr>
                        <td>accMode</td>
                        <td>接入方式</td>
                        <td>
                            <select name="accMode" style="width: 100%">
                                <option value="GATEWAY">网关</option>
                                <option value="INTERFACE">接口</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>clientIP</td>
                        <td>网银用户IP</td>
                        <td><input type="text" style="width: 100%" name="clientIP" value="" /></td>
                    </tr>
                    <tr>
                        <td>signType</td>
                        <td>签名方式（目前只支持MD5）</td>
                        <td><input type="text" style="width: 100%" name="signType" value="MD5" /></td>
                    </tr>
                    <tr>
                        <td>key</td>
                        <td>商户密钥</td>
                        <td><input type="text" style="width: 100%" id="key" name="key" value="9c6ed2f7f9e2d01c6c557f44b4edba6a" /></td>
                    </tr>
                    <tr>
                        <td>sign</td>
                        <td>签名摘要</td>
                        <td><input type="text" style="width: 100%" id="sign" readonly value="点击下方生成sign即可" name="sign" /></td>
                    </tr>
                </thead>
            </table>


			<%--URL：<input id="url" type="text" size="100" value="http://192.168.1.130:8080/gateway/quick/pay"/> <br/><!-- http://127.0.0.1:8080/gateway/pay.htm -->--%>
			<%--接口编号：<input type="text" name="apiCode" value="YL-PAY" /> <br/>--%>
	                    <%--参数编码字符集：<input type="text" name="inputCharset" id="inputCharset" value="UTF-8" /> <br/>--%>
                        <%--商户编号：<input type="text" id="partner" name="partner" value="C100000" /> <br/>--%>
	                    <%--合作方唯一订单号：<input id="outOrderId" type="text" name="outOrderId"   size="100" /> <br/>--%>
	                    <%--订单金额：<input type="text" name="amount" value="10" /> <br/>--%>
	                    <%--币种：<input type="text" name="currency" value="CNY" /> <br/>--%>
	                    <%--支付方式：<input type="text" name="payType" value="QUICKPAY"/> <br/><!-- AUTHPAY -->--%>
	                    <%--支付接口编号：<input type="text" name="interfaceCode" value=""/>AUTHPAY_YLZF-DEBIT-CARD <br/>--%>
	                    <%--同一订单是否可重复提交：<input type="text" name="retryFalg" value="TRUE" /> <br/>--%>
	                    <%--订单提交时间：<input type="text" id="submitTime" name="submitTime" /> <br/>--%>
	                    <%--订单超时时间：<input type="text" id="timeout" name="timeout" value="1D"/> <br/>--%>
	                    <%--业务扩展参数：<input type="text" name="extParam" /> <br/>--%>
	                    <%--回传参数：<input type="text" name="returnParam" /> <br/>--%>
	                    <%--商品名称：<input type="text" name="productName" value="充值"/> <br/>--%>
	                    <%--支付结果页面回调URL：<input type="text" name="redirectUrl" value="http://www.baidu.com/" /> <br/>--%>
	                    <%--支付结果后台通知URL：<input type="text" name="notifyUrl" value="http://www.baidu.com/" /> <br/>--%>
	                    <%--支付方式：<input type="text" name="paymentType" value="" />ALL <br/>--%>
	                    <%--银行卡号：<input type="text" name="bankCardNo" value="6215583202002031321"/>6225768773991953-5220010103793289 <br/>--%>
	                    <%--接入方式：<input type="text" name="accMode" value="GATEWAY" />INTERFACE|GATEWAY <br/>--%>
	                    <%--微信二维码获取方式：<input type="text" name="wxNativeType" value="PAGE" />PAGE|URL <br/>--%>
	                  <%--  结算类型：<input type="text" name="settleType" value="FEE" /><br/>
	                   代付费率：<input type="text" name="remitFee" value="1" /><br/>
	                   快捷支付费率：<input type="text" name="quickPayFee" value="0.003" /><br/>
	                  结算金额：<input type="text" name="settleAmount" value="9.5" /><br/> --%>
	                    <%--网银用户ip：<input type="text" name="clientIP" value="" /><br/>--%>
	                    <%--签名方式：<input type="text" name="signType" value="MD5" /> <br/>--%>
	                    <%--密钥：<input type="text" id="key" name="key" value="fb59c6d069f80c85bb535d727cada9ba" />--%>
            <%--<input type="text" id="sign" value="" name="sign" size="100" /><br/>--%>
            <input type="button" onclick="getSign()" value="生成sign" /><input type="button" value="提交" onclick="sub()" />
		</form>
	</div>
</body>
</html>