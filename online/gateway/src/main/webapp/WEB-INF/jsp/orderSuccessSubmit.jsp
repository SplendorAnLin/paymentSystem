<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header1.jsp"%>
<%@ include file="include/commons-include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>聚合</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/global.css" />
	<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/index.css" />
	
</head>

<body>
	<!--top-->
	<div class="LfItTopBg">
		<div class="LfItTopLogo"><h1><a href="#"><img src="${applicationScope.staticFilesHost}${requestScope.contextPath}/image/Lflogo1.jpg" /></a></h1></div>
	</div>
	<!--LfItTopBg-->
	<!--content-->
	<div class="LfItContent">
		<div class="LfItCCNav"></div>
		<div class="clear"></div>
		<div class="LfItCCSucceed">
			<div class="LfItCCSucceedT"></div>
			<div class="LfItCCSucceedC">
				<div class="LfItCCSucceedCL lL"></div>
				<div class="LfItCCSucceedCR lL">
					<p class="LfItCCSucceedCRO">请牢记以下订单号，尽快刷卡支付！</p>
					<p>应付金额：<i><%=request.getParameter("amount")%>元</i></p>
					<p>刷卡订单号：<%=request.getParameter("interfaceRequestID")%></p>
				</div>
				<div class="clear"></div>
			</div>
			<div class="LfItCCSucceedF">
				<p>您还可以：<a href="#">查看订单详情</a></p>
			</div>
		</div>
		<!--LfItCCSucceed-->
	</div>
	<!--LfItContent-->
</body>
</html>
