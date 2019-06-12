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
		<!--微信自带样式-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/weui.css"/>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/weui/dist/style/weui.css"/>
		<!--css初始化-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/common.css?author=zhupan&v=171201" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css?author=zhupan&v=171201" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/assest/qrcode/jquery.qrcode.min.js"></script>
		<!--页面样式-->
		<!--页面自适应js-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js?author=zhupan&v=171201"></script>
	
		<title>二维码收款</title>
		<style type="text/css">
			body{	padding: 0.15rem;
				font-size: 0.2rem;
				color: #000000;
				background: #1B82D2;
				overflow: hidden;
			}
			.setmoney{
				font-size: 0.5rem;
				font-weight: 800;
				height: 1rem;
				line-height: 1rem;
				margin: 0.2rem 0;
			}
			.container{
				padding: 0.2rem;
			}
		     .txe_center{
		     	text-align: center;
		     }
		     .f_blue{
		     	color: rgba(1,113,225,1);
		     }
		     .qrcode{
		     	width: 3rem;
			    margin: auto;
			    height: 3rem;
		     }
		     .qrcode canvas{
		     	width: 100%;
		     	height: 100%;
		     }
		     .weui_btn{
		     	background: rgba(57,150,230,0.3);
			    box-shadow: 4px 4px 4px rgba(0,0,0,0.3);
			    width: 80%;
		     }
		</style>
	</head>
	<body>
		<div class="container bg_white">
			<p class="f_blue">二维码收款</p>
			<p class="txe_center">扫一扫向我付款</p>
			<p class="setmoney txe_center">￥<span>${amount}</span></p>
			<div class="qrcode"></div>
		</div>

		<div class="page-bd-15" style="margin-top: 0.6rem;">
			<a href="toGathring"  class="weui_btn bg-green" >重新输入金额</a>
		</div>

		<div class="page-bd-15" style="margin-top: 0.3rem;">
			<a href="toWelcome" class="weui_btn" >取消收款</a>
		</div>
		<input type="hidden" id="url" name="url" value="${url}">

		<script type="text/javascript">
			$(".qrcode").qrcode($("#url").val());
		</script>
	</body>
</html>
