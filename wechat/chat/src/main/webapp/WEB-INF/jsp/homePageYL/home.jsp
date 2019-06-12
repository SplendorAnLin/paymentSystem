<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
		<!--css初始化样式-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/common.css" />
		<!--通用样式-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/index.css?author=zhupan&v=171201" />
		<!--页面样式-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/assest/css/home/home.css?author=zhupan&v=171201" />
		<!--自适应函数-->
		  <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/response.js?author=zhupan&v=171201"></script>
		  <script type="text/javascript" src="${pageContext.request.contextPath}/js/public/jquery-1.8.3.min.js"></script>
		<!--轮播css和js-->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/public/css/swiper/swiper.min.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/public/swiper/swiper.min.js"></script>
		<title>主页</title>
	</head>

	<body>
		<header>
			<div class="header">
				渤海银行
			</div>
		</header>

		<section>
			<div class="swiper-container" style="height: 2.5rem;">
				<div class="swiper-wrapper">
					<div class="swiper-slide"><img class="tuceng21" src="${pageContext.request.contextPath}/image/public/banner/banner1.png" /></div>
					<div class="swiper-slide"><img class="tuceng21" src="${pageContext.request.contextPath}/image/public/banner/banner2.png" /></div>
				</div>
				<div class="swiper-pagination"></div>
			</div>
			<!--主菜单-->
			<div class="menu_lo3">
			<ul>
				<li>
					<a href="toGathring" target="_top">
						<img src="${pageContext.request.contextPath}/image/public/web_ico/qrCode.png" />
						<p class="fo_gray">微信收款</p>
					</a>

				</li>
				<li>
					<a href="toStoreQrcode" target="_top">
						<img src="${pageContext.request.contextPath}/image/public/web_ico/icon47_ewm.png" />
						<p class="fo_gray">店二维码</p>
					</a>
				</li>
				<li>
					<a href="toAccountDetails" target="_top">
						<img src="${pageContext.request.contextPath}/image/public/web_ico/icon52_moneydetials.png" />
						<p class="fo_gray">账户明细</p>
					</a>
				</li>
				<li>
					<a href="toTallyOrder" target="_top">
						<img src="${pageContext.request.contextPath}/image/public/web_ico/icon51_orderout.png" />
						<p class="fo_gray">结算订单</p>
					</a>
				</li>
				<li>
					<a href="toSelectTradeOrder" target="_top">
						<img src="${pageContext.request.contextPath}/image/public/web_ico/icon45_orderin.png" />
						<p class="fo_gray">收款订单</p>
					</a>
				</li>
			</ul>
			</div>
		</section>
		<form action="weeklySales">
			<input name="ownerId" type="hidden" id="ownerId" value="${ownerId }"/>
			<input name="customerNo" type="hidden" id="customerNo" value="${customerNo }"/>
			<input name="userName" type="hidden" id="userName" value="${userName }"/>
		</form>
		<!--轮播js-->
		<script type="text/javascript">
			var mySwiper = new Swiper('.swiper-container', {
				autoplay: 3000, //可选选项，自动滑动
				loop: true,
				pagination: '.swiper-pagination',
				autoplayDisableOnInteraction: false,
				paginationClickable: true,
				simulateTouch: false,
				observer: true, //修改swiper自己或子元素时，自动初始化swiper
				observeParents: true, //修改swiper的父元素时，自动初始化swiper
			})
		</script>
	</body>
</html>