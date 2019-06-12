<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp"%>
<%@ include file="include/commons-include.jsp"%>
<%@page import="java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<!doctype HTML>
<head>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="聚合支付,网关,在线支付">
	<meta http-equiv="description" content="聚合支付网关">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>支付成功</title>
    <link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/mobileBase.css" />
	<link type="text/css" rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/mobileStyle.css" />
    <%
          String time = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    %>

    <script type="text/javascript">
    	function back(){
    		var orderid = $("#orderid").val();
    		var url = "${pageContext.request.contextPath }/callback/successNotify.htm?orderid=" + orderid;
    		window.location.href = url;
    	}
    </script>
</head>
<body>
	<div>
	    <header>
	        <div class="nav_wrap">
	            <div class="nav_box">
	                <title>聚合移动支付</title>
	                <c:if test="${returnMessage == 'SUCCESS'}">
		                <button class="return_btn" type="button" onclick="back();">完成</button>
	                </c:if>
	                <c:if test="${returnMessage != 'SUCCESS'}">
		                <button class="return_btn" type="button" onclick="javascript:history.go(-1);">返回</button>
	                </c:if>
	            </div>
	        </div>
	    </header>
	    <div class="context_wrap">
	        <div class="orderinfo_wrap">
	            <ul class="orderinfo_box">
	                <li class="title">订单信息</li>
	                <li>订单编号<span class="fr">${orderId}</span></li>
	                <li>支付时间<span class="fr"><%=time%></span></li>
	            </ul>
	            <input type="hidden" id="orderid" value="${orderId}"/>
	        </div>
	        <div class="payselect_wrap">
	            <ul class="payselect_box">
	                <li class="title">支付结果</li>
	                <li>
	                	<p class="notice_font">
	                		<c:if test="${returnMessage == 'SUCCESS'}">交易成功</c:if>
	                		<c:if test="${returnMessage != 'SUCCESS'}">${returnMessage}</c:if>
	                	</p>
	                </li>
	            </ul>
	        </div>
	    </div>
    </div>
    <footer></footer>
</body>
</html>