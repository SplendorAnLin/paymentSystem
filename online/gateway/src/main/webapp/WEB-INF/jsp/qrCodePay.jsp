<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setAttribute("contextPath", request.getContextPath());
%>
<%@ include file="include/commons-include.jsp"%>
<!DOCTYPE html>
<html lang="zh" class="full-size oh">
<head>
    <meta charset="UTF-8">
    <title>订单支付 - 千付宝支付</title>
    <link rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/rest.css">
    <link rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/public.css">
    <link rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/component.css">
    <link rel="stylesheet" href="${applicationScope.staticFilesHost}${requestScope.contextPath}/css/content.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/qrcode.js"></script>
    <script type="text/javascript" src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript">
        window.onload = function() {
            // 二维码对象
            var qrcode;
            // 默认设置
            var content = document.getElementById("content").value;
            var size = 150;
            // 获取内容
            content = content.replace(/(^\s*)|(\s*$)/g, "");

            // 清除上一次的二维码
            if (qrcode) {
                qrcode.clear();
            }

            // 创建二维码
            qrcode = new QRCode(document.getElementById("qrcode"), {
                width : size,//设置宽高
                height : size
            });

            qrcode.makeCode(document.getElementById("content").value);
        }
       $(function() {
           setTimeout(queryOrder, 2000);
       });
        function queryOrder() {
            $.post("${applicationScope.staticFilesHost}${requestScope.contextPath}/queryWalletOrder.htm","partnerCode="+$("#customerNo").val()+"&requestCode="+$("#outOrderId").val(),function(result){
                if (result != null && result != '') {
                    result = eval('(' + result + ')');
                }
                if (result != null && result != '' && result.code == '00') {
                    $("#qrcode").html('<img src="${applicationScope.staticFilesHost}${requestScope.contextPath}/image/pay_complete.png" alt="" style="width: 20%;">');
                    $("#info").html("3秒后返回商户页面.......");
                    $("#zfSpan").removeClass("b-coralBlue").addClass("b-babyBlue");
                    $("#completeSpan").removeClass("b-gray").addClass("b-coralBlue").attr("style","width: 315px");
                    $("#nextSpan").removeClass("arrow-current").addClass("arrow-complete");
                    $("#conNextSpan").attr("style","background: #9cddf5");
                    setTimeout(function(){
                        $("#info").html("2秒后返回商户页面.......");
                    }, 1000);
                    setTimeout(function(){
                        $("#info").html("1秒后返回商户页面.......");
                    }, 2000);
                    setTimeout(function(){
                        window.location.href = "${applicationScope.staticFilesHost}${requestScope.contextPath}/callback/wechatNative.htm?orderCode="+ $("#orderCode").val();
                    }, 3000);
                } else if (result != null && result != '' && result.code == '99') {
                    $("#qrcode").html('<img src="${applicationScope.staticFilesHost}${requestScope.contextPath}/image/message/exclamation-circle.png" alt="" style="width: 15%;">');
                    $("#info").html("<br/>二维码已过期请重新下单！");
                }  else {
                    setTimeout(queryOrder, 1000);
                }
            });
        }
    </script>
</head>
<body>

<div class="header">
    <div class="logo-box fl">
        <img src="${applicationScope.staticFilesHost}${requestScope.contextPath}/image/logo-head.png" alt="">
    </div>
    <div class="account fr">
        <ul class="links fl">
            <li><a href="javascript:void(0);">千付宝网关系统</a></li>
            <li><a href="javascript:void(0);">API文档</a></li>
            <li><a href="javascript:void(0);">帮助</a></li>
        </ul>
        <div class="user-plan fl">
            <span class="title">HI , 千付宝支付有限公司</span>
        </div>
    </div>
</div>

<div class="b-out mtb-40 w-950">
    <div class="b-in">
        <div class="uc-process">
            <span class="unit b-babyBlue">下单</span>
            <span class="arrow-complete" id="conNextSpan">
        <span class="next"></span>
        <span class="prev"></span>
      </span>
            <span id='zfSpan' class="unit b-coralBlue">支付</span>
            <span id="nextSpan" class="arrow-current">
        <span class="next"></span>
        <span class="prev"></span>
      </span>
            <span id="completeSpan" class="unit b-gray">支付成功</span>
        </div>

        <div class="order-box">
            <div class="order-info">
                <div class="order-title fix">
                    <strong class="fl f-18">订单信息</strong>
                    <span class="total fr">
            <span class="cny">¥</span>
            <span><fmt:formatNumber pattern="#,##0.00#" value="${amount}" /></span>
          </span>
                </div>
                <div class="order-table-box mt-20">
                    <div class="order-table-scroll">
                        <table class="uc-table w-p-100 f-12" style="font-size: 14px;">
                            <tbody>
                            <tr>
                                <%--<td class="c_black">商户名称</td>--%>
                                <%--<td>${customerName }</td>--%>
                                <%--<td class="text-right"><span class="align"><span class="cny">¥</span><span>120</span></span></td>--%>
                            </tr>
                            <tr>
                                <td class="c_black">订单号</td>
                                <td>${outOrderId}</td>
                                <input type="hidden" id="customerNo" value="${customerNo }" />
                                <input type="hidden" id="orderCode" value="${orderCode }" />
                                <input type="hidden" id="outOrderId" value="${outOrderId }" />
                                <%--<td class="text-right"><span class="align"><span class="cny">¥</span><span>5212.20</span></span></td>--%>
                            </tr>
                            </tr>
                            <td class="c_black">订单金额</td>
                            <td class="money">¥ <fmt:formatNumber pattern="#,##0.00#" value="${amount}" /></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <input id="content" value="${code_url}" type="hidden"/>
            <div class="code" style="text-align: center;padding-bottom: 15px;padding-top: 15px;">
                <div id="qrcode" style="text-align: center;width: 100%">
                </div>
                <p id="info" style="color: #716b6b; font-size: 1.5em">
                    <%--<c:if test="${typeFlag == 'WX'}">微信扫码或长按识别二维码完成支付</c:if>--%>
                    <%--<c:if test="${typeFlag == 'ALIPAY'}">支付宝扫码或长按识别二维码完成支付</c:if>--%>
                    请在10分钟内扫码支付，否则影响到账时间。
                </p>
            </div>
        </div>
    </div>
</div>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/plugins/jquery-1.8.3.min.js"></script>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/plugins/highcharts.js"></script>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/plugins/select.js"></script>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/plugins/datepicker/WdatePicker.js"></script>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/chart.js"></script>
<script src="${applicationScope.staticFilesHost}${requestScope.contextPath}/js/newJs/UI.js"></script>
<script>
    if (window.tabSwitch) {
        tabSwitch($('#payway-tab'), 'active');
    }
</script>
</body>
</html>