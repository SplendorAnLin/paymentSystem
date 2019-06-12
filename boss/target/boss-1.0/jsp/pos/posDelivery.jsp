<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS出库</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/posDelivery.action" method="post" id="form" prompt="DropdownMessage"  data-success="出库成功" data-fail="出库失败">
        <input type="hidden" name="pos.id" value="<%=request.getParameter("pos.id")%>" />
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">所属代理商:</span>
              <div class="input-wrap">
                <input type="text" name="agentNo" class="input-text" required checkPosAgentNo trigger='{"checkPosAgentNo": 2}' />
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">代理商全称:</span>
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
    // 检测代理商编号
    Compared.add('checkPosAgentNo', function(val, params, ele, ansyFn) {
      // TODO 之后移入 api.js 中
      utils.ajax({
    	  url: 'checkPosAgentNo.action?agentNo=' + val,
    	  dataType: 'json',
    	  success: function(agentInfo) {
    		  if (agentInfo == 'false') {
    			  ansyFn(Compared.toStatus(false, '代理商编号不存在!'));
    			  fullNameInput.val('');
    		  } else if (agentInfo == 'levelNotMatch') {
                  ansyFn(Compared.toStatus(false, '代理商必须为一级代理商!'));
                  fullNameInput.val('');
    		  } else {
    			  fullNameInput.val(agentInfo.fullName);
    			  ansyFn(Compared.toStatus(true));
    		  }
    	  },
    	  error: function() {
    		  fullNameInput.val('');
    		  Messages.error('检测代理商编号失败, 请稍后重试...');
    	  }
      });
    });
  </script>
</body>
</html>
