<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../include/commons-include.jsp" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="renderer" content="webkit">
		<!--不要缓存-->
		<META HTTP-EQUIV="pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
		<META HTTP-EQUIV="expires" CONTENT="0">
		<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
		<meta content="yes" name="apple-mobile-web-app-capable">
		<meta content="black" name="apple-mobile-web-app-status-bar-style">
		<meta content="telephone=no" name="format-detection">
		<meta name="format-detection" content="telephone=no">
		<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<!-- uc强制竖屏 -->
		<meta name="screen-orientation" content="portrait">
		<!-- QQ强制竖屏 -->
		<meta name="x5-orientation" content="portrait">
		<!-- UC强制全屏 -->
		<meta name="full-screen" content="yes">
		<!-- QQ强制全屏 -->
		<meta name="x5-fullscreen" content="true">
		<!-- UC应用模式 -->
		<meta name="browsermode" content="application">
		<!-- QQ应用模式 -->
		<meta name="x5-page-mode" content="app">
		<!-- windows phone 点击无高光 -->
		<meta name="msapplication-tap-highlight" content="no">
		<!-- home screen app 全屏 -->
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta http-equiv="x-rim-auto-match" content="none">
		<!--样式初始化-->
		<link rel="stylesheet" href="css/common.css" />
		<!--通用样式-->
		<link rel="stylesheet" href="css/basic.css" />
		<!--页面样式-->
		<link rel="stylesheet" href="css/pay_result_page.css" />
		<link rel="stylesheet" href="css/payment_pay.css" />
		<!--页面自适应函数-->
		<script type="text/javascript" src="js/response.js"></script>
		
		<title></title>
		<style type="text/css">
		
		</style>
	</head>
	<body oncontextmenu="return false" onselectstart="return false" style="overflow: hidden;">
		<header >
			<div class="header">
			<div class="header_title">
				<span>订单成功</span>
			</div>
			</div>
			<div class="sys-header theme-bg absolute">
				<div class="logo fl">
					<img class="toggle-drawerNav" src="image/head-logo.png" alt="">
				</div>
				<div class="fr">
					<ul class="links fl">
						<li>
							<a target="_blank" href="javascript:void(0);">聚合运营管理系统</a>
						</li>
						<li>
							<a target="_blank" href="javascript:void(0);">API文档</a>
						</li>
						<li>
							<a target="_blank" href="javascript:void(0);">帮助</a>
						</li>
					</ul>
					<div class="control-panel fl">
						<div class="user-name f-13">
							<span>Hi, </span>
							<span>聚合支付有限公司</span>
						</div>
					</div>
				</div>
		</header>
		<section >
				
			<div class="success">
			<div class="pay_count"><img src="image/success_pay.png" /></div>	
			<div class="pay_count successok">恭喜，交易成功 </div>
			</div>
			<div class="list" >
						<div class="item" style="margin-top: 0.6rem;">
							<div class="pull_left font_18" style="width: 47%;">商户名称: </div>
							<div class="pull_right gray font_18" style="width: 49%;">湖北聚合</div>
						</div>
						<div class="item">
							<div class="pull_left font_18" style="width: 47%;">订单金额: </div>
							<div class="pull_right gray font_18" style="width: 49%;"><span>10.00</span> 元</div>
						</div>
					</div>
					<div class="foot_btn"> <a class="submit_ok" >返回</a></div>
		</section>
		<footer>
			<div class="footer">
					<a class="submit_ok">返回</a>
				</div>
		</footer>
	</body>
</html>
