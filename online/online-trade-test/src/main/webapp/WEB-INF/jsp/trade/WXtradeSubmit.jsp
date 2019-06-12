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
                        <td><input id="url" type="text" size="100" style="width: 100%" value="http://localhost:8080/gateway/interface/pay.htm"/></td>
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
                        <td><input type="text" style="width: 100%" id="partner" name="partner" value="C100000" /></td>
                    </tr>
                    <tr>
                        <td>outOrderId</td>
                        <td>合作方唯一订单号</td>
                        <td><input type="text" style="width: 100%" id="outOrderId" name="outOrderId" /></td>
                    </tr>
                    <tr>
                        <td>amount</td>
                        <td>订单金额（单位：元 小数点后保留两位）</td>
                        <td><input type="text" style="width: 100%" name="amount" value="0.03" /></td>
                    </tr>
                    <tr>
                        <td>payType</td>
                        <td>支付方式</td>
                        <td>
                            <select name="payType" style="width: 100%">
                                <option>请选择</option>
                                <option value="JDH5">京东H5</option>
                                <option value="UNIONPAYNATIVE">银联钱包</option>
                                <option value="QQNATIVE">QQ扫码</option>
                                <option value="ALIPAY" selected = "selected">支付宝</option>
                                <option value="WXNATIVE">微信</option>
                                <option value="QQH5">QQH5</option>
                                <option value="ALIPAYMICROPAY">支付宝条码</option>
                                <option value="WXMICROPAY">微信条码</option>
                                <option value="ALIPAYJSAPI">支付宝服务窗</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>authCode</td>
                        <td>条形码（支付宝条码 微信条码时必传）</td>
                        <td><input type="text" style="width: 100%" name="authCode"/></td>
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
                        <td>notifyUrl</td>
                        <td>异步通知地址</td>
                        <td><input type="text" style="width: 100%" name="notifyUrl" value="https://www.baidu.com" /></td>
                    </tr>
                    <tr>
                        <td>signType</td>
                        <td>签名方式（目前只支持MD5）</td>
                        <td><input type="text" style="width: 100%" name="signType" value="MD5" /></td>
                    </tr>
                    <tr>
                        <td>key</td>
                        <td>商户密钥</td>
                        <td><input type="text" style="width: 100%" id="key" name="key" value="fb59c6d069f80c85bb535d727cada9ba" /></td>
                    </tr>
                    <tr>
                        <td>sign</td>
                        <td>签名摘要</td>
                        <td><input type="text" style="width: 100%" id="sign" readonly value="点击下方生成sign即可" name="sign" /></td>
                    </tr>
                </thead>
            </table>
            <input type="button" onclick="getSign()" value="生成sign" /><input type="button" value="提交" onclick="sub()" />
		</form>
	</div>
</body>
</html>
<%-- URL：<input id="url" type="text" size="100" value="http://cbhb.bank-pay.com/gateway/interface/pay.htm"/> <br/> --%>
<%--URL：<input id="url" type="text" size="100" value="http://localhost:8080/gateway/interface/pay.htm"/> <br/>--%>
<%--接口编号：<input type="text" name="apiCode" value="YL-PAY" /> <br/>--%>
<%--参数编码字符集：<input type="text" name="inputCharset" id="inputCharset" value="UTF-8" /> <br/>--%>
<%--商户编号：<input type="text" id="partner" name="partner" value="C100000" /> <br/>--%>
<%--合作方唯一订单号：<input id="outOrderId" type="text" name="outOrderId" size="100" /> <br/>--%>
<%--订单金额：<input type="text" name="amount" value="1" /> <br/>--%>
<%-- 币种：<input type="text" name="currency" value="CNY" /> <br/> --%>
<%--支付方式：<input type="text" name="payType" value="UNIONPAYNATIVE"/>JDH5|QQNATIVE<br/>--%>
<%--authCode：<input type="text" name="authCode" value=""/> <br/>--%>
<%--  支付接口编号：<input type="text" name="interfaceCode" value=""/>AUTHPAY_YLZF-DEBIT-CARD <br/> --%>
<%--  同一订单是否可重复提交：<input type="text" name="retryFalg" value="TRUE" /> <br/> --%>
<%-- 订单提交时间：<input type="text" id="submitTime" name="submitTime" /> <br/> --%>
<%--  订单超时时间：<input type="text" id="timeout" name="timeout" value="1D"/> <br/> --%>
<%--业务扩展参数：<input type="text" name="extParam" /> <br/>--%>
<%--回传参数：<input type="text" name="returnParam" /> <br/>--%>
<%--商品名称：<input type="text" name="product" value="小李飞刀"/> <br/>--%>
<%-- 支付结果页面回调URL：<input type="text" name="redirectUrl" value="http://www.baidu.com/" /> <br/> --%>
<%--支付结果后台通知URL：<input type="text" name="notifyUrl" value="http://www.baidu.com/" /> <br/>--%>
<%--签名方式：<input type="text" name="signType" value="MD5" /> <br/>--%>
<%--    密钥：<input type="text" id="key" name="key" value="9fee790a727d4bf149ee4b32ca69270b" /> --%>
<%--密钥：<input type="text" id="key" name="key" value="fb59c6d069f80c85bb535d727cada9ba" />--%>
<%--<input type="text" id="sign" value="" name="sign" size="100" /><br/>--%>