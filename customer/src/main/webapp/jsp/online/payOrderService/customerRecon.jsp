<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>对账单下载</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body>
  
  <form class="validator" action="${pageContext.request.contextPath}/customerRecon.action" method="post" prompt="message"  style="padding: 2em 0.4em 0.4em;">

    <div class="fix">
      <div class="item">
        <div class="input-area">
          <span class="label w-90">对账单类型:</span>
          <div class="input-wrap">
            <%--<dict:select value="ALL" styleClass="input-select" dictTypeId="SYS_CODE_ALL" name="sumInfo"></dict:select>--%>
            <input type="text"  name="sumInfo" required value="ALL">
          </div>
        </div>
      </div>
      <div class="item">
        <div class="input-area">
          <span class="label w-90">对账单时间:</span>
          <div class="input-wrap">
            <input type="text" class="input-text input-date default-date" name="orderTimeStart" required date>
          </div>
        </div>
      </div>
    </div>

    <div class="text-right" style="width: 278px;">
      <button class="btn btn-submit">下载对账单</button>
    </div>

  </form>
  
  <%@ include file="../../include/bodyLink.jsp"%>
  <script>
  $(window).load(function() {
    // 获取表单验证对象实例 
    var formValidator = window.getValidator($('form'));
    formValidator.options.onCustomize = function() {
    	$(document.body).showLoadImage(null, true);
    	setTimeout(function() {
    		$(document.body).hideLoadImage(null, true);
    	}, 1500);
    	return true;
    };
  });

  </script>
</body>
</html>
