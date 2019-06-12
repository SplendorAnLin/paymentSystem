<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户提现</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 720px; padding: 10px;">
  
  <div class="text-left" style="padding: 5px;">
    <div class="drawCash">
      <div class="fix">
        <div class="item">
          <div class="input-area">
            <span class="label w-90">可用余额:</span>
            <div class="input-wrap">
              <span class="text-secondary"><span class="mr-5 count color-red">${balance }</span>元</span>
            </div>
          </div>
        </div>
        <div class="item">
          <div class="input-area">
            <span class="label w-90">提现金额:</span>
            <div class="input-wrap">
              <input type="number" class="input-text" id="amount">
            </div>
          </div>
        </div>
      </div>
      <button class="btn btn-submit hidden" onclick="drawCash('${balance }');">提交</button>
    </div>
    <div class="tips f-13 bg-gray-middle text-secondary" style="padding: 10px; background: #f9f9f9;line-height: 25px;">
      <p>1、清算时间：（正常情况9:00-21:00）具体请关注我司公告通知。</p>
      <p>2、结算注意事项：单笔结算金额不得超过5万元，如果大于5万请分多笔结算。</p>
      <p>3、结算卡更换：目前不支持自助更换结算银行卡，如需更换请与我们联系</p>
    </div>
  </div>
  
  
  
  
  <!-- 付款审核验证对话框-短信验证 -->
  <div id="accept-password-sms" style="width: 400px; padding: 0.2em;display:none;">
    <div class="text-left" style="padding: 2em 1.2em;">
      <form class="validator" prompt="DropdownMessage">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">提现金额:</span>
              <div class="input-wrap">
                <span class="text-secondary"><span class="mr-5 count cash-amount">0</span>元</span>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">提现密码:</span>
              <div class="input-wrap">
                <input type="password" name="password" required rangelength="[6,16]" checkDrawCashPassword trigger='{"checkDrawCashPassword": 0}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">手机验证码:</span>
              <div class="input-wrap">
                <input type="password" name="verify-code" required rangelength="[6,6]" placeholder="验证码6位数" checkVerifyCode trigger='{"checkVerifyCode": 0}'>
              </div>
              <a id="sendVerifyCode" href="javascript:void(0);">获取验证码</a>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
  
  
  <!-- 付款审核验证对话框-提现密码验证 -->
  <div id="accept-password" style="width: 378px; padding: 0.2em;display:none;">
    <div class="text-left" style="padding: 2em 1.2em;">
      <form class="validator" prompt="DropdownMessage">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">提现金额:</span>
              <div class="input-wrap">
                <span class="text-secondary"><span class="mr-5 count cash-amount">0</span>元</span>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">提现密码:</span>
              <div class="input-wrap">
                <input type="password" name="password" required rangelength="[6,16]" checkDrawCashPassword trigger='{"checkDrawCashPassword": 0}'>
              </div>
            </div>
          </div>
        </div>
      </form>
      <div class="tips bg-gray-middle text-secondary" style="padding: 1em;">
        如需更高安全级别，可在【提现密码】中开启手机验证码验证功能。
      </div>
    </div>
  </div>
  
  
  
  
  
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    window.drawCashInit();
  </script>
</body>
</html>
