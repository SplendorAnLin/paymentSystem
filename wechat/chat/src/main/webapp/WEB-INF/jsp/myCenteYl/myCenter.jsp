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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/common.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/weui.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
    <!--通用样式-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css"/>
    <!--自适应函数-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/loginOut.js"></script>
    <title>个人中心</title>
    <style>
        .weui_cells_access .weui_cell_ft:after {
            border: none;
        }

        .fon24 {
            font-size: 0.24rem;
        }

        .weui_cells_access a.weui_cell {
            padding-left: 21px;
        }
    </style>
</head>
<body>
<header>
    <div class="header">
        个人中心
        <a onclick="loginOut()">退出</a>
    </div>
</header>
<section>
    <div class="weui_cells weui_cells_access">
        <a class="weui_cell" href="#">
            <div class="weui_cell_hd">
                <img id="customerPic" src="${customerImg }" alt="icon"
                     style="width:60px;margin-right:15px;display:block">
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <p style="font-weight: 600;" id="customerName"></p>
                <p class="text_gray fon24"> 账号：<span id="customerId"></span></p>
            </div>

        </a>
    </div>
    <div class="weui_cells weui_cells_access">
        <a class="weui_cell" href="#">
            <div class="weui_cell_hd">
                <img src="${pageContext.request.contextPath}/image/public/mycenter/customer_center.png" alt="icon"
                     style="width:25px;margin-right:15px;display:block">
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <p class="fon24">商户信息</p>
            </div>
            <div class="weui_cell_ft">
                <img style="height: 100%;top: 0; right: 0; position: absolute;"
                     src="../../public/image/mycenter/authenticated.png"/>
            </div>
        </a>

        <a class="weui_cell" href="toAccountDetails" target="_top">
            <div class="weui_cell_hd">
                <img src="${pageContext.request.contextPath}/image/public/mycenter/zijinmingxi.png" alt="icon"
                     style="width:25px;margin-right:15px;display:block">
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <p class="fon24">资金明细</p>
            </div>
        </a>

        <a class="weui_cell" href="toFindByCustNo" target="_top">
            <div class="weui_cell_hd">
                <img src="${pageContext.request.contextPath}/image/public/mycenter/guanli1.png" alt="icon"
                     style="width:25px;margin-right:15px;display:block">
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <p class="fon24">银行卡管理</p>
            </div>
        </a>
    </div>
    <div class="weui_cells weui_cells_access">
        <a class="weui_cell" href="#">
            <div class="weui_cell_hd">
                <img src="${pageContext.request.contextPath}/image/public/mycenter/aboutus.png" alt="icon"
                     style="width:25px;margin-right:15px;display:block">
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <p class="fon24">关于我们</p>
            </div>
        </a>
        <a class="weui_cell" onclick="loginOut()">
            <div class="weui_cell_hd">
                <img src="${pageContext.request.contextPath}/image/public/mycenter/tuichu.png" alt="icon"
                     style="width:25px;margin-right:15px;display:block">
            </div>
            <div class="weui_cell_bd weui_cell_primary">
                <p class="fon24">安全退出</p>
            </div>
        </a>
    </div>
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
<form action="weeklySales">
    <input name="ownerId" type="hidden" id="ownerId" value="${ownerId}"/>
    <input name="customerNo" type="hidden" id="customerNo" value="${customerNo}"/>
    <input name="userName" type="hidden" id="userName" value="${userName}"/>
</form>
<script type="text/javascript">
    $.ajax({
        type: "post",
        url: "queryCustomerInfo",
        data: {
            "customerNo": $("#ownerId").val(),
            "userName": $("#userName").val()
        },
        beforeSend:function(){$("#loadingToast").fadeIn("fast")},
        success: function (data) {
            $("#customerName").html(data.responseData.cust.fullName);
            $("#customerId").html(data.responseData.cust.phoneNo);
        },
        error: function () {
            console.log("ajax error")
        },
        complete:function(){$("#loadingToast").fadeOut("fast")}
    })
</script>
</body>
</html>