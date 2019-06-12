<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>网点新增</title>
  <%@ include file="../../include/header.jsp"%>
  <style>
  .w-110 {
    width: 110px;
  }
  </style>
</head>
<body style="width: 410px; padding:0.2em;">
  
  <div class="query-box" style="padding: 0.8em 3em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator notification" action="${pageContext.request.contextPath}/shopAdd.action" method="post" id="form" prompt="DropdownMessage"  data-success="新增成功" data-fail="新增失败">
        <div class="fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-110">网点名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="shop.shopName" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-110">小票打印名称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="shop.printName" required>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-110">拨号POS绑定号码:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="shop.bindPhoneNo">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-110">所属商户:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customerNo"  required notChinese checkCustomerNo trigger='{"checkCustomerNo": 2}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-110">商户全称:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" id="fullNameInput" disabled placeholder="请填写所属商户">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-110">状态:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select sp-select" dictTypeId="STATUS" name="shop.status"></dict:select>
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
  <script>
    var fullNameInput = $('#fullNameInput');
    // 获取商户编号全称
    Compared.add('checkCustomerNo', function(val, params, ele, ansyFn) {
      Api.boss.findCustomerFullName(val, function(fullName) {
    	if (fullName == 'FALSE') {
    		fullNameInput.val('');
    	} else {
    		fullNameInput.val(fullName);
    	}
        ansyFn(Compared.toStatus(fullName != 'FALSE', '此商户不存在!'));
      });
    });
  </script>
</body>
</html>
