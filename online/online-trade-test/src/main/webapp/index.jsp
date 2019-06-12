<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>在线交易测试系统</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <link href="css/base.css" rel="stylesheet" type="text/css"/>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/js/jquery/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/butil.js"></script>
    <script type="text/javascript" src="js/tabs.js"></script>
</head>

<body style="text-align: center; margin-top: 30px;">
<a href="toTradeSubmit.htm">网关交易接口测试</a>
<br>
<%--<br>--%>
<%--<a href="query/query.htm">订单查询接口测试</a>--%>
<br/>
<a href="toWXTradeSubmit.htm">直连交易接口测试</a>
<%--<br/>--%>
<%--<a href="toRealAuth.htm">实名认证接口测试</a>--%>
<%--<br/>--%>
<%--<a href="toQuick.htm">快捷交易接口测试</a>--%>
</body>
</html>