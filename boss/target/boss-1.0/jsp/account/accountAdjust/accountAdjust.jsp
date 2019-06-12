<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户调帐</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="adjustmentAccountAction.action" method="post" prompt="DropdownMessage"  data-success="调帐成功" data-fail="调帐失败">
        <input type="hidden" id="balance" value="<%=request.getParameter("balance")%>" />
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="accountNo" id="accountNo" readonly="readonly" value="<%=request.getParameter("id")%>">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">资金标识:</span>
              <div class="input-wrap">
                <dict:select name="symbol" id="symbol" styleClass="input-select" dictTypeId="CAPITAL_IDENTIFICATION"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">调账金额:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="amount" id="amount" required amount checkAmount>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">调账原因:</span>
              <div class="input-wrap">
                <input type="text"  name="adjustReason" id="adjustReason" placeholder="输入您的调帐原因" class="input-text">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">分 配</button>
        </div>
      </form>
    </div>
  </div>


  <%@ include file="../../include/bodyLink.jsp"%>
  <script>
  
  // 检测可用金额
  Compared.add('checkAmount', function(val, params, ele, ansyFn) {
  	// 可用金额
  	var balance = parseFloat($('#balance').val());
  	// 调帐金额
  	var adjustAmount = parseFloat(val);
  	// 调帐上限 100w
  	var max = 1000000;
  	// 资金标识
  	var symbol = $('#symbol').val();
  	var result;
  	
    if (adjustAmount == 0) {
      return Compared.toStatus(false, '调帐金额不能为零!');
    }
  	if (symbol === 'PLUS') {
  		result = Compared.rules.range(val, [0, max]);
  		return Compared.toStatus(result.status, '调帐金额超过100w上限!');
  	} else if (symbol === 'SUBTRACT') {
  		result = Compared.rules.range(val, [0, balance]);
  		return Compared.toStatus(result.status, '调帐金额不能超过当前可用余额!');
  	}
  });
  
  
  $(document).ready(function() {
    // 获取表单验证对象实例 
    var customValidator = window.getValidator($('form'));
    // 挂接验证钩子
    customValidator.options.onCustomize = function() {
      var accountNo = $("#accountNo").val();
      var symbol = $("#symbol").currentOption().text();
      var amount = $("#amount").val();
      
      var msg = utils.tpl('您真的要给编号为: <span class="text-secondary">{{accountNo}}</span> 的账号{{symbol}} <span class="text-secondary">{{amount}}</span>元吗?', {
      	accountNo: accountNo,
      	symbol: symbol,
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
