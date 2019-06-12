<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS绑定</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/posBind.action" method="post" id="form" prompt="DropdownMessage"  data-success="绑定成功" data-fail="绑定失败">
        <input type="hidden" name="pos.id" value="<%=request.getParameter("pos.id")%>" />
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所属商户:</span>
              <div class="input-wrap">
                <input type="text" name="agentNo" class="input-text" required checkPosCustomerNo trigger='{"checkPosCustomerNo": 2}' />
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">代理商户:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" id="fullName" disabled />
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">新 增</button>
        </div>
      </form>
    </div>
  </div>

  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    var fullNameInput = $('#fullName');
    // 检测商户编号
    Compared.add('checkPosCustomerNo', function(val, params, ele, ansyFn) {
      // TODO 之后移入 api.js 中
      utils.ajax({
    	  url: 'checkPosCustomerNo.action?customerNo=' + val,
    	  dataType: 'json',
    	  success: function(customerInfo) {
    		  if (customerInfo == 'false') {
    			  ansyFn(Compared.toStatus(false, '商户编号不存在!'));
    			  fullNameInput.val('');
    		  } else {
    			  fullNameInput.val(customerInfo.fullName);
    			  ansyFn(Compared.toStatus(true));
    		  }
    	  },
    	  error: function() {
    		  fullNameInput.val('');
    		  Messages.error('检测商户编号失败, 请稍后重试...');
    	  }
      });
    });
  </script>
</body>
</html>
