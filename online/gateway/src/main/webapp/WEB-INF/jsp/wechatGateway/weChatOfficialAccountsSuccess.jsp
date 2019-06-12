<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="format-detection" content="telephone=no" />
		<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/css/bootstrap.min.css">
		<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/css/bootstrap-theme.min.css">
		<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/css/weChatOfficialAccounts.css">
		<script type="text/javascript" src="${applicationScope.staticFilesHost}/js/jquery/jquery-1.10.2.min.js"></script>
		<script type="text/javascript">
			function CloseWebPage() {
				WeixinJSBridge.call('closeWindow');
			}
		</script>
	</head>
<body>
		<div class=" ">
			<div class="wrapper">
				<div class="content_success contentTop">
					<div class="wenzi_success fontweight700">
						商户名称
					</div>
					<div class="shuru_success">
						${customerName }
					</div>
				</div>
				<div class="content_success contentBottom">
					<div class="wenzi_success fontweight700">
						订单编号
					</div>
					<div class="shuru_success">
						${requestCode }
					</div>
				</div>
				<hr/>
				<div class="content_success contentBottom contentTop">
					<div class="wenzi_success fontweight700">
							订单金额
					</div>
					<div class="shuru_success fontweight700">
						<fmt:formatNumber value="${amount }" pattern="#,##0.00#"/>
					</div>
				</div>
			</div>
			<div class="wrapper">
				<div class="text_center showInfo_success margin_top25" >
						<span class="font_center_success">支付成功</span>
				</div>
				<div class="margin_top20 text_center margin_bottom25">
					<input type="button" class="btn confirmBtn" value="完&nbsp;&nbsp;成" onclick="CloseWebPage()"/>
				</div>
			</div>
		</div>
</body>
</html>