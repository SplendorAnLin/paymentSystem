<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/commons-include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript" src="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/js/date.js"></script>
	<script type="text/javascript" src="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/js/accountInfo/accountManagement.js"></script>
</head>
<body>
	<div class="pop_tit"><h2>账户历史明细</h2></div>
	<form action="" method="post" id="queryDetailForm">
		<c:if test="${accountRecordedDetail != null}">
		<table class="global_table" align="center" cellpadding="0" cellspacing="1">
			<caption>基础信息 </caption>
			<tr>
				<th>账号</th>
				<td>${accountRecordedDetail.accountNo }</td>
				<th>用户号</th>
				<td>${accountRecordedDetail.userNo }</td>
				<th>角色</th>
				<td>${accountRecordedDetail.userRole }</td>
			</tr>
			<tr>
				<th>业务类型</th>
				<td><dict:write dictTypeCode="account.BussinessCode" dictKey="${accountRecordedDetail.bussinessCode }" /></td>
				<th>币种</th>
				<td><dict:write dictTypeCode="account.Currency" dictKey="${accountRecordedDetail.currency }" /></td>
				<th>系统编码</th>
				<td>${accountRecordedDetail.systemCode }</td>
			</tr>
			<tr>
				<th>系统交易流水号</th>
				<td>${accountRecordedDetail.transFlow }</td>
				<th>资金标识</th>
				<td><dict:write dictTypeCode="account.FundSymbol" dictKey="${accountRecordedDetail.symbol }" /></td>
				<th>交易金额</th>
				<td><fmt:formatNumber value="${accountRecordedDetail.transAmount }" pattern="#.##"/></td>
			</tr>
			<tr>
				<th>创建日期</th>
				<td><fmt:formatDate value="${accountRecordedDetail.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<th>交易日期</th>
				<td><fmt:formatDate value="${accountRecordedDetail.transTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<th>待入账日期</th>
				<td><fmt:formatDate value="${accountRecordedDetail.waitAccountDate }" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<th>余额</th>
				<td colspan="5"><fmt:formatNumber value="${accountRecordedDetail.remainBalance }" pattern="#.##"/></td>
			</tr>
		</table>
		</c:if>
	<center>
		<input type="button" class="global_btn" value="返回" onclick="toAccountHistoryQueryDetail();"/>
	</center>
	</form>
</body>
</html>