<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="renderer" content="webkit">
    <!--不要缓存-->
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta name="format-detection" content="telephone=no">
    <!--css初始化样式-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/common.css?author=zhupan&v=171201"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/assest/css/static/static.css?author=zhupan&v=171201""/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css?author=zhupan&v=171201"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js?author=zhupan&v=171201"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/echart/echarts.common.min.js"></script>
    <title>统计</title>

</head>
<body>
<header>
    <div class="header">
       统计
    </div>
</header>
<section>
    <form action="weeklySales">
        <input name="ownerId" type="hidden" id="ownerId" value="${ownerId }"/>
        <input name="customerNo" type="hidden" id="customerNo" value="${customerNo }"/>
        <input name="userName" type="hidden" id="userName" value="${userName }"/>
    </form>
    <div class="item">
        <div class="left_title">
            <img src="${pageContext.request.contextPath}/image/public/icon/statistics_ro.png">
            <span>周销售统计图</span>
        </div>
        <div class="clear"></div>
    </div>
    <div class="item">
        <div class="text_item border_right" style="position: relative;">
            <p class="text_gray font_20">总收入 </p>
            <p id="datashow">￥0.00</p>
        </div>
        <div class="text_item ">
            <p class="text_gray font_20">笔数</p>
            <p id="moneyNum">0</p>
        </div>
        <div class="clear"></div>
    </div>
    <!--统计图-->
    <div id="main" style="text-align: center" class="columnar"></div>
    <!--统计图end-->
    <!--平台统计图-->
    <div class="item">
        <div class="left_title">
            <img src="${pageContext.request.contextPath}/image/public/icon/statistics_ro.png">
            <span>平台统计图</span>
        </div>
        <div class="clear"></div>
    </div>
    <div id="pie_chart" style="text-align: center" class="columnar"></div>
    <!--平台统计图end-->
    <!--操作提示start-->
    <div class="weui_dialog_alert" style="display: none;">
        <div class="weui-mask_transparent"></div>
        <div class="weui-toast" >
            <i class="weui_icon_warn weui-icon_toast"></i>
            <p style="margin-top: 10px" class="weui-toast__content" id="responseMsg"></p>
        </div>
    </div>
    <!--操作提示end-->
    <!--操作中提示start-->
    <div id="loadingToast"  style="display:none">
        <div class="weui-mask_transparent" style="background: rgba(0,0,0,0.2);"></div>
        <div class="weui-toast">
            <i class="weui-loading weui-icon_toast"></i>
            <p class="weui-toast__content">加载中</p>
        </div>
    </div>
    <!--操作中提示end-->
</section>
<div id="targetContainer"></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/assest/static.js?author=zhupan&v=171206"></script>
</body>
</html>