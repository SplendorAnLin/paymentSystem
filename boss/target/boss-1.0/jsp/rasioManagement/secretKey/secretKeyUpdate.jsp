<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改密钥</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/secretKeyUpdate.action" method="post" id="form" prompt="DropdownMessage"  data-success="修改成功" data-fail="修改失败">
        <input type="hidden" name="secretKey.id" value="${secretKey.id }" />
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">keyName:</span>
              <div class="input-wrap">
                    <input type="text" class="input-text" name="secretKey.keyName"  value="${secretKey.keyName }"  readonly>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">key:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="secretKey.key" maxlength="16" minlength="16" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" value="${secretKey.key }"  required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">checkValue:</span>
              <div class="input-wrap">
                <input type="text" class="input-text fee" name="secretKey.checkValue" readonly="readonly" value="${secretKey.checkValue }" required>
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
  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
