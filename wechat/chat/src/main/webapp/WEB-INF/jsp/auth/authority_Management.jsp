<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../include/commons-include.jsp"%>
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
		<!-- home screen app 全屏 -->
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta http-equiv="x-rim-auto-match" content="none">
		<link href="css/common.css" />
		<link rel="stylesheet" href="css/authority_Management.css" />
		<title>个人权限管理</title>
	</head>
	<body>
	 <header>
	 	<h1>设置个人权限</h1>
	 </header>
	<section>
	 	 <div class="container">
            <span><input type="checkbox"class="input_check" checked readonly disabled><label for="check3"></label></span>余额变更 
        </div>
        <div class="container">
            <span><input type="checkbox"class="input_check" checked id="check4" disabled readonly><label for="check4"></label></span>订单完成
        </div>
       
	 </section>
	 
	 <footer>
	 	<a class="save" href="success">保存</a>
	 </footer>
	</body>
</html>

