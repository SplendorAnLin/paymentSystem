﻿<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="yes" name="apple-touch-fullscreen" />
  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta name="Description" content="">
  <meta name="Keywords" content="">
  <meta content="telephone=no,email=no" name="format-detection" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="renderer" content="webkit|ie-comp|ie-stand">
  <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
  <link rel="stylesheet" href="./css/profile/login_new.css">
  <link rel="shortcut icon" href="./images/favicon.ico">
  <title>商户管理系统 - 登陆</title>
  <style>
    .bg_1 {
      background-image: url(./images/login/banner_1.jpg);
      background-position: center;
      /*background-size: 100% 100%;*/
    }
    input:-webkit-autofill {
      -webkit-box-shadow: 0 0 0px 1000px white inset;
      border-width: 0 0 1px !important;
      border-style: solid !important;
      border-color: rgba(0, 0, 0, 0.12) !important;
    }
  </style>
</head>
<body>

<div class="main-view">
  <div class="header">
    <div class="header-in">
      <div class="logo-box">
        <a style="display: block;height: 100%;" class="ib" href="#" target="_blank">
          <img style="max-height: 100%;" src="./images/head-logo1.png" alt="">
        </a>
      </div>
      <ul class="links">
        <li class="app_download">
          <a href="#">商家APP</a>
          <%--<img class="arrow-down" src="./images/login/uiki_down_icon.png"></a>--%>
          <%--<dl class="qr-code-box">--%>
          <%--<dt><img src="images/login/download_code.png"></dt>--%>
          <%--</dl>--%>
        </li>
        <li class="app_download">
          <a href="#">关注我们</a>
          <%--<img class="arrow-down" src="./images/login/uiki_down_icon.png"></a>--%>
          <%--<dl class="qr-code-box">--%>
          <%--<dt><img src="images/login/wechat_code.jpg"></dt>--%>
          <%--</dl>--%>
        </li>
      </ul>
    </div>
  </div>
  <div class="login_box">
    <div class="login_plan">
      <!-- banner轮换 -->
      <div class="banner">
        <ul>
          <li class="bg_1 active"></li>
        </ul>
      </div>

      <!-- 登陆面板 -->
      <div class="login-box">
        <div class="login_page_wrapper">
          <div class="login_card">
            <div class="md-card-content">
              <ul class="tap_login_con">
                <li>
                  <input type="hidden" id="errMsg" value="${errorMsg}" />
                    <input type="hidden" id="TenantId" name="TenantId" value="" />
                  <form class="form-horizontal" id="loginForm" method="post" role="form" action="operatorLogin.action">
                    <div class="form-row">
                      <div class="md-input-wrapper">
                        <label for="username">用户名</label>
                        <input type="text" name="auth.username" id="username" autocomplete="off">
                        <span class="md-input-bar"></span>
                      </div>
                    </div>
                    <div class="form-row">
                      <div class="md-input-wrapper">
                        <label for="password">密码</label>
                        <input type="password" name="auth.password" id="password" >
                        <span class="md-input-bar"></span>
                      </div>
                    </div>
                    <div class="form-row">
                      <div class="md-input-wrapper">
                        <label for="checkCode">验证码</label>
                        <input type="text" name="checkCode" id="checkCode" maxlength="6" size="6">
                        <img class="verifyimg" alt="点击刷新验证码" src="captcha.svl" style="cursor:pointer;width:48%; height:35px;" onclick="this.src='captcha.svl?d='+new Date()*1"
                             title="点击刷新验证码">
                        <span class="md-input-bar"></span>
                      </div>
                    </div>
                    <select id="cookiename" name="cookiename" style="display:none;">
                      <option value="86400"></option>
                      <option value="604800"></option>
                      <option value="2592000"></option>
                      <option value="7776000"></option>
                      <option value="31536000"></option>
                      <option value="999999999"></option>
                    </select>
                    <div class="error-message">
                      <p>验证码输入错误</p>
                    </div>
                    <div class="button-box">
                      <button id="submit-btn" onclick="submitForm(this);" style="display:block;width: 100%;" class="md-btn md-btn-primary" href="#">登陆</button>
                    </div>
                    <div class="text-box">
                      <a class="fr" href="toForgotPassword" target="_blank">忘记密码</a>
                    </div>
                  </form>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="footer">
    <div class="footer-in">
      <p class="copyright">© 2007-2018 pay.feiyijj.com</p>
    </div>
  </div>
</div>
<script src="./js/plugin/jquery-1.8.3.js"></script>
<script>
    if (self != top) {
        top.location.href = self.location.href;
    }
    $('.md-input-wrapper input').focus(function() {
        $(this).closest('.md-input-wrapper').addClass('md-input-focus');
    });
    $('.md-input-wrapper input').blur(function() {
        $(this).closest('.md-input-wrapper').removeClass('md-input-focus');

        if (this.value !== '') {
            $(this).closest('.md-input-wrapper').addClass('md-input-filled');
        } else {
            $(this).closest('.md-input-wrapper').removeClass('md-input-filled');
        }

    });


    function showErrorMsg(msg) {
        $('.error-message p').text(msg);
        $('.error-message').show();
        $('#submit-btn').removeClass('disabled');
    }

    function hideErrorMsg() {
        $('.error-message').hide();
    }

    function submitForm(btn) {
        $(btn).addClass('disabled');
        if ($("#username").val() == "") {
            showErrorMsg("用户名不能为空！");
            return false;
        }
        if ($("#password").val() == "") {
            showErrorMsg("密码不能为空！");
            return false;
        }
        if ($("#checkCode").val().length != 4) {
            showErrorMsg("验证码必须4位数！");
            return false;
        }

        $(btn).closest('form').submit();
    }

    $(document).ready(function() {
        var errmsg = $("#errMsg").val();
        if (errmsg != null && errmsg != '') {
            showErrorMsg(errmsg);
            var password = $('#password')[0];
            password.type = 'text';
            password.value = '';
            password.type = 'password';
        }
    });



    $('.md-input-wrapper input').each(function() {
        var input = $(this);
        if (this.value != '')
            input.closest('.md-input-wrapper').addClass('md-input-filled');
    });
    $('#username').focus();
</script>
</body>
</html>