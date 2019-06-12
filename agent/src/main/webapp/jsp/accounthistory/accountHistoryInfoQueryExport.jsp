<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@page import="java.net.URLEncoder"%>
	<%
	String filename = "账户历史数据.xls";
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
	<table cellpadding="1" cellspacing="1" align="center" class="global_tableresult" style="width: 100%;">
		<tr>
			<th nowrap="true">账户编号</th>
			<th nowrap="true">系统编码</th>
			<th nowrap="true">交易系统流水号</th>
			<th nowrap="true">资金标识</th>
			<th nowrap="true">交易金额</th>
			<th nowrap="true">创建日期</th>
			<th nowrap="true">交易日期</th>
		</tr>
		<c:forEach items="${accountRecordedDetails }" var="ard">
			<tr align="center">
				<td style="vnd.ms-excel.numberformat:@ ">${ard.accountNo }</td>
				<td>${ard.systemCode }</td>
				<td>${ard.transFlow }</td>
				<td>
					<c:if test="${ard.symbol.name() == 'PLUS'}">加</c:if>
					<c:if test="${ard.symbol.name() == 'SUBTRACT'}">减</c:if>
				</td>
				<td>
					<div style="vnd.ms-excel.numberformat:#,##0.00">
						<c:if test="${ard.symbol.name() == 'SUBTRACT'}">-</c:if>${ard.remainBalance }
					</div>
				</td>
				<td><fmt:formatDate value="${ard.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><fmt:formatDate value="${ard.transTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>