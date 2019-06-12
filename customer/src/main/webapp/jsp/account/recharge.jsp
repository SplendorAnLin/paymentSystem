<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>充值</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 720px; padding: 10px;">
  
  <div class="text-left" style="padding: 5px;">
    <form class="validator stop-formeNnter" prompt="DropdownMessage" method="post" target="_blank" id="rechargeForm">
      <input type="hidden" name="apiCode" value="YL-PAY" />
      <input type="hidden" name="inputCharset" id="inputCharset" value="UTF-8" />
      <input type="hidden" id="partner" name="partner" value="${sessionScope.auth.customerno}"/>
      <input id="outOrderId" type="hidden" name="outOrderId"/>
      <!-- <input type="hidden" name="amount" id="transAmount"/> -->
      <input type="hidden" name="currency" value="CNY" />
      <input type="hidden" name="retryFalg" value="TRUE" />
      <input type="hidden" id="submitTime" name="submitTime" />
      <input type="hidden" name="redirectUrl" value="http://pay.feiyijj.com/customer/operatorLogin.action" />
      <input type="hidden" name="notifyUrl" value="" />
      <input type="hidden" id="timeout" name="timeout" value="1D"/>
      <input type="hidden" name="productName" value="充值"/>
      <input type="hidden" name="payType" id="payType" value="ALL"/>
      <input type="hidden" name="bankCardNo"/>
      <input type="hidden" name="accMode" value="GATEWAY" /><br/>
      <input type="hidden" name="wxNativeType" value="PAGE" />
      <input type="hidden" name="signType" value="MD5" />
      <input type="hidden" id="sign" name="sign" />
      <div class="fix">
        <div class="item">
          <div class="input-area">
            <span class="label w-90">可用余额:</span>
            <div class="input-wrap">
              <span class="text-secondary"><span class="mr-5 balance color-red">加载中...</span>元</span>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">充值金额:</span>
            <div class="input-wrap">
              <input type="number" name="amount" class="input-text" required amount >
              <span>元</span>
            </div>
          </div>
        </div>
      </div>
      <button class="btn btn-submit hidden">提交</button>
    </form>
    <div class="tips f-13 bg-gray-middle text-secondary" style="padding: 10px; background: #f9f9f9;line-height: 25px;">
      <p>1、在线充值：请在充值后刷新页面获取最新账户余额。</p>
      <p>2、充值注意事项：交易手续费将实时扣除，请在交易记录查询明细。</p>
      <p style="color: red;">注：不得使用本系统参与洗钱、信用卡套现、犯罪等违法活动，一经查处将冻结账户！</p>
    </div>
  </div>
  

  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    window.rechargeInit();
  </script>
</body>
</html>
