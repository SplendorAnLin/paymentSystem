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

<title>乐富支付订单查询页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="乐富支付,人民币网关,订单支付结果查询">
<meta http-equiv="description" content="乐富支付订单支付结果查询">
<script type="text/javascript" src="js/jquery/jquery-1.10.2.min.js"></script>

<script type="text/javascript">
	$(function() {
		$("#urlSelect").change(function(){
			var url = $("#url");
			var gatewayUrl = $("#gatewayUrl");
			var partner = $("#partner");
			var key = $("#key");
			if($(this).val() == "production"){
				url.val("https://pay.lefu8.com/online-trade-test/partner/query.htm");
				gatewayUrl.val("https://pay.lefu8.com/gateway/query.htm");
				partner.val("8612183239");
				key.val("e053c95ed7ea4c8f99fa0aa0589ebe17");
			}else if($(this).val() == "test"){
				url.val("https://qa.lefu8.com/online-trade-test/partner/query.htm");
				gatewayUrl.val("https://qa.lefu8.com/gateway/query.htm");
				partner.val("8611146479");
				key.val("23b052c76f2d45428e5db872bf7a12c9");
			}else if($(this).val() == "development"){
				url.val("<%=request.getScheme() + "://" + request.getServerName() + ("80".equals(request.getServerPort())||"443".equals(request.getServerPort()) ? "" : (":" + request.getServerPort())) %>/online-trade-test/partner/query.htm");
				gatewayUrl.val("<%=request.getScheme() + "://" + request.getServerName() + ("80".equals(request.getServerPort())||"443".equals(request.getServerPort()) ? "" : (":" + request.getServerPort())) %>/gateway/query.htm");
				partner.val("8619117161");
				key.val("c1b45b8ddc8041b48440955934f916d7");
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
		$("#form1").attr("action", $("#url").val());
		$("#form1").submit();
	}
</script>
</head>

<body>
	<div style="border: 1px solid red;padding: 0px;">
		<form id="form1" method="post">
			URL：
				<select id="urlSelect">
					<option value=""></option>
					<option value="production">生产</option>
					<option value="test">测试</option>
					<option value="development">开发</option>
				</select>
			<input id="url" type="text" size="100" /> <br/>
			网关提交地址：<input id="gatewayUrl" name="gatewayUrl"> <br/>
			接口编号：<input type="text" name="queryCode" value="directSingleQuery-1.0" /> <br/>
			参数编码字符集：<input type="text" name="inputCharset" value="UTF-8" /> <br/><br/>
			卖方（合作方）编号：<input type="text" name="partner" value="8619117161" /> <br/>
			合作方唯一订单号：<input id="outOrderId" type="text" name="outOrderId" size="100" /> <br/>
			签名方式：<input type="text" name="signType" value="MD5" /> <br/>
			密钥：<input type="text" id="key" name="key" value="" />
			<input type="text" id="sign" value="" name="sign" size="100" /><br/>
			<input type="button" onclick="getSign()" value="生成sign" /><input type="button" value="提交" onclick="sub()" />
		</form>
	</div>
</body>
</html>
