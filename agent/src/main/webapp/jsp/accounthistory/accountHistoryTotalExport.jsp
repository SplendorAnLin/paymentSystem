<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.net.URLEncoder"%>
<%@ include file="../include/commons-include.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<%
	String filename = "账户合计.xls";
	filename =URLEncoder.encode(filename,"UTF-8");
	response.setHeader("Pragma ", "public");
	response.setHeader("Cache-Control ", "must-revalidate, post-check=0, pre-check=0 ");
	response.addHeader("Cache-Control ", "public ");
	response.addHeader("Content-Disposition", "attachment; filename="+filename);
	response.setContentType("application/vnd.ms-excel.numberformat:@;charset=UTF-8");
%>
<html>
<head>
<style>
table {
	border: thin solid #333;
	border-collapse: collapse;
	border-spacing: 0px;
}

th {
	background-color: #ccc;
	border: thin solid #333;
}

td {
	border: thin solid #000;
}
</style>
</head>
<body>
	<table cellpadding="1" cellspacing="1" align="center" class="global_tableresult" style="width: 100%;vnd.ms-excel.numberformat:@">
		<tr>
			<th nowrap="true">交易时间</th>
			<th nowrap="true">业务类型</th>
			<th nowrap="true">资金标识</th>
			<th nowrap="true">交易金额</th>
			<th nowrap="true">账号编码数量</th>
		</tr>
		<c:forEach items="${lst }" var="ard">
			<tr align="center">
				<td><fmt:formatDate value="${ard.transTime }" pattern="yyyy-MM-dd"/></td>
				<td><dict:write dictTypeCode="account.BussinessCode" dictKey="${ard.bussinessCode }" /></td>
				<td><dict:write dictTypeCode="account.FundSymbol" dictKey="${ard.symbol }" /></td>
				<td><fmt:formatNumber value="${ard.transAmount }" pattern="#.##"/></td>
				<td>${ard.code }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>