<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>修改账户密码</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 378px; padding: 0.2em;">

  <div class="query-box" style="padding: 0.8em 3em;">
    <!--查询表单-->
    <div class="query-form">
      <form class="validator ajaxFormNotification" action="${pageContext.request.contextPath}/operatorPasswordUpdate.action" method="post" prompt="DropdownMessage" data-success="修改密码成功" data-fail="修改密码失败" >
 
        <div class=" mt-10 fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">账户号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="operator.username" readonly="readonly" value="${sessionScope.auth.username}">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">原密码:</span>
              <div class="input-wrap">
                <input type="password" class="input-text" id="original" name="operator.password" required rangelength='[6,16]'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">新密码:</span>
              <div class="input-wrap">
                <input type="password" class="input-text" id="newpass" name="newpassword" required rangelength='[6,16]' notEqual='#original' message='{"notEqual": "不能与原密码相同"}'>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">重复新密码:</span>
              <div class="input-wrap">
                <input type="password" class="input-text" name="newpasswordconfirm" required rangelength='[6,16]' equalTo='#newpass' message='{"equalTo": "与新密码不相同"}'>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10 hidden">
          <button class="btn btn-submit">提 交</button>
        </div>
      </form>
    </div>
  </div>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
  var form = $('form');
  // 挂接表单结果提示钩子
  form.data('receiver-hook', function(msg, closeDialog) {
      switch(msg) {
        case 'true':
          closeDialog(true);
          break;
        case 'old_error':
          closeDialog(false, '原密码错误，密码修改失败!');
          break;
        default:
          closeDialog(false, '密码修改失败!');
      }
  });
  </script>
</body>
</html>
