<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/header.jsp"%>
<%@page import="java.net.URLEncoder"%>
    <%
	String filename = "账户历史数据.xls";
	filename = URLEncoder.encode(filename, "UTF-8");
	response.setHeader("Pragma ", "public");
	response.setHeader("Cache-Control ", "must-revalidate, post-check=0, pre-check=0 ");
	response.addHeader("Cache-Control ", "public ");
	response.addHeader("Content-Disposition", "attachment; filename=" + filename);
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
	<table cellpadding="1" cellspacing="1" align="center" class="global_tableresult" style="width: 100%; vnd .ms-excel.numberformat: @">
		<tr>
			<th nowrap="true">用户号</th>
			<th nowrap="true">角色</th>
			<th nowrap="true">业务类型</th>
			<th nowrap="true">账户编号</th>
			<th nowrap="true">系统编码</th>
			<th nowrap="true">交易系统流水号</th>
			<th nowrap="true">资金标识</th>
      		<th nowrap="true">原金额</th>
			<th nowrap="true">交易金额</th>
			<th nowrap="true">余额</th>
			<th nowrap="true">余额</th>
			<th nowrap="true">交易日期</th>
			<th nowrap="true">待入账日期</th>
		</tr>
		<c:forEach items="${accountRecordedDetails }" var="ard">
			<tr align="center">
				<td>${ard.userNo }</td>
				<td><dict:write dictId="${ard.userRole.name() }" dictTypeId="USER"></dict:write></td>
				<td>
					<dict:write dictId="${ard.bussinessCode }" dictTypeId="BUSINESS_TYPE"></dict:write>
				</td>
				<%-- <c:if test="${ard.bussinessCode == 'DPAY_DEBIT'}">代付出款</c:if>
					<c:if test="${ard.bussinessCode == 'DPAY_DEBIT_FEE'}">代付手续费出款</c:if>
					<c:if test="${ard.bussinessCode == 'DPAY_CREDIT'}">代付退款 </c:if> <c:if
						test="${ard.bussinessCode == 'DPAY_CREDIT_FEE'}">代付手续费退款</c:if> <c:if
						test="${ard.bussinessCode == 'ONLINE_CREDIT'}">支付入账</c:if> <c:if
						test="${ard.bussinessCode == 'ONLINE_CREDIT_FEE'}">支付手续费扣款 </c:if>
					<c:if test="${ard.bussinessCode == 'SHATE_CREDIT'}">分润入账 </c:if> --%>
				<td><div style= "vnd.ms-excel.numberformat:@">${ard.accountNo }</div></td>
				<td>
					<dict:write dictId="${ard.systemCode }" dictTypeId="SYS_CODE"></dict:write>
				</td>
				<td>${ard.transFlow }</td>
				<td>
					<dict:write dictId="${ard.symbol.name() }" dictTypeId="CAPITAL_IDENTIFICATION"></dict:write>
                </td>
        	<td>
          <c:if test="${ard.symbol.name() == 'PLUS'}">
            <fmt:formatNumber value="${ard.remainBalance - ard.transAmount}" pattern="#.##" />
          </c:if>
          <c:if test="${ard.symbol.name() == 'SUBTRACT'}">
            <fmt:formatNumber value="${ard.remainBalance + ard.transAmount}" pattern="#.##" />
          </c:if>
        </td>
        <td>
                    <c:if test="${ard.symbol.name() == 'PLUS'}"><fmt:formatNumber value="${ard.transAmount }" pattern="#.##" /></c:if> 
                    <c:if test="${ard.symbol.name() == 'SUBTRACT'}">-<fmt:formatNumber value="${ard.transAmount }" pattern="#.##" /></c:if>
                </td>
				<td>
					<div style="vnd .ms-excel.numberformat: #,##0 .00">
						<c:if test="${ard.symbol.name() == 'PLUS'}"></c:if>
						<c:if test="${ard.symbol.name() == 'SUBTRACT'}"></c:if>${ard.remainBalance }
					</div>
				</td>
				<td><fmt:formatDate value="${ard.createTime }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><fmt:formatDate value="${ard.transTime }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td><fmt:formatDate value="${ard.waitAccountDate }"
						pattern="yyyy-MM-dd " /></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>