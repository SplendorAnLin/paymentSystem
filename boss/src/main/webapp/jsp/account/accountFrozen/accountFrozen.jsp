<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户资金冻结</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="accountFrozenAction.action" method="post"  prompt="DropdownMessage"  data-success="冻结成功" data-fail="冻结失败">
        <input type="hidden" id="balance" value="<%=request.getParameter("balance")%>">
        <div class="fix">
      <div class="item">
        <div class="input-area">
          <span class="label w-90">账户编号:</span>
          <div class="input-wrap">
            <input type="text" class="input-text" id="accountNo" name="accountNo" readonly="readonly" value="<%=request.getParameter("id")%>">
          </div>
        </div>
      </div>
      <div class="item">
        <div class="input-area">
          <span class="label w-90">冻结金额:</span>
          <div class="input-wrap">
            <input type="text" class="input-text" id="amount" name="amount" required amount data-ignorezero="true" maxIgnoreEmpy="#balance" message='{"maxIgnoreEmpy": "冻结金额不能超过当前可用余额"}' >
          </div>
        </div>
      </div>
      <div class="item">
        <div class="input-area">
          <span class="label w-90">冻结期限:</span>
          <div class="input-wrap">
            <input type="text" class="input-text input-date" name="freezeLimit" required>
          </div>
        </div>
      </div>
      <div class="item">
        <div class="input-area">
          <span class="label w-90">冻结原因:</span>
          <div class="input-wrap">
            <input type="text"  name="adjustReason" class="input-text" placeholder="输入您的冻结原因">
          </div>
        </div>
      </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">冻 结</button>
        </div>
      </form>
    </div>
  </div>
  
  <%@ include file="../../include/bodyLink.jsp"%>
  <script>
  $(document).ready(function() {
    // 获取表单验证对象实例 
    var customValidator = window.getValidator($('form'));
    // 挂接验证钩子
    customValidator.options.onCustomize = function() {
      var accountNo = $("#accountNo").val();
      var amount = $("#amount").val();
      
      var msg = utils.tpl('您真的要给编号为: <span class="text-secondary">{{accountNo}}</span> 的账号冻结 <span class="text-secondary">{{amount}}</span>元吗?', {
        accountNo: accountNo,
        amount: amount
      });

      new window.top.Confirm(msg, '请确认', function() {
        customValidator.submit(true);
      }, null, null, true);
      
      return false;
    };
  });
  </script>
</body>
</html>
