<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/help/dropload.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/help/common.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/help/app_useList.css">
	<script src="${pageContext.request.contextPath}/js/help/response.js"></script>
	<title>帮助信息</title>
</head>
<body>
	<header>
    <form class="help-from" action="showAppHelp.action" target="help-list" method="post">
    	<div class="search"><input type="text" name="title" id="title" placeholder="请输入搜索内容" />
    	<input type="hidden"  name="sort" id="sort" value="${param.sort }">
			<img src="images/search.png" class="ico_search"></img>
		</div>
    </form>
	</header>
	<section>
    <iframe class="help-list" name="help-list" frameborder="0"></iframe>
</section>
  <script src="${pageContext.request.contextPath}/js/plugin/jquery-1.8.3.js"></script>
  <script type="text/javascript">
  $(function(){
		if($("form").submit()){
			$("iframe").attr("src",$("form").attr("action")+"?sort=${param.sort }");
		}
		else{
			alert("request error!!")
		}
	})
  
  </script>
</body>
</html>