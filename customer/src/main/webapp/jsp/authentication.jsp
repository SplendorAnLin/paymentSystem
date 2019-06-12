<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="yes" name="apple-touch-fullscreen" />
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <meta name="renderer" content="webkit|ie-comp|ie-stand">
  <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
  <link rel="stylesheet" href="./css/profile/rest.css">
  <link rel="stylesheet" href="./css/plugin/font-awesome.css">
  <link rel="stylesheet" href="./css/profile/restPWD.css">
  <title>找回密码</title>
  <script>
  var contextPath = "${pageContext.request.contextPath }";
  </script>
</head>
<body>
  
  <div class="hader">
    <div class="header-layout">
      <h1 class="logo" id="logo"><a href="#">首页</a></h1>
      <p class="title">找回密码</p>
      <ul class="header-nav">
        <li class="first"><a href="#">回到首页</a></li>
        <li><a href="${pageContext.request.contextPath }/login.action">回到登陆</a></li>
      </ul>
    </div>
  </div>

  <div class="content">
    <div class="content-layout">
      <div class="maincenter">
        <div class="maincenter-box">
          <div class="tiptext" data-type="error">
            <p><i class="fa"></i><span class="tiptext-message">请先输入手机号!</span><span class="timeTips"></span></p>
          </div>
          <form class="ui-form" action="randomResetpassword.action" onsubmit="return toSubmit();">
              <div class="form-item">
                <label class="form-label">登陆名:</label>
                <input class="form-input" name="phone" id="phone" type="number" oninput="if(value.length>11)value=value.slice(0,11)" placeholder=" 填写您的登陆手机号 ">
                <span class="text-message" id="phone-tips">手机号码无效</span>
              </div>
              <div class="form-item">
                  <label class="form-label">验证码:</label>
                  <input class="form-input" name="code" readonly type="text">
                  <span class="form-text" id="send-verif">发送验证码</span>
              </div>
              <div class="form-item">
                <input type="submit" value=" 确定 " readonly class="ui-button primary size" id="submitBtn" disabled>
              </div>
            </form>
        </div>
      </div>
    </div>
  </div>


  <script src="./js/plugin/jquery-1.8.3.js"></script>
  <script src="./js/profile/restPWD.js"></script>
</body>
</html>