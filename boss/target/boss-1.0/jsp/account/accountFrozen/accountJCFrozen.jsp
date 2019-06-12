<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户资金解冻</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="accountJCFrozenAction.action"  method="post" prompt="DropdownMessage"  data-success="解冻成功" data-fail="解冻失败">
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
          <span class="label w-90">冻结编号:</span>
          <div class="input-wrap">
            <input type="text" class="input-text" name="freezeNo" required checkFreezNo trigger='{"checkFreezNo": 2}'>
          </div>
        </div>
      </div>
      <div class="item">
        <div class="input-area">
          <span class="label w-90">解冻原因:</span>
          <div class="input-wrap">
            <input type="text"  name="adjustReason" class="input-text" placeholder="输入您的解冻原因">
          </div>
        </div>
      </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">解 冻</button>
        </div>
      </form>
    </div>
  </div>

  
  <%@ include file="../../include/bodyLink.jsp"%>
  <script>
  
  var accountNo = $('#accountNo').val();
  // 检测冻结编号是否存在
  Compared.add('checkFreezNo', function(val, params, ele, ansyFn) {
    Api.boss.checkFreezNo(val, function(accNo) {    	
      ansyFn(Compared.toStatus(accNo == accountNo, '冻结编号不存在!'));
    });
  });
  
  $(document).ready(function() {
    // 获取表单验证对象实例 
    var customValidator = window.getValidator($('form'));
    // 挂接验证钩子
    customValidator.options.onCustomize = function() {
      var accountNo = $("#accountNo").val();

      var msg = utils.tpl('您真的要给编号为: <span class="text-secondary">{{accountNo}}</span> 的账号解冻吗?', {
        accountNo: accountNo
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
