<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <meta content="yes" name="apple-mobile-web-app-capable" />
  <meta content="yes" name="apple-touch-fullscreen" />
  <meta content="telephone=no,email=no" name="format-detection" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app/rest.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app/help.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app/plugin/font-awesome.css">
  <title>帮助与反馈</title>
</head>
<body>
  
  <div class="article">
    <div class="title">
      <span class="return-ico"><i class="fa-chevron-left fa"></i></span>
      <p>${title }</p>
      
    </div>
    <div class="content">${content }</div>

  </div>

  <script src="${pageContext.request.contextPath}/js/jquery-2.2.1.min.js"></script>
  <script>
	$('.return-ico').click(function() {
	  history.back();
	});
  </script>
</body>
</html>