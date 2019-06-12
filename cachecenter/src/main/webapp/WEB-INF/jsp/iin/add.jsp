<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>
<%@include file="../include/commons-include.jsp"%>
<!DOCTYPE html PUBLIC "-//W3CUTF-8//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body>
	<div class="detail_tit">
		<h2>新增发行者识别信息</h2>
	</div>
	<br>
	<form action="${pageContext.request.contextPath}/iin/add.htm" method="POST" enctype="multipart/form-data">
		<dl>
			<dd>分隔符(不写默认为英文逗号“,”)</dd>
			<dd>
				<input name="separator">
			</dd>
		</dl>
		<dl>
			<dd>发行者识别信息(按以下顺序：发卡行名称,机构代码,卡名,卡号长度,识别码长度,识别码,卡种,银行编码)</dd>
			<dd>
				<textarea name="iinText" rows="15" cols="120"></textarea>
			</dd>
		</dl>
		<dl>
			<dd>发行者识别信息(按以下顺序：发卡行名称,机构代码,卡名,卡号长度,识别码长度,识别码,卡种,银行编码)</dd>
			<dd>
				<input type="file" name="iinTxt" />
			</dd>
		</dl>

		<center>
			<input type="submit" class="global_btn" value="新增" />
		</center>
		
		<h3>${errorMsg}</h3>
	</form>
</body>
</html>
