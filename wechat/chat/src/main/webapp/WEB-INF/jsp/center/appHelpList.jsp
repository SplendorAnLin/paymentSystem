<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
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
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app/plugin/font-awesome.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app/help.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app/page.css">
  <title>帮助与反馈</title>
</head>
<body style="width: 100%;">
  
  <div class="list-wrap">
    <p class="list-title">热点问题</p>
    <ul>
     <c:forEach items="${page.object }" var="order">
						  <li><a href="chathelp?id=${order.id }">${order.title }</a> <i class="arrow-right fa fa-angle-right"></i></li>
						</c:forEach>
    </ul>

	<div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="100" total="${countPage}"  current="${pagingPage}"></div>

  </div>


  <script src="${pageContext.request.contextPath}/js/jquery-2.2.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/app/page.js"></script>
  <script>
    var win = window;
    $('.list-wrap a').click(function(event) {
      win.parent.location = $(this).attr('href');
      event.stopPropagation();
      return false;
    });
    console.log("${pagingPage}","${countPage}");
  </script>
</body>
</html>