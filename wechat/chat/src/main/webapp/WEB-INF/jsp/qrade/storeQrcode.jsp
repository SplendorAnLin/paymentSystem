<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="renderer" content="webkit">
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/common.css?author=zhupan&v=171201"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css?author=zhupan&v=171201" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js?author=zhupan&v=171201"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/assest/qrcode/jquery.qrcode.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/assest/css/qrcode/storeQrcode.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/assest/qrcode/html2canvas.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
		<title>店二维码</title>

	</head>

	<body id="body">
		<header>
			<div class="goback" style="z-index: 3;">
				<a onClick="window.history.go(-1);"><img src="${pageContext.request.contextPath}/image/public/web_ico/back.png"/></a>
				店二维码
			</div>
		</header>
		<div class="body">
			<img class="bodyimg" src="${pageContext.request.contextPath}/image/assest/qcordelast.png" />
			<div class="qrcode"></div>
		</div>
		<div class="savepic">长按保存图片</div>
		<!--操作提示start-->
		<div class="weui_dialog_alert" style="display: none;">
			<div class="weui-mask_transparent"></div>
			<div class="weui-toast">
				<i class="weui_icon_warn weui-icon_toast"></i>
				<p style="margin-top: 10px" class="weui-toast__content" id="responseMsg">已完成</p>
			</div>
		</div>
		<!--操作提示end-->
		<!--操作中提示start-->
		<div id="loadingToast" style="display:none">
			<div class="weui-mask_transparent" style="background: rgba(0,0,0,0.2);"></div>
			<div class="weui-toast">
				<i class="weui-loading weui-icon_toast"></i>
				<p class="weui-toast__content">加载中</p>
			</div>
		</div>
		<!--操作中提示end-->
		<form action="weeklySales">
			<input name="ownerId" type="hidden" id="ownerId" value="${ownerId }"/>
			<input name="customerNo" type="hidden" id="customerNo" value="${customerNo }"/>
			<input name="userName" type="hidden" id="userName" value="${userName }"/>
		</form>
		<script type="text/javascript">
			$(document).ready(function() {
				window.onload=function(){
                 $.ajax({
					 type:"post",
					 url:"getQRcode",
					 data:{"ownerId":$("#ownerId").val(),
						 "customerNo":$("#customerNo").val(),
						 "userName":$("#userName").val()
					 },
                     beforeSend:function(){$("#loadingToast").fadeIn("fast")},
					 success:function(data){
                         $(".qrcode").qrcode(data.responseData.QRcodeUrl);
                         var w = $(".body").width();
                         var h = $(".body").height();

                         //要将 canvas 的宽高设置成容器宽高的 2 倍
                         var canvas = document.createElement("canvas");
                         canvas.width = w * 2;
                         canvas.height = h * 2;
                         canvas.style.width = w + "px";
                         canvas.style.height = h + "px";

                         var context = canvas.getContext("2d");
                         //然后将画布缩放，将图像放大两倍画到画布上
                         context.scale(2, 2);
                         var str = $('.body');
                         html2canvas([str.get(0)], {
                             canvas: canvas,
                             onrendered: function(canvas) {
                                 var image = canvas.toDataURL("image/png");
                                 var pHtml = "<img src=" + image + " />";
                                 $('.body').html(pHtml);
                                 $(".body img").css("width", 100 + "%");
                                 $(".body img").css("height", 100 + "%");
                             }
                         });
					 },
                     complete:function(){$("#loadingToast").fadeOut("fast")}
				 })


				}
				
			});
		</script>
	</body>

</html>