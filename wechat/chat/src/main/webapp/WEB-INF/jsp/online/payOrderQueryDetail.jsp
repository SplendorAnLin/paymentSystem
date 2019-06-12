<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<meta charset="utf-8" />
<meta name="renderer" content="webkit">
<!--不要缓存-->
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<META HTTP-EQUIV="expires" CONTENT="0">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta name="format-detection" content="telephone=no">
<!-- home screen app 全屏 -->
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta http-equiv="x-rim-auto-match" content="none">
<!--时间插件样式-->
<link rel="stylesheet" href="js/DateSelector/DateSelector.css" />
<!--全局样式-->
<link rel="stylesheet" href="css/common.css" />
<!--下拉加载-->
<link rel="stylesheet" href="css/dropload.css" />
<!--页面样式-->
<link rel="stylesheet" href="css/searchQueryResult.css" />
<!--页面自适应函数-->
<script type="text/javascript" src="js/response.js"></script>

<title>订单交易明细</title>
</head>
<body style="background: #F0F0F0 none repeat scroll 0 0;">
	<div class="contant">
		<ul>
			<li class="li_border"><span class="span_left">商户名称:</span><span
				class="span_right">${customer.fullName}</span></li>
			<li><span class="span_left" >商户编号:</span><span class="span_right">${customer.customerNo }</span></li>
			<li style="line-height: normal"><span class="span_left" >订单编号:</span><span
				class="span_right" style="height: 0.6rem;overflow: hidden">${order.code }</span></li>
			<li><span class="span_left">流水号：</span><span class="span_right">${order.requestCode }</span></li>
			<li><span class="span_left">交易类型：</span> <c:if 
					test="${order.payType == 'ALIPAY' }">
					<span class="span_right">支付宝</span>
				</c:if> <c:if test="${order.payType == 'B2C' }">
					<span class="span_right">个人网银</span>
				</c:if> <c:if test="${order.payType == 'B2B' }">
					<span class="span_right">企业网银</span>
				</c:if> <c:if test="${order.payType == 'AUTHPAY' }">
					<span class="span_right">认证支付</span>
				</c:if> <%-- 	    <c:if test="${order.payType == 'RECEIVE' }">代收</c:if> --%>
				<c:if test="${order.payType == 'WXJSAPI' }">
					<span class="span_right">微信公众号</span>
				</c:if> 
				<c:if test="${order.payType == 'WXNATIVE' }">
					<span class="span_right">微信扫码</span>
				</c:if> 
				<c:if test="${order.payType == 'WXMICROPAY' }">
					<span class="span_right">微信条码</span>
				</c:if> <%-- <c:if test="${order.payType == 'HOLIDAY_REMIT' }">假日付</c:if> --%>
				<c:if test="${order.payType == 'UNKNOWN' }">
					<span class="span_right">未知</span>
				</c:if> 
				<c:if test="${order.payType == 'REMIT' }">
					<span class="span_right">付款</span>
				</c:if>
				<c:if test="${order.payType == 'ALIPAYMICROPAY' }">
					<span class="span_right">支付宝条码</span>
				</c:if>
				<c:if test="${order.payType == 'QUICKPAY' }">
					<span class="span_right">快捷支付</span>
				</c:if>
				</li>
			<li ><span class="span_left">交易金额：</span><span
				class="span_right">RMB <fmt:formatNumber value="${order.amount  }" pattern="#0.00#" /></span></li>
		</ul>

		<ul>
			<li><span class="span_left">手续费：</span><span class="span_right">RMB
					<fmt:formatNumber value="${order.receiverFee  }" pattern="#0.00#" /></span></li>
			<li><span class="span_left">到账金额：</span><span class="span_right">RMB
					<fmt:formatNumber value="${order.amount-order.receiverFee  }" pattern="#0.00#" /></span></li>
			<li><span class="span_left">订单状态：</span> <c:if
					test="${order.status=='WAIT_PAY'}">
					<span class="span_right">等待付款</span>
				</c:if> <c:if test="${order.status=='SUCCESS'}">
					<span class="span_right">成功</span>
				</c:if> <c:if test="${order.status=='CLOSED'}">
					<span class="span_right">关闭</span>
				</c:if> <c:if test="${order.status=='FAILED'}">
					<span class="span_right">失败</span>
				</c:if></li>
			<li><span class="span_left">清算状态:</span> <c:if
					test="${order.clearingStatus =='UN_CLEARING'}">
					<span class="span_right">未清算</span>
				</c:if> <c:if test="${order.clearingStatus =='CLEARING_SUCCESS'}">
					<span class="span_right">清算成功</span>
				</c:if> <c:if test="${order.clearingStatus =='CLEARING_FAILED'}">
					<span class="span_right">清算失败</span>
				</c:if></li>
			<li><span class="span_left">下单时间:</span><span class="span_right">
					<fmt:formatDate value="${order.orderTime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
			</span></li>
			<li><span class="span_left">支付时间:</span><span class="span_right">
					<fmt:formatDate value="${order.successPayTime}"
						pattern="yyyy-MM-dd HH:mm:ss" />
			</span></li>
		</ul>
	</div>
</body>
</html>