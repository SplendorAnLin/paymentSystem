<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tld/c.tld" %>
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
	<div class="content">
		<c:if test="${appHelpList eq null or appHelpList.size() == 0}">
			   <p class="sea_title">无匹配记录</p> 
		</c:if>
		<c:if test="${appHelpList != null && appHelpList.size() > 0 }">
		<p class="sea_title">热点问题</p>
			<div class="lists">
				
  	 		</div>
  	 	</c:if>
	</div>
  <script src="${pageContext.request.contextPath}/js/plugin/jquery-1.8.3.js"></script>
  <script src="${pageContext.request.contextPath}/js/help/dropload.min.js"></script>
  
 
  <script>
    var win = window;
    $('.list-wrap a').click(function(event) {
   win.parent.location = $(this).attr('href');
      event.stopPropagation();
      return false;
    });
  </script>
  <script>
			$(function() {
				var content= window.parent.$("#title").val();
				var sort= window.parent.$("#sort").val();
				//当前页
				var page = 1;
				//总页数
				var pagecount = ${countPage};
				var showCount=${showCount};
				// dropload
				$('.content').dropload({
					scrollArea: window,
					domUp: {
						domClass: 'dropload-up',
						domRefresh: '<div class="dropload-refresh">↓下拉刷新</div>',
						domUpdate: '<div class="dropload-update">↑释放更新</div>',
						domLoad: '<div class="dropload-load"><span class="loading"></span>正在刷新</div>'
					},
					domDown: {
						domClass: 'dropload-down',
						domRefresh: '<div class="dropload-refresh">↑上拉加载更多</div>',
						domLoad: '<div class="dropload-load"><span class="loading"></span>加载中</div>',
						domNoData: '<div class="dropload-noData">暂无数据</div>'
					},
					
					loadUpFn: function(me) {
						page=1;
						$.ajax({
							type: 'GET',
							url: "findHelpList.action",
							data:{"sort":sort,"title":content},
							dataType: 'json',
							success: function(data) {
								data=JSON.parse(data);
								var result = '';
								for(var i = 0; i < data.length; i++) {
									result += '<a class="item opacity" href="showAppHelp.action?id=' + data[i].id + '">' +
										'<h3>' + data[i].title + '</h3>' +'</a>';
								}
								setTimeout(function() {
									$('.lists').html(result);
									// 每次数据加载完，必须重置
									me.resetload();
									// 重置页数，重新获取loadDownFn的数据
								//	page =1;
									// 解锁loadDownFn里锁定的情况
									me.unlock();
									me.noData(false);
								}, 1000);
							},
							error: function(xhr, type) {
								alert('Ajax error!');
								// 即使加载出错，也得重置
								me.resetload();
							}
						});
					},
					loadDownFn: function(me) {
						page++;
						// 拼接HTML
						var result = '';
						$.ajax({
							type: 'GET',
							url:'findHelpList.action?pagingPage='+page,
							data:{"sort":sort,"title":content},
							dataType: 'json',
							success: function(data) {
								data=JSON.parse(data);
								if(data.length > 0) {
									for(var i = 0; i <data.length; i++) {
										result += '<a class="item opacity" href="showAppHelp.action?id=' + data[i].id + '">' +
											'<h3>' + data[i].title + '</h3>' +'</a>';
									}
								} 
								if(showCount>data.length||pagecount==page){
									me.lock();
									me.noData();
								}
								setTimeout(function() {
									// 插入数据到页面，放到最后面
									$('.lists').append(result);
									// 每次数据插入，必须重置
									me.resetload();
								}, 1000);
							}
						});
					},
					threshold: 50
				});
				//查询
				
			});
		</script>
</body>
</html>