<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<%@include file="../include/commons-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3CUTF-8//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<div class="detail_tit">
		<h2>银行简称匹配对应</h2>
	</div>
	<br>
	<form action="${pageContext.request.contextPath}/cnaps/nickname.htm" method="POST">
		<dl>
			<dd>银行名称简称信息(按以下顺序：银行名称,可用匹配的简称)</dd>
			<dd>
				<textarea name="nicknameText" rows="15" cols="120">${nicknameText}</textarea>
			</dd>
		</dl>

		<center>
			<input type="submit" class="global_btn" value="更新" />
		</center>
	</form>
</body>
</html>
