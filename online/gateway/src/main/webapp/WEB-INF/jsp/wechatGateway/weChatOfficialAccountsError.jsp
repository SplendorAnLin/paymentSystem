<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<%
		// 商户响应码
		String responseCode = request.getAttribute("responseCode").toString();
		// 商户响应信息
		String responseMsg = request.getAttribute("responseMsg").toString();
	%>
	<div class="wrapper text_center" >
		<div class="text_center showInfo margin_top25 margin_bottom10" >
			<div class="font_center_fail" style="text-align:left">
				<div class="errorTitle">支付失败</div>
				<div class="content_gray">
					<div class="gray_wenzi margin_top5">
						失败编码:
					</div>
					<div class="gray_shuru margin_top5">
						${responseCode }
					</div>
				</div>
				<div class="content_gray">
					<div class="gray_wenzi">
						失败描述:
					</div>
					<div class="gray_shuru">
						${responseMsg }
					</div>
				</div>
			</div>
		</div>
		<div class="text_center margin_top10 margin_bottom25">
			<input type="button" class="btn rePayBtn" value="关闭" onclick="CloseWebPage();"/>
		</div>
	</div>

</body>
</html>