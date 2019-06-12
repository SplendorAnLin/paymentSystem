<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico">
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<!--[if lt IE 9]>
<script type="text/javascript" src="js/html5.js"></script>
<script type="text/javascript" src="js/respond.min.js"></script>
<script type="text/javascript" src="js/PIE_IE678.js"></script>
<![endif]-->
<link href="css/H-ui.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.login.css" rel="stylesheet" type="text/css" />
<!--[if IE 6]>
<script type="text/javascript" src="js/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>运营后台 - 登录</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.1.8.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {

		/* var flag = $("#flag").val();
		if (flag == '0') {
			$("#vcode").hide();
		}

		var isSuccess = $("#status").html();
		if (isSuccess != null) {
			$("#flag").val('1');
			$("#vcode").show();
		} */

		var errmsg = $("#errMsg").val();
		if (errmsg != null && errmsg != '') {
			alert(errmsg);
		}

	});

	function submitForm() {
		if ($("#flag").val() == '1') {
			if ($("#username").val() == "") {
				alert("用户名不能为空！");
				return false;
			}
			if ($("#password").val() == "") {
				alert("密码不能为空！");
				return false;
			}
			if ($("#yzm").val() == "") {
				alert("验证码不能为空！");
				return false;
			}
		} else {
			if ($("#username").val() == "") {
				alert("用户名不能为空！");
				return false;
			}
			if ($("#password").val() == "") {
				alert("密码不能为空！");
				return false;
			}
		}
		$("#loginForm").submit();
	}
	function dialogMessage(info) {
		$("#dialog:ui-dialog").dialog("destroy");
		$("#dialog-message-info").html(info);
		$("#dialog-message").dialog({
			modal : true,
			buttons : {
				Ok : function() {
					$(this).dialog("close");
				}
			}
		});
	}
	
	function changeYzm(){
		var yzm = document.getElementById("captcha");
		yzm.src = 'captcha.svl?d='+new Date()*1;
	}
</script>
</head>
<body>
	<input type="hidden" id="errMsg" value="${errorMsg}"/>
	<input type="hidden" id="TenantId" name="TenantId" value="" />
	<div class="header"><div style="font-size: 25px;color: white;margin-top: 10px;margin-left: 20px;">运营管理系统</div></div>
	<div class="loginWraper">
		<div id="loginform" class="loginBox">
			<form id="loginForm" action="operatorLogin.action" method="post">
				<div class="formRow user">
					<input id="username" name="auth.username" type="text" placeholder="用户名"
						class="input_text input-big">
				</div>
				<div class="formRow password">
					<input id="password" name="auth.password" type="password" placeholder="密 码"
						class="input_text input-big">
				</div>
				<div class="formRow yzm">
					<input id="yzm" name="checkCode" class="input_text input-big" type="text" maxlength="4" placeholder="验证码"
						onBlur="if(this.value==''){this.value='验证码'}"
						onClick="if(this.value=='验证码'){this.value='';}" value="验证码"
						style="width: 150px;">&nbsp;&nbsp;&nbsp;
					<img id="captcha" src="captcha.svl" style="height:42px;" onclick="this.src='captcha.svl?d='+new Date()*1"/>
					<a id="kanbuq" href="javascript:changeYzm();">看不清，换一张</a>
				</div>
				<div class="formRow">
					<input name="" onclick='javascript:submitForm();' type="button" class="btn radius btn-success btn-big"
						value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;"> 
					<input
						name="" type="reset" class="btn radius btn-default btn-big"
						value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
				</div>
			</form>
		</div>
	</div>
	<div class="footer">现代金控</div>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/H-ui.js"></script>

</body>
</html>