<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta content="telephone=no,email=no" name="format-detection">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="yes" name="apple-mobile-web-app-capable">
<%@ taglib prefix="s" uri="/WEB-INF/tld/struts-tags.tld"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet" href="css/rest.css">
<link rel="stylesheet" href="css/bill.css">
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
<!--全局样式-->
<link rel="stylesheet" href="css/common.css" />
<!--下拉加载-->
<link rel="stylesheet" href="css/dropload.css" />
<!--页面样式-->
<link rel="stylesheet" href="css/searchQueryResult.css" />
<!--页面自适应函数-->
<script type="text/javascript" src="js/response.js"></script>
<title>结算交易明细</title>
</head>
<body style="background: #F0F0F0 none repeat scroll 0 0;">
	<div class="contant">
		<ul>
			<li class="li_border"><span class="span_left">商户名称:</span><span
				class="span_right">${cust.fullName}</span></li>
			<li><span class="span_left">商户编号：</span><span class="span_right">${cust.customerNo }</span></li>
			<li style="line-height: normal"><span class="span_left">开户银行:</span><span style="height: 0.6rem;overflow: hidden"  class="span_right">${dpaydetail.BANK_NAME }</span></li>
			<li><span class="span_left">开户账户:</span><span class="span_right">${dpaydetail.ACCOUNT_NO }</span></li>
			<li><span class="span_left">交易类型：</span> <c:if
					test="${dpaydetail.REQUEST_TYPE == 'PAGE' }">
					<span class="span_right">商户后台付款</span>
				</c:if> <c:if test="${dpaydetail.REQUEST_TYPE == 'INTERFACE' }">
					<span class="span_right">接口代付</span>
				</c:if> <c:if test="${dpaydetail.REQUEST_TYPE == 'DRAWCASH' }">
					<span class="span_right">商户提现</span>
				</c:if> <c:if test="${dpaydetail.REQUEST_TYPE == 'AUTO_DRAWCASH' }">
					<span class="span_right">自动结算</span>
				</c:if></li>
			<li  style="line-height: normal"><span class="span_left">流水号:</span><span
				class="span_right">${dpaydetail.FLOW_NO }</span></li>
		</ul>

		<ul>
			<li><span class="span_left">交易金额:</span><span class="span_right">RMB <fmt:formatNumber value="${dpaydetail. AMOUNT }" pattern="#0.00#" /></span></li>
			<li><span class="span_left">手续费:</span><span class="span_right">RMB <fmt:formatNumber value="${dpaydetail. FEE }" pattern="#0.00#" /></span></li>
			<li><span class="span_left">到账金额:</span><span class="span_right">RMB <fmt:formatNumber value="${dpaydetail. AMOUNT}" pattern="#0.00#" /></span></li>
			<li><span class="span_left">订单状态:</span>
			<c:if test="${dpaydetail.STATUS == 'WAIT' }"><span class="span_right">等待付款</span></c:if>
			<c:if test="${dpaydetail.STATUS == 'SUCCESS' }"><span class="span_right">代付成功</span></c:if>
			<c:if test="${dpaydetail.STATUS == 'HANDLEDING' }"><span class="span_right">处理中</span></c:if>
			<c:if test="${dpaydetail.STATUS == 'FAILED' }"><span class="span_right">失败</span></c:if>
			<c:if test="${dpaydetail.status == 'UNKNOWN' }"><span class="span_right">未知</span></c:if></li>
			<li><span class="span_left">交易时间:</span><span class="span_right"><fmt:formatDate value="${dpaydetail.CREATE_DATE }" pattern="yyyy-MM-dd HH:mm:ss" /></span></li>
			<li><span class="span_left">完成时间:</span><span class="span_right"><fmt:formatDate value="${dpaydetail.COMPLETE_DATE}" pattern="yyyy-MM-dd HH:mm:ss" /></span></li>
		</ul>
	</div>
</body>
</html>