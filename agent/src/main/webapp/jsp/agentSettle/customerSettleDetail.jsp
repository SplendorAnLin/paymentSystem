<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<html>
<head>
	<script type="text/javascript" src="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/js/date.js"></script>
	<script type="text/javascript" src="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/js/accountInfo/accountManagement.js"></script>
</head>
<body>
	<div class="pop_tit"><h2>账户详情</h2></div>
	<form action="" method="post" id="queryDetailForm">
		<c:if test="${customerSettle != null}">
		<table class="global_table" align="center" cellpadding="0" cellspacing="1">
			<tr>
				<th>accountName</th>
				<td>
					${account.currency }
				</td>
				<th>状态</th>
				<td>
					<c:if test="${account.status.name() == 'NORMAL'}">正常</c:if>
					<c:if test="${account.status.name() == 'END_IN'}">止收 </c:if>
					<c:if test="${account.status.name() == 'EN_OUT'}">止支</c:if>
					<c:if test="${account.status.name() == 'FREEZE'}">冻结</c:if>
				</td>
			</tr>
			<tr>
				<th>总余额</th>
				<td>${account.balance }</td>
				<th>在途资金</th>
				<td>${account.transitBalance }</td>
			</tr>
			<tr>
				<th>冻结资金</th>
				<td>${account.freezeBalance }</td>
				<th>开户时间</th>
				<td><fmt:formatDate value="${account.openDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</table>
		</c:if>
	</form>
</body>
</html>