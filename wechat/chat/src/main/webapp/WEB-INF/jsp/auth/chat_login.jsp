<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/commons-include.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="format-detection" content="telephone=no">
	<link rel="dns-prefetch" href="//res.wx.qq.com">
	<link rel="dns-prefetch" href="//mmbiz.qpic.cn">
	<link rel="stylesheet" href="http://yl.bank-pay.com/gateway/css/QRCode/Modal.css">
    <link rel="shortcut icon" type="image/x-icon" href="https://res.wx.qq.com/mpres/zh_CN/htmledition/comm_htmledition/images/icon/common/favicon.ico">
    <link rel="stylesheet" href="https://res.wx.qq.com/mpres/htmledition/style/wap_style/page/home/login270806.css">
    <link rel="stylesheet" type="text/css" href="https://res.wx.qq.com/mpres/htmledition/style/wap_style/widget/page_tips270806.css"
    />
    <link rel="stylesheet" type="text/css" href="https://res.wx.qq.com/mpres/htmledition/style/wap_style/widget/tips/tips_light270806.css"
    />
    <title>用户绑定</title>
    <style>
    .login_form.cell_form .frm_vcode_cell {
        line-height: 3;
    }
    </style>
</head>
<body class="zh_CN page_login" >
    
    <div class="wx_login">
        <div class="con">
            <div class="page_hd">
                <div class="page_tit">用户绑定 </div>
            </div>
          <form id="js_form_login" class="login_form cell_form" name="loginForm" action="getverify">
					<div class="frm_ctrl login_main">
						<div class="cells cells_split login_input">
							<div class="cell">
								<div class="cell_bd cell_primary">
									<input type="text" placeholder="手机号" id="loginId" class="frm_input" name="phone">
								</div>
							</div>
							<div id="js_div_imgcode" class="cell frm_vcode_cell">

								<div class="cell_bd cell_primary">
									<input type="text" placeholder="验证码" class="frm_input" id="phoneCode"  name="smsCode">
								</div>
								<a href="javascript:;" id="send" class="imgcode_link" onclick="send(this);">获取验证码</a>
							</div>
						</div>
					</div>

					<div class="cells_split cells_form cells_select cells_checkbox remember_info">
						<label class="cell frm_select_label" for="js_input_remember">
			            <input type="checkbox" class="frm_select" name="checkbox1" id="js_input_remember" checked="checked">
			            <div class="cell_hd icon_selected_wrp">
			                <i class="icon_selected"></i>
			                <em class="icon_arrow"></em>
			            </div>
			            <div class="cell_bd cell_primary">
			                <p class="mt_v">记住我</p>
			            </div>
			        </label>

					</div>

					<div class="btn_outer">
						<a  class="btn actived btn_login"  href="javascript:void(0);" onclick="submitForm()">绑定</a>
						<!--<button class="btn actived btn_login" >绑定</button>-->
					</div>

				</form>
        </div>
        <div class="ft_menu">
				<a href="wap_reg.jsp;" target="http://mp.weixin.qq.com/mp/opshowpage?action=main#wechat_redirect">用户注册</a>&nbsp;&nbsp;&nbsp;
				<a href="${downloadUrl }">APP下载</a>
			</div>
    </div>
    <!--弹窗提示框star-->
		<div class="fullpage" style="position: absolute;top: 0;left: 0;width: 100%;height: 100%;background: rgba(0,0,0,0.3); z-index: 10;display: none;">
			<div class="diolog" style="position: relative; width: 200px;height: 90px;top: calc(50% - 75px); left: calc(50% - 100px);background: white;padding: 30px 0; border-radius: 5px;">
				<p style="text-align: center;"><img style="width: 40px;" src="image/success.png" /></p>
				<p style="text-align: center;">绑定成功！<span class="ast_ti">3</span>秒后自动返回</p>
				<p style="text-align: center; " class="close_full">关闭</p>
			</div>
		</div>
		<!--弹窗提示框star-->

<script src="js/jquery-1.8.3.js"></script>
<script src="http://yl.bank-pay.com/gateway/js/QRCode/utils.js"></script>
<script src="http://yl.bank-pay.com/gateway/js/QRCode/Modal.js"></script>
  <script type="text/javascript" src="${pageContext.request.contextPath}/js/wxbind.js"></script>
   <script>
   
   var phone = $('#loginId');
   var phoneCode = $('#phoneCode');
   var agree=$('#agree');
   // 验证手机号
   function checkPhone() {
	   return verification.phone(phone.val());
   }
   
   
  // 获取验证码
  function send(ele) {
	if (!checkPhone()) {
		return;
	}
    VerificaCode.send('getmessage?phone=' + phone.val(), function(time) {
      $(ele).text('剩余 ' + time + ' 秒');
    }, function() {
      $(ele).text('重新发送');
    }, 90);
  }
  
  // 提交
  function submitForm() {
	  
	 if (!checkPhone()) {
		alert('手机号不正确');
		return;
	}
	if(phoneCode.val() ==""){
		alert('验证码不能为空');
		return;
	}
	if (VerificaCode.get('getmessage?phone=' + phone.val()) != phoneCode.val()) {
		alert('验证码不正确');
		return;
	}
	 else
	 {
	 var smsCode=$('#phoneCode').val();
	 $.ajax({
			type : 'post',
			url : 'getverify',
			data : 'smsCode=' + smsCode+'&phone='+phone.val(),
			dataType:'json',
			success : function(msg) {
				//缺少手机验证码发送后的js验证
				if(msg==true){
					aa();
				}
				if(msg==false){
					alert("验证码错误");
				}
				
			}
		});
	 }
	
	
	
  }
  
  
/**
 *验证手机号码是否存在
 */
  $('#loginId').blur(function(){
	  var mobile=$('#loginId').val();
	  if(mobile!=""){
		  $.ajax({
				type : 'post',
				url : 'getphone',
				data : 'phone=' + mobile,
				success : function(Params) {//得到验证码 需要在页面上验证
					//缺少手机验证码发送后的js验证
					if(Params=="null"){
						alert("用户不存在");
					}
				}
			});
	  }else{
		  alert("手机号不能为空")
	  }
		});

	var dialog = $('<div></div>');
	$('.pact').click(function() {
		dialog.html('${content}');
		dialog.Alert('${title }');
	});
  
  </script>
  
  <script type="text/javascript">
			/*避免输入框顶上去乱页面start*/
			var oHeight = $(document).height(); //屏幕当前的高度
			$(window).resize(function() {
				if($(document).height() < oHeight) {
					$(".ft_menu").css("display", "none");
					$(".wx_login").css("background", "url(image/half_index_bg.jpg)")
				} else {
					$(".ft_menu").css("display", "block");
					$(".wx_login").css("background", "url(image/index_bg_1270806.jpg)")
				}
			});
			/*避免输入框顶上去乱页面end*/
			/*发送验证计时器start*/
			var curTime = 0,
				timer = null,
				btn = $('#send');
			btn.on('click', function() {
				if(timer === null) {
					curTime = 60;
					timer = setInterval(handleTimer, 1000);
				}
			});

			function handleTimer() {

				if(curTime == 0) {
					clearInterval(timer);
					timer = null;
					$(".imgcode_link").css("color", "#04be02;");
					btn.text('重新发送');
				} else {
					$(".imgcode_link").css("color", "#ccc");
					btn.text(curTime + 's后发送');
					curTime--;
				}
			}
			/*发送验证计时器end*/
			/*倒计时关闭start*/

			/*倒计时关闭end*/
			function aa() {
				$(".fullpage").css("display", "block");
				if($(".fullpage").css("display") == "block") {
					var times = 3;
					window.setInterval(function() {
						if(times == 0) {
							$(".fullpage").fadeOut('fast');
						} else {
							times--;
							$(".ast_ti").text(times);
						}
					}, 1000)
					window.location.href="personalRights";
				}
				$(".close_full").click(function() {
					$(".fullpage").fadeOut('fast');
					window.location.href="personalRights";
				})
			}
		</script>

</body>
</html>