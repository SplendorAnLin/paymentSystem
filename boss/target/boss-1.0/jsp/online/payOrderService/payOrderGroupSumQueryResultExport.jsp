<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/commons-include.jsp" %>
<%
	response.setHeader( "Pragma ", "public");
	response.setHeader( "Cache-Control ", "must-revalidate, post-check=0, pre-check=0 ");
	response.addHeader( "Cache-Control ", "public ");
	response.addHeader("Content-Disposition", "attachment; filename=payOrderSumInfo.xls");
	response.setContentType("application/vnd.ms-excel.numberformat:@;charset=UTF-8");
%>
<html>
<head>
	<style type="text/css">
		.txt{
			mso-ignore:padding;
			mso-generic-font-family:auto;
			mso-font-charset:134;
			mso-number-format:"\@";
			mso-background-source:auto;
			mso-pattern:auto;
		}
		td{
			white-space:nowrap;
			border:1px dotted #333;
		}
	</style>
</head>
<body>
	<c:if test="${sumResult != null && fn:length(sumResult) > 0}">
		<table cellpadding="0" cellspacing="1" align="center"  class="global_tableresult" style="width: 100%">
			<tr>
				<th>
					<c:if test="${groupType == 'customerNo'}">商户编号</c:if>
					<c:if test="${groupType == 'orderTime'}">下单日期</c:if>
				</th>
				<c:if test="${interfaceType != '' }">
					<th>支付方式</th>
				</c:if>
				<th>金额</th>
				<th>手续费</th>
				<th>交易成本</th>
				<th>笔数</th>
			</tr>
			<c:forEach items="${sumResult }" var="result">
				<tr align="center">
					<td>
						<c:if test="${groupType == 'customerNo'}">${result.key.receiver }</c:if>
						<c:if test="${groupType == 'orderTime'}">${result.key.orderTime }</c:if>
					</td>
					<c:if test="${interfaceType != '' }">
						<td>
							<dict:write dictTypeCode="InterfaceType" dictKey="${interfaceType }"></dict:write>
						</td>
					</c:if>
					<td><fmt:formatNumber value="${result.value.amount}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${result.value.receiverFee}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${result.value.cost}" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${result.value.count}" pattern="#"/></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${fn:length(sumResult) == 0}">
		无符合条件的记录
	</c:if>
</body>
</html>