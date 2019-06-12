<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
  <meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="yes" name="apple-touch-fullscreen" />
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <meta name="renderer" content="webkit|ie-comp|ie-stand">
  <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
  <link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/QRCode/pay-min.css">
  <title>自助付款</title>
</head>
<body>

  <!-- 表单 -->
  <form id="formPay" action="${applicationScope.staticFilesHost}/gateway/QRAutoPay" method="post">
    <!-- 动态参数 -->
    <input type="hidden" name="payType" value="${payType }" />
    <input type="hidden" name="partnerNo" id="partner" value="${partnerNo }" />
    <input type="hidden" name="amount"  id="amount" value=""> 
  </form>

  <!-- 内容 -->
  <div class="content width-max">
    <div class="inner">
      <div class="home-ico">
          <img src="${applicationScope.staticFilesHost}/gateway/image/house_ico.png">
      </div>
      <h1 class="primary-title">自助付款</h1>
      <p class="company">${fullName }</p>
      <div class="payform">
        <div class="amount-placeholder">付款金额</div>
        <div class="amount-input" id="amount-input"></div>
        <div class="amount_plan" id="unit_holder">
          <span class="unit">¥</span>
          <span class="amount ellipsis"></span>
        </div>
      </div>
      <p class="copyright">-- 聚合支付提供技术支持 --</p>
    </div>
  </div>

  <!-- 键盘 -->
  <div class="keyboard">
    <a class="key left-0 top-0" data-code="1">1</a>
    <a class="key left-1 top-0" data-code="2">2</a>
    <a class="key left-2 top-0" data-code="3">3</a>
    <a class="key left-0 top-1" data-code="4">4</a>
    <a class="key left-1 top-1" data-code="5">5</a>
    <a class="key left-2 top-1" data-code="6">6</a>
    <a class="key left-0 top-2" data-code="7">7</a>
    <a class="key left-1 top-2" data-code="8">8</a>
    <a class="key left-2 top-2" data-code="9">9</a>
    <a class="key left-0 top-3" data-code="clear">清</a>
    <a class="key left-1 top-3" data-code="0">0</a>
    <a class="key left-2 top-3" data-code=".">.</a>
    <a class="key left-3 top-0" data-code="backspace">
      <img src="image/delete.png">
    </a>
    <a class="key left-3 top-1 submit-key disable" id="to-submit" onclick="paySubmit()" >确认<br>支付</a>
  </div>

  <!-- 对话框 -->
  <div class="alert"><p class="alert-msg"></p></div>
  <!-- 加载动画 -->
  <div class="fullpage">
    <div class="spinner">
      <div class="cube1"></div>
      <div class="cube2"></div>
    </div>
  </div>

  <script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/pay-min.js"></script>
  <script>
    function paySubmit() {
      var amount = parseFloat(utils.find('#amount').value);
      if (amount == 0 || isNaN(amount)) {
        utils.alert('请输入金额');
        return;
      }
      utils.submit();
      var form = utils.find('#formPay');
      form.submit();
/*       utils.ajax({
        method: 'post',
        url: form.getAttribute('action'),
        data: utils.serialize(form),
        success: function(msg) {
          // todo
          console.log(msg);
        },
        error: function() {
          utils.alert('发生未知错误，请稍后再试!');
          utils.revoked();
        }
      }); */
    };
  </script>
</body>
</html>