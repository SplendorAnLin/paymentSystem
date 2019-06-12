<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>收款人信息修改</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 540px; padding:0.2em;">
  
  <div class="query-box" style="padding: 1.8em 3em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="payeeEdit.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="payee.id" value="${payee.id }" id="id" />
        <input type="hidden" name="payee.bankCode" value="${payee.bankCode }" id="bankCode" id="bankCode" />
        <input type="hidden" name="payee.sabkCode" value="${payee.sabkCode}" id="sabkCode" />
        <input type="hidden" name="payee.sabkName" value="${payee.sabkName }" id="sabkName" />
        <input type="hidden" name="payee.cnapsCode" value="${payee.cnapsCode }" id="cnapsCode" />
        <input type="hidden" name="payee.cnapsName" value="${payee.cnapsName }" id="cnapsName" />
       
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">收款人姓名:</span>
              <div class="input-wrap">
                <input type="text" name="payee.accountName" id="cardName" class="input-text" value="${payee.accountName }" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">身份证号码:</span>
              <div class="input-wrap">
                <input type="text" name="payee.idCardNo" id="idCardNo" value="${payee.idCardNo}" class="input-text"  required idCard>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">收款账户类型:</span>
              <div class="input-wrap">
                <dict:select value="${payee.accountType.name() }" styleClass="input-select" id="accType" dictTypeId="PAYEE_TYPE" name="payee.accountType"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">银行卡号:</span>
              <div class="input-wrap">
                <input type="text"  name="payee.accountNo" id="accountNo" class="input-text" value="${payee.accountNo}" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">开户行信息:</span>
              <div class="input-wrap mr-10">
                <input type="text" class="input-text" style="width: 280px;" name="payee.openBankName" id="openBankName" value="${payee.openBankName}" placeholder="请输入地区、分行支行名称等关键词进行匹配" required>
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
