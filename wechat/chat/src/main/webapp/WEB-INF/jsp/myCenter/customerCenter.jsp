<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="renderer" content="webkit">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <!--css初始化样式-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/common.css?author=zhupan&v=171201" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css?author=zhupan&v=171201" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/weui.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js?author=zhupan&v=171201"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
    <title></title>
    <style type="text/css">
        .weui_cell_ft {
            max-width: 67%;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

    </style>
</head>

<body>
<header>
    <div class="goback">
        <a  href="toWelcome?name=0"><img src="${pageContext.request.contextPath}/image/public/web_ico/back.png"/></a>
        商户信息
    </div>
</header>
<section>
    <div style="background: white;margin:0.2rem;">
        <div class="weui_cell no_access">
            <div class="weui_cell_bd weui_cell_primary">
                <p>商户名称</p>
            </div>
            <div class="weui_cell_ft" id="fullName"></div>
        </div>

        <div class="weui_cell no_access">
            <div class="weui_cell_bd weui_cell_primary">
                <p>商户简称</p>
            </div>
            <div class="weui_cell_ft" id="shortName"></div>
        </div>

        <div class="weui_cell no_access">
            <div class="weui_cell_bd weui_cell_primary">
                <p >身份证号</p>
            </div>
            <div class="weui_cell_ft hidden_o" id="idCard"></div>
        </div>
    </div>

    <div style="background: white;margin:0.2rem;">

        <div class="weui_cell no_access">
            <div class="weui_cell_bd weui_cell_primary">
                <p>商户地址</p>
            </div>
            <div class="weui_cell_ft" id="addr"></div>
        </div>

        <div class="weui_cell no_access">
            <div class="weui_cell_bd weui_cell_primary">
                <p>联系方式</p>
            </div>
            <div class="weui_cell_ft" id="phoneNo"></div>
        </div>

        <div class="weui_cell no_access">
            <div class="weui_cell_bd weui_cell_primary">
                <p>邮箱</p>
            </div>
            <div class="weui_cell_ft" id="email"></div>
        </div>

        <div class="weui_cell no_access">
            <div class="weui_cell_bd weui_cell_primary">
                <p >开通时间</p>
            </div>
            <div class="weui_cell_ft" id="createTime"></div>
        </div>


    </div>

</section>
<form action="weeklySales">
    <input name="ownerId" type="hidden" id="ownerId" value="${ownerId }"/>
    <input name="customerNo" type="hidden" id="customerNo" value="${customerNo }"/>
    <input name="userName" type="hidden" id="userName" value="${userName }"/>
</form>
<!--操作提示start-->
<div class="weui_dialog_alert" style="display: none;">
    <div class="weui-mask_transparent"></div>
    <div class="weui-toast" >
        <i class="weui_icon_warn weui-icon_toast"></i>
        <p style="margin-top: 10px" class="weui-toast__content" id="responseMsg">已完成</p>
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
<script type="text/javascript">
    $(function () {
        function Cipher_text_display(id){
            var reg = /^(\d{4})\d+(\d{4})$/;
            id = id.replace(reg, "$1****$2");
            return id;
        }
        function Cipher_text_displayp(id){
            var reg = /^(\d{3})\d+(\d{4})$/;
            id = id.replace(reg, "$1****$2");
            return id;
        }
        //时间戳转换
        function formatDate(now) {
            var year=now.getFullYear();
            var month=now.getMonth()+1;
            if(month<10){
                month="0"+month;
            }
            var date=now.getDate();
            if(date<10){
                date="0"+date;
            }
            var hour=now.getHours();
            if(hour<10){
                hour="0"+hour;
            }
            var minute=now.getMinutes();
            if(minute<10){
                minute="0"+minute;
            }
            var second=now.getSeconds();
            if(second<10){
                second="0"+second;
            }
            return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
        }
        $.ajax({
            type:"post",
            "url":"queryCustomerInfo",
            data: {
                "customerNo":$("#customerNo").val(),
                "userName":$("#userName").val()
            },
            beforeSend:function(){$("#loadingToast").fadeIn("fast")},
            success:function(data){
                if(data.responseCode=="000000"){
                    var result=data.responseData.cust;
                  $("#fullName").html(result.fullName);
                    $("#email").html(result.email);
                    var d=new Date(result.createTime);
                    $("#createTime").html(formatDate(d) );
                    $("#idCard").html(Cipher_text_display(result.idCard));
                    $("#shortName").html(result.shortName);
                    $("#phoneNo").html(Cipher_text_displayp(result.phoneNo) );
                    $("#addr").html(result.addr);
                }
                else {
                    $("#responseMsg").html(data.responseMsg)
                    $(".weui_dialog_alert").fadeIn("fast").delay(2000).fadeOut("fast");
                }
            },
            complete:function(){$("#loadingToast").fadeOut("fast")}
        })
    })
</script>
</body>

</html>