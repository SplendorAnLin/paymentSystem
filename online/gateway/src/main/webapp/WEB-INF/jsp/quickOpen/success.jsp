<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/commons-include.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
  <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="yes" name="apple-touch-fullscreen" />
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <meta name="renderer" content="webkit|ie-comp|ie-stand">
  <link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/quickOpen/weui.min.css">
  <link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/quickOpen/wapRegister.css?v=2017110711&author=XueYou">
  <title>扫码入网成功</title>
</head>
<body>

  <div class="container">
    <div class="page">
      <div class="page__bd">
        <div class="weui-msg">
            <div class="weui-msg__icon-area"><i class="weui-icon-success weui-icon_msg"></i></div>
            <div class="weui-msg__text-area">
                <h2 class="weui-msg__title">入网成功</h2>
                <p class="weui-msg__desc">恭喜您成功聚合支付的用户<br>聚合支付通过多项国际级标准金融认证，使用先进技术产品保护账户安全，7*24小时不间断监控。使用聚合支付，放心经营，安心消费</p>
            </div>
            <div class="weui-msg__opr-area">
                <p class="weui-btn-area">
                    <a href="http://pay.feiyijj.com/app/download/" class="weui-btn weui-btn_primary">下载APP</a>
                </p>
            </div>
            <div class="weui-msg__extra-area">
                <div class="weui-footer">
                    <!-- <p class="weui-footer__links">
                        <a href="javascript:void(0);" class="weui-footer__link">IOS客户端下载</a>
                        <a href="javascript:void(0);" class="weui-footer__link">Android客户端下载</a>
                    </p> -->
                    <%--<p class="weui-footer__text">客服电话: 400-860-7199</p>--%>
                </div>
            </div>
        </div>
      </div>
    </div>
  </div>

</body>
</html>