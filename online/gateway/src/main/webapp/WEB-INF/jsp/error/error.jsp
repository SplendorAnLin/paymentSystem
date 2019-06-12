<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <script type="text/javascript" src="${applicationScope.staticFilesHost}/gateway/js/response.js"></script>
    <link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/error.css"/>
    <title></title>

</head>
<body>
<!-- 页眉 -->
<header class="sys-header theme-bg absolute">
    <div class="logo fl">
        <img class="toggle-drawerNav" src="${applicationScope.staticFilesHost}/gateway/image/logo-head.png" alt="">
    </div>
    <div class="fr">
        <ul class="links fl">
            <li><a target="_blank" href="javascript:void(0);">聚合运营管理系统</a></li>
            <li><a target="_blank" href="javascript:void(0);">API文档</a></li>
            <li><a target="_blank" href="javascript:void(0);">帮助</a></li>
        </ul>
        <div class="control-panel fl">
            <div class="user-name f-13">
                <span>Hi, </span>
                <span>聚合支付有限公司</span>
            </div>
        </div>
    </div>
</header>

<section>
    <div class="errorimg">
        <img src="${applicationScope.staticFilesHost}/gateway/image/error.png"/>
        <span>交易失败</span></div>
    <div class="error_sta">
        <div class="left">错误编码：</div>
        <div class="right"><%=request.getAttribute("merchantCode") == null ? "9999" : request.getAttribute("merchantCode").toString() %></div>
    </div>
    <div class="error_sta">
        <div class="left">提示信息：</div>
        <div class="right"><%=request.getAttribute("responseMessage") == null ? "未知错误，请联系客服人员" : request.getAttribute("responseMessage").toString() %>
        </div>
    </div>

</section>

</body>
</html>