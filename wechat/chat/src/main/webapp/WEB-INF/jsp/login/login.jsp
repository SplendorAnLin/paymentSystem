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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/assest/css/login/login.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/assest/css/login/ionic/css/ionic.min.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
    <title></title>
    <style type="text/css">
        .bar-positive .button {
            border-color: #0c60ee;
            background-color: #1B82D2;
            color: #fff;
        }
        .error {
            height: 40px;
            position: absolute;
            right: 0;
            /* bottom: -0.2rem; */
            background: white;
            border: 1px solid rgba(220, 220, 220, 1);
            padding: 0 5px;
            border-radius: 5px;
            font-size: 14px;
            /* z-index: 100000000; */
            line-height: 40px;
        }
    </style>
    <script type="text/javascript">
        if (window != top){
            top.location.href = location.href;
        }

    </script>
</head>

<body style="background: white;">
<div class="content ">
    <div class="title"><img src="${pageContext.request.contextPath}/image/assest/logo.png"/></div>
    <div class="list padding-horizontal" style="width: 70%;margin: auto;">
        <label class="item item-input" style="border: none;">
            <i class="icon  ion-person placeholder-icon"></i>
            <input type="text" id="userName" pattern="[0-9]" maxlength="11" required="required" placeholder="请输入登录名">
        </label>
        <label class="item item-input " style="border-width: 1px 0 1px 0;">
            <i class="icon  ion-locked placeholder-icon"></i>
            <input type="password" id="password" required="required" placeholder="请输入密码">
        </label>
    </div>
    <div class="button-bar bar-positive padding-horizontal" style="width: 75%;margin: auto;margin-top: 15%;">
        <input type="button" class="button" id="login" value="登录"/>
    </div>
</div>
<div class="bar-footer" style="text-align: center">
    <a>
        <button class="button button-clear pull-left" style="color:#000">快速注册</button>
    </a>
    <button class="button button-clear pull-left" style="color:#999">忘记密码</button>
</div>

<!--操作提示start-->
<div class="weui_dialog_alert" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast" style="width: 9.6em;padding: 10px;margin-left: -4.8em;">
        <i class="weui_icon_warn weui-icon_toast"></i>
        <p style="margin-top: 10px" class="weui-toast__content" id="responseMsg"></p>
    </div>
</div>
<!--操作提示end-->
<!--操作中提示start-->
<div id="loadingToast"  style="display:none">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">加载中</p>
    </div>
</div>
<!--操作中提示end-->

<script type="text/javascript">
    function verification() {
    };
    verification.prototype.phone = function (value, errormsg, dom) {
        var check;
        var values = value.val();
        if (!(/^1[3|4|5|7|8|9][0-9]\d{4,8}$/.test(values))) {
            dom.after($("<p>").addClass("error").append(errormsg).fadeIn("fast").delay(2000).fadeOut("fast"));
            check = false;
            value.focus();
        } else {
            check = true;
        }
        return check;
    }
    verification.prototype.pwd = function (value, errormsg, dom) {
        var check;
        var values = value.val();
        if ($.trim(values.length) <= 0) {
            dom.after($("<p>").addClass("error").append(errormsg).fadeIn("fast").delay(2000).fadeOut("fast"));
            check = false;
            value.focus();
        } else {
            check = true;
        }
        return check;
    }

    var verifications = new verification()
    $("body").delegate("#userName", "change", function () {
        verifications.phone($("#userName"), "手机格式不正确", $("#userName"));
    })
    $("body").delegate("#password", "change", function () {
        verifications.pwd($("#password"), "密码不能为空", $("#password"));
    })

    $("#login").click(function () {
        var check = verifications.phone($("#userName"), "手机格式不正确", $("#userName")) && verifications.pwd($("#password"), "密码不能为空", $("#password"));
        if (check == false) {
            return check;
        }
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/appLogin",
            data: {
                "userName": $("#userName").val(),
                "password": $("#password").val()
            },
            dataType: 'json',
            beforeSend:function(){$("#loadingToast").fadeIn("fast")},
            success: function (data) {
                if (data.responseCode == "FALSE" || data.responseCode == "ERROR") {
                    $("#responseMsg").html(data.responseMsg)
                    $(".weui_dialog_alert").fadeIn("fast").delay(2000).fadeOut("fast");
                } else {
                        window.location.href = "toWelcome";
                }
            },
            error: function (data) {
                console.log("Ajax Error");
            },
            complete:function(){$("#loadingToast").fadeOut("fast")}
        });
    });
</script>
</body>
</html>