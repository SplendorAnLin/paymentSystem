<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改Mcc</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 400px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 4em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/mccAdd.action" method="post" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">Mcc:</span>
              <div class="input-wrap">
                    <input type="text" class="input-text" name="mccInfo.mcc" required checkMcc trigger='{"checkMcc": 2}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">类别:</span>
              <div class="input-wrap">
                <dict:select nullOption="false" styleClass="input-select" name="mccInfo.category" dictTypeId="CATEGORY"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">标准费率:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="mccInfo.rate" required amount>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">简称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="mccInfo.name" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">描述:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="mccInfo.description" required>
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
    Compared.add('checkMcc', function(val, params, ele, ansyFn) {
      Api.boss.checkMcc(val, function(msg) {
        ansyFn(Compared.toStatus(msg === 'false', 'Mcc已存在!'));
      });
    });
  </script>
</body>
</html>
