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
    <!--微信自带样式-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/weui.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
    <title>添加银行卡</title>
    <style type="text/css">
        #bankname {
            display: block;
            width: 80%;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
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
</head>

<body style="background: white;">
<header>
    <div class="goback">
        <div class="goback">
            <a onClick="window.history.go(-1);"><img src="${pageContext.request.contextPath}/image/public/web_ico/back.png"/></a>
            添加银行卡
        </div>
    </div>
</header>
<!--<div class="weui_cells_title font_orange text_center fon_30">*请绑定<span>张晓杰</span>的银行卡</div>-->
    <div class="weui_cells weui_cells_form">
        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">姓名</label></div>
            <div class="weui_cell_bd weui_cell_primary" id="accountName">${name}</div>
        </div>

        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">身份证号</label></div>
            <div class="weui_cell_bd weui_cell_primary" id="idNumber">${idCard}</div>
        </div>

        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">银行卡</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input class="weui_input" type="number" pattern="[0-9]*" id="bankNumber" placeholder="请输入银行卡号" />
            </div>
        </div>

        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">所属银行</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <span id="bankName"></span>
            </div>
        </div>

        <div class="weui_cell">
            <div class="weui_cell_hd"><label class="weui_label">预留手机号</label></div>
            <div class="weui_cell_bd weui_cell_primary">
                <input class="weui_input" type="number" maxlength="11" placeholder="输入手机号" id="tel_number" pattern="[0-9]*" />
            </div>
        </div>

    </div>
    <div class="page-bd-15" style="margin-top: 1rem;">
        <input type="button" class="weui_btn" style="background: #1B82D2;" id="bankcard_add" value="添加" /input>
    </div>
<form action="weeklySales">
    <input name="ownerId" type="hidden" id="ownerId" value="${ownerId }"/>
    <input name="customerNo" type="hidden" id="customerNo" value="${customerNo }"/>
    <input name="userName" type="hidden" id="userName" value="${userName }"/>
    <input name="cardName" type="hidden" id="cardName"/>
    <input name="cardType" type="hidden" id="cardType"/>
    <input name="bankCode" type="hidden" id="bankCode"/>
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
<div id="loadingToast" style="display:none">
    <div class="weui-mask_transparent" style="background: rgba(0,0,0,0.2);"></div>
    <div class="weui-toast">
        <i class="weui-loading weui-icon_toast"></i>
        <p class="weui-toast__content">加载中</p>
    </div>
</div>
<!--操作中提示end-->

<script type="text/javascript">
    $(function() {
        function verification() {};
        verification.prototype.phone = function(value, errormsg, dom) {
            var check=false;
            var values = value.val();
            if(!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(values))) {
                value.focus();
                dom.after($("<p>").addClass("error").append(errormsg).fadeIn("fast").delay(2000).fadeOut("fast"));
            } else {
                check = true;
            }
            return check;
        },
            verification.prototype.bankcheck = function(value, errormsg, dom) {
                var check=false;
                var values = value.val();
                if(!(bankCard(value.val()))) {
                    value.focus();
                    dom.after($("<p>").addClass("error").append(errormsg).fadeIn("fast").delay(2000).fadeOut("fast"));
                } else {
                    check = true;
                }
                return check;
            }

        var verifications = new verification()
        $("body").delegate("#tel_number", "change", function() {
            verifications.phone($("#tel_number"), "手机格式不正确", $("#tel_number"));
        });

        $("body").delegate("#bankNumber", "change", function() {
           var check=verifications.bankcheck($("#bankNumber"), "银行卡格式不正确", $("#bankNumber"));
           if(check==false){return check};
            bankChange();
        });
        $("#bankcard_add").click(function() {
            var check =verifications.bankcheck($("#bankNumber"), "银行卡格式不正确", $("#bankNumber")) &&  verifications.phone($("#tel_number"), "手机格式不正确", $("#tel_number")) ;
            if(check==false){
                return check;
            }
            $.ajax({
                type: "post",
                url: "addTansCard",
                data: {
                    "phone": $("#tel_number").val(),
                    "bankCode": $("#bankCode").val(),
                    "customerNo":$("#customerNo").val(),
                    "userName":$("#userName").val(),
                    "idNumber":$("#idNumber").html(),
                    "cardType":$("#cardType").val(),
                    "cardAttr":"TRANS_CARD",
                    "cardAlias":$("#cardName").val(),
                    "bankName":$("#bankName").html(),
                    "accountName":$("#accountName").html(),
                    "accountNo":$("#bankNumber").val()
                },
                success: function(data) {
                    console.log(data);
                   //
                    // window.location.href = "myCard.html"
                },
                error: function() {},
            });


        });

        /**添加银行卡start**/
        function bankChange(){
            var bankCard = $("#bankNumber").val();
            $.post("recognition", {"accountNo":bankCard}, function(data) {
                var bankBin = 0;
                var isFind = false;
                $("#cardName").val(data.cardName);
                $("#cardType").val(data.cardType);
                $("#bankCode").val(data.providerCode);
                for(var key = 10; key >= 2; key--) {
                    bankBin = bankCard.substring(0, key);
                    $.each(data, function(i, item) {
                        if(data.code == bankBin) {
                            isFind = true;
                            $("#bankName").text(data.agencyName);
                            return  data.agencyName;
                        }
                    });

                    if(isFind) {
                        break;
                    }
                }

                if(!isFind) {
                    $("#bankName").text("未知发卡银行");
                    return "未知发卡银行";
                }
            });
        }
        // 银行卡号
        function bankCard(val) {
            var status = true;
            // value为NAN(非法值)或者长度小于12, 则false
            if(isNaN(val))
                status = false;
            if(val.length < 12) {
                status = false;
            }
            // 将 123456字符串卡号,分割成数组 ['1', '2', '3', ...]
            var nums = val.split('');
            // 合计
            var sum = 0;
            // 索引
            var index = 1;
            for(var i = 0; i < nums.length; i++) {
                // 当前索引是否为偶数
                if((i + 1) % 2 === 0) {
                    // 当前数组倒index的数字 * 2, 转数值
                    var tmp = Number(nums[nums.length - index]) * 2;
                    if(tmp >= 10) {
                        // 将大于等于10的值转字符串
                        var t = tmp + ''.split('');
                        // tmp 值等于 字符串[0] + 字符串[1],  '16' 就是 1+6=7
                        tmp = Number(t[0]) + Number(t[1]);
                    }
                    // 累加值
                    sum += tmp;
                } else {
                    // sum 累加当前数组倒index的数字
                    sum += Number(nums[nums.length - index]);
                }
                // 累加索引
                index++;
            }
            // 如果值不是10的倍数, 则不是一个合法的银行卡号
            if(sum % 10 !== 0) {
                status = false;
            }

            return status;
        };
    })
</script>
</body>

</html>