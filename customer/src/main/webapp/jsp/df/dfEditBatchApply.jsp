<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>代付信息修改</title>
  <%@ include file="../include/header.jsp"%>
</head>
<%
request.setCharacterEncoding("utf-8");
%>
<body style="width: 540px; padding:0.2em;">
  
  <div class="query-box" style="padding: 1.8em 3em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator stop-formeNnter"  id="form" prompt="DropdownMessage" >
        <input type="hidden" name="bankCode" id="bankCode" />
        <input type="hidden" name="sabkCode" id="sabkCode" />
        <input type="hidden" name="cardType" id="cardType" /> 
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">收款人姓名:</span>
              <div class="input-wrap">
                <input type="text" name="accountName" id="cardName" class="input-text" value="<%= new String(request.getParameter("accountName").getBytes("ISO-8859-1"),"UTF-8")%>" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">收款账户类型:</span>
              <div class="input-wrap">
                <dict:select name="accountType" styleClass="input-select" id="accType" dictTypeId="PAYEE_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">收款账号:</span>
              <div class="input-wrap">
                <input type="text" name="accountNo" id="accountNo" class="input-text" value="<%=request.getParameter("accountNo")%>" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">代付金额:</span>
              <div class="input-wrap">
                <input type="text" name="amount" id="amount" class="input-text" value="<%=request.getParameter("amount")%>" required amount>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">打款原因:</span>
              <div class="input-wrap">
                <input type="text" name="description" class="input-text" value="">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">开户行信息:</span>
              <div class="input-wrap mr-10">
                <input type="text" class="input-text" style="width: 280px;" name="openBankName" id="openBankName" value="<%= new String(request.getParameter("openBankName").getBytes("ISO-8859-1"),"UTF-8")%>" placeholder="请输入地区、分行支行名称等关键词进行匹配" required>
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
    	$('#accType').selectForValue('<%=request.getParameter("accountType")%>');
    	$('#cardName').selectForValue('<%=request.getParameter("accountName")%>');
    	
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
