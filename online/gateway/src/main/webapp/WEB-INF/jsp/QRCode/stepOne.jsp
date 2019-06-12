<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>扫码入网</title>
<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/QRCode/register.css">
<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/QRCode/Modal.css">
<link rel="stylesheet" href="${applicationScope.staticFilesHost}/gateway/css/QRCode/font-awesome.css">
<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/flexible_wap.js"></script>
<style>
.input-row .in.last {
    border-bottom: none;
}
.maskTop {
	display: flex;
	position: absolute;
	left: 0;
	top: 0;
	width: 0;
	height: 0;
	background-color: rgba(0, 0, 0, 0.7);
    text-align: center;
    align-items: center;
    transition: opacity 0.3s;
    opacity: 0;
}
.maskTop.active {
	width: 100%;
	height: 100%;
	opacity: 1;
	z-index: 5;
}
.maskTop .i {
	align-self: center;
}
.load-block {
    width: 50px;
    height: 50px;
    font-size: 1.5em;
    flex: 1;
}
</style>
</head>
<body class="bc-lightGray">
	<div class="mobileFrame">
	
		<div class="maskTop">
			<div class="load-block c-red">
				<i class="fa fa-spinner fa-spin fa-3x fa-fw margin-bottom"></i>
			</div>
		</div>
		<div class="nav-warp">
			<ul class="row">
				<li class="col-4 active"><a href="javascript:void(0);">登陆信息</a></li>
				<li class="col-4"><a href="javascript:void(0);">基本信息</a></li>
				<li class="col-4"><a href="javascript:void(0);">结算信息</a></li>
			</ul>
		</div>
		<form:form class="validator" action="${applicationScope.staticFilesHost}/gateway/one" method="POST" >
			<input type="hidden" name="cardNo" value="${cardNo }">
			<input type="hidden" name="checkCode" value="${checkCode }">
			<div class="input-row">
				<div class="in row">
					<label class="col-3">手机号:</label> <input class="col-9"
						type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"
						 name="phone" maxlength="11" required phone checkPhone>
				</div>
			</div>
			<div class="input-row">
				<div class="in row">
					<label class="col-3">验证码:</label> <input class="col-6" onkeyup="this.value=this.value.replace(/\D/g,'')" 
					 onafterpaste="this.value=this.value.replace(/\D/g,'')" type="text" 
					  name="checkNum" maxlength="6" required checkVerificaCode>
						<a id="verCode" class="col-3 verCode" href="javascript:void(0);">获取验证码</a>
				</div>
			</div>
			<%-- <div class="input-row">
				<div class="in row">
					<label class="col-3">登陆密码:</label> <input class="col-9"
						id="password" type="password" minlength="6" maxlength="12"
						required placeholder="6-12位字母或数字组合">
				</div>
			</div>
			<div class="input-row">
				<div class="in row last">
					<label class="col-3">确认密码:</label> <input class="col-9"
						type="password" name="passWord" minlength="6" maxlength="12" required
						equalTo="#password">
				</div>
			</div> --%>
			<div class="btnWrap">
				<button class="btn-full">下一步</button>
			</div>
			<div class="btnWrap">
				<input type="checkbox" required id="agree" name="agree"  /> <label for="agree">同意</label> <a class="link pact" href="javascript:void(0);">${title }</a>
			</div>
			<%--<div class="btnWrap">--%>
				<%--<span class="contact">客服电话: <span>400-860-7199</span></span>--%>
			<%--</div>--%>
		</form:form>
	</div>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/jquery-1.8.3.js"></script>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/utils.js"></script>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/verification.js"></script>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/Modal.js"></script>
	<script src="${applicationScope.staticFilesHost}/gateway/js/QRCode/register.js"></script>
  <script>
  	var validator = $('.validator').data('Validator');
  	validator.options.submitHandler = function(from, status) {
  		if (status === true) {
  			$('.maskTop').addClass('active');
  		}
  	};
  	
	var phone = $('[name="phone"]');
    $.addMethod('checkVerificaCode', function(value, element, param, fn) {
/*       getVerifyUrl(function(url) {
    	  fn(value == VerificaCode.get(url));
      }); */
  	  return value.length == 6;
    }, '验证码必须为6位数字!');
    $('#verCode').click(function() {
      var element = $(this);

      getVerifyUrl(function(url) {
          VerificaCode.send(url, function(time) {
              element.text('剩余 ' + time + ' 秒');
            }, function() {
              element.text('重新发送');
            }, 90);
      });
    });
    $.addMethod('checkPhone', function(value, element, param, fn) {
		$.ajax({
			url : '${applicationScope.staticFilesHost}/gateway/checkPhone?phone=' + value,
			type : 'post',
			success : function(msg) {
				fn(msg == "TRUE");
			},
			error : function() {
				fn(false);
			}
		});
   	}, '手机号重复!', '等待中...');
			function getVerifyUrl(fn) {
				// 手机号验证通过,才能发送验证码
				validator.element(phone, function(status) {
					if (status) {
						fn('${applicationScope.staticFilesHost}/gateway/sendVerifyCode?phone='
								+ phone.val());
					} else {
						fn(null);
					}
				});
			}	
	var dialog = $('<div></div>');
	$('.pact').click(function() {
		dialog.html('${content}');
		dialog.Alert('${title }');
	});
		</script>
</body>
</html>