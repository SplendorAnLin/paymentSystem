<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>结算信息修改</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 540px; padding:0.2em;">
  
  <div class="query-box" style="padding: 1.8em 3em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="updateCustomerSettle.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="customerSettle.bankCode" value="${customerSettle.bankCode }" id="bankCode">
        <input type="hidden" id="sabkCode">
        <input type="hidden" id="sabkName">
       
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                <input type="text" name="customerSettle.customerNo" class="input-text" value="${customerSettle.customerNo}" readonly>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">姓名:</span>
              <div class="input-wrap">
                <input type="text" name="customerSettle.accountName" id="cardName" class="input-text" value="${customerSettle.accountName}" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" value="${customerSettle.customerType }" id="accType" name="customerSettle.customerType" dictTypeId="ACCOUNT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">银行卡号:</span>
              <div class="input-wrap">
                <input type="text" name="customerSettle.accountNo" id="accountNo" class="input-text" value="${customerSettle.accountNo}"" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">开户行信息:</span>
              <div class="input-wrap mr-10">
                <input type="text" class="input-text" style="width: 280px;" name="customerSettle.openBankName" id="openBankName" value="${customerSettle.openBankName}" placeholder="请输入地区、分行支行名称等关键词进行匹配" required>
              </div>
              <input class="checkbox" type="checkbox" id="sabkBankFlag" value="0">
              <label>总行</label>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">修 改</button>
        </div>
      </form>
    </div>
  </div>

  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    $(document).ready(function() {
      // 获取表单验证对象实例 
      var customValidator = window.getValidator($('form'));
      // 初始化卡识别验证
      var autoComplete = general.SettleCardValidator($('form'), customValidator, true);

      var initWidth = $(document.body).outerHeight();
      var openBankName = $('#openBankName');

      autoComplete.options.notAvailable = true;

      openBankName.on(AutoComplete.Event.show, function(event, autoComplete) {
        $(document.body).height(initWidth + autoComplete.element.outerHeight());
      });
      openBankName.on(AutoComplete.Event.hide, function(event, autoComplete) {
        $(document.body).height(initWidth);
      });
      openBankName.on(AutoComplete.Event.select, function(event, data) {
        $(document.body).height(initWidth);
      });
    });

  </script>
</body>
</html>
