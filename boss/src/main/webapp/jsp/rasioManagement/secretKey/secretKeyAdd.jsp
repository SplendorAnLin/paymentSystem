<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>新增密钥</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/secretKeyAdd.action" method="post" id="form" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <div class="fix">

          <div class="item">
            <div class="input-area">
              <span class="label w-90">keyName:</span>
              <div class="input-wrap">
                    <input type="text" class="input-text" name="secretKey.keyName"  required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">key:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="secretKey.key" maxlength="16" minlength="16" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" required>
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
