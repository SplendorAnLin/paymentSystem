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

<title>实名认证接口测试</title>
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

		$("#urlSelect").change(function(){
			var url = $("#url");
			var partner = $("#partner");
			var key = $("#key");
			if($(this).val() == "production"){
				url.val("http://pay.feiyijj.com/realAuth-front/auth/trade.htm");
				partner.val("C100009");
				key.val("713120d0cea3c08a751f1d8fb38aa301");
			}else if($(this).val() == "test"){
				url.val("http://10.10.111.70/auth-front/auth/trade.htm");
				 partner.val("8614271579");
                 key.val("5E1524AABA00627C87DD2E28726AA785");
			}else if($(this).val() == "development"){
				url.val("<%=request.getScheme() + "://" + request.getServerName() + ("80".equals(request.getServerPort())||"443".equals(request.getServerPort()) ? "" : (":" + request.getServerPort())) %>/realAuth-front/auth/trade.htm");
				partner.val("C100000");
				key.val("fb59c6d069f80c85bb535d727cada9ba");
			}else{
				url.val("");
				partner.val("");
				key.val("");
			}
		});
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
		var payerName = $("#payerName").val();
		$("#payerName").val(encodeURI(payerName));
		$("#form1").attr("action", $("#url").val());
		$("#form1").submit();
	}
	
	$(function(){
		$("#urlSelect").val("development");
		var url = $("#url");
		var partner = $("#partner");
		var key = $("#key");
		url.val("<%=request.getScheme() + "://" + request.getServerName() + ("80".equals(request.getServerPort())||"443".equals(request.getServerPort()) ? "" : (":" + request.getServerPort())) %>/realAuth-front/auth/trade.htm");
		partner.val("C100000");
		key.val("fb59c6d069f80c85bb535d727cada9ba");
	});
</script>

</head>
<body>
	<div style="border: 1px solid red;padding: 0px;">
		<form id="form1" method="post">
			URL：
				<select id="urlSelect">
					<option value=""></option>
					<option value="development">开发</option>
					<option value="test">测试</option>
					<option value="production">生产</option>
				</select>
			<input id="url" type="text" size="100" /> <br/>
			接口编号：<input type="text" name="apiCode" value="YL-REALAUTH" /> <br/>
			版本号：<input type="text" name="versionCode" value="1.0" /> <br/>
			参数编码字符集：<input type="text" name="inputCharset" id="inputCharset" value="UTF-8" /> <br/><br/>
			卖方（合作方）编号：<input type="text" id="partner" name="partner" value="" /> <br/>
			合作方唯一订单号：<input id="outOrderId" type="text" name="requestCode"   size="100" /> <br/>
			订单提交时间：<input type="text" id="submitTime" name="submitTime" /> <br/>
			订单超时时间：<input type="text" id="timeout" name="timeout" value="1D"/> <br/><br/>
			业务扩展参数：<input type="text" name="extParam" value='' /> <br/>
			支付结果后台通知URL：<input type="text" name="notifyURL" value="http://www.baidu.com/" /> <br/><br/>
			签名方式：<input type="text" name="signType" value="MD5" /> <br/>
			卡号：<input type="text" name="bankCardNo" value="6212260200029905938" /> <br/>
			开户名：<input type="text" name="payerName" id="payerName" value="测试" /> <br/>
			证件号码：<input type="text" name="certNo" value="410622199101286019" /> <br/>
			手机号：<input type="text" name="payerMobNo" value="18511793606" /> <br/>
			业务类型：<input type="text" name="busiType" value="BINDCARD_AUTH"/> <br/>
			密钥：<input type="text" id="key" name="key" value="" />
			<input type="text" id="sign" value="" name="sign" size="100" /><br/>
			<input type="button" onclick="getSign()" value="生成sign" /><input type="button" value="提交" onclick="sub()" />
		</form>
	</div>
	<div id="result" style="width: 1000px"></div>
</body>
</html>
