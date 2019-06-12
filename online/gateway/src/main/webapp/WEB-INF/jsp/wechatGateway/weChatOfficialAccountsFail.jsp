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
		<div class=" ">
			<div class="wrapper">
				<div class="content_success contentTop">
					<div class="wenzi_success">
						商户名称
					</div>
					<div class="shuru_success">
						${customerName }
					</div>
				</div>
				<div class="content_success contentBottom">
					<div class="wenzi_success">
						订单编号
					</div>
					<div class="shuru_success">
						${customerOrderCode }
					</div>
				</div>
				<hr/>
				<div class="content_success contentBottom contentTop">
					<div class="wenzi_success">
							订单金额
					</div>
					<div class="shuru_success">
						${amount }
					</div>
				</div>
			</div>
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
		</div>
</body>
</html>