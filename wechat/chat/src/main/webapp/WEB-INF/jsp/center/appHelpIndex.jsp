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
  <title>帮助与反馈</title>
</head>
<body>
  
  <div class="help-box">

    <form class="help-from" action="chatlist" target="help-list" method="post">
      <div class="wrap fix">
        <input class="serach-input" type="text" name="title" placeholder="在此输入搜索问题" >
        <input class="serach-submit query-btn"  type="submit" value="搜索">
      </div>
    </form>

    <iframe class="help-list" name="help-list" frameborder="0"></iframe>
  </div>

  <script src="${pageContext.request.contextPath}/js/jquery.min.1.8.1.js"></script>
  <script>
  $(document.body).ready(function() {
    $('.help-from').submit();
  });
  $('.help-list').load(function() {
    var iframe = $(this);
    var body = iframe.contents().find('body');
    iframe.css('height', body.height() + 5);
  });
  </script>
</body>
</html>