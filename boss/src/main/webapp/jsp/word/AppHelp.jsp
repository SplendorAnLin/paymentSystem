<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="initial-scale=1, user-scalable=0, minimal-ui" charset="UTF-8">
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
	<!-- home screen app 全屏 -->
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta http-equiv="x-rim-auto-match" content="none">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/help/common.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/help/Title_main.css">
	<script src="${pageContext.request.contextPath}/js/help/response.js"></script>
	<title>帮助信息</title>
</head>
<body>
	<header>
		<h1>${protocolManagement.title }</h1>
		<p class="about_news" style="padding-bottom: 15px;">
			<img class="FTS_opt" src="${pageContext.request.contextPath}/images/icon_A.png" />
		</p>
		<!--<p class="about_news" style="padding-bottom: 15px;"><span>2017-08-29 10:00</span></p>-->
	</header>
	<section>${protocolManagement.content }</section>

  	<script src="${pageContext.request.contextPath}/js/plugin/jquery-1.8.3.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".FTS_opt").click(function() {
				if(!$(this).hasClass("hasclass")) {
					$(this).attr("src","${pageContext.request.contextPath}/images/icon_A-.png");
					$("section").css("font-size", "18px");
					$(this).addClass("hasclass");
				} else {
					$(this).attr("src","${pageContext.request.contextPath}/images/icon_A.png");
					$("section").css("font-size", "16px");
					$(this).removeClass("hasclass");
				}
			})
		})
	</script>
</body>
</html>