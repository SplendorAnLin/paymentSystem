<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
<title>分享注册</title>
<style>
.title {
	text-align: center;
	color: #333;
}
.line {
	margin: 0 10px;
}
.qr {
	width: 100%;
	margin: 0 auto;
}
.qr img {
	width: 100%;
}
.url-wrap {
    padding: 0 10px;
}
.url {
	display: inline-block;
	width: 90%;
	  white-space: nowrap;
	  overflow: hidden;
	  text-overflow: ellipsis;
	  vertical-align: middle;
}
</style>
</head>
<body style="width: 400px; padding: 10px;">

	<div class="share">
		<p class="title"><span>分享链接</span><span class="line">|</span><span>邀请注册</span></p>
		<div class="qr">
			<img src="data:image/png;base64,${msg }">
		</div>
		<p class="url-wrap"><span class="url">${url }</span><a data-clipboard-text="${url }" class="btn-copy" href="#">[复制]</a></p>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>