<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../include/header.jsp"%>
<%
	response.setHeader( "Pragma ", "public");
	response.setHeader( "Cache-Control ", "must-revalidate, post-check=0, pre-check=0 ");
	response.addHeader( "Cache-Control ", "public ");
	response.addHeader("Content-Disposition", "attachment; filename=%E4%BA%A4%E6%98%93%E6%B1%87%E6%80%BB.xls");
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
	<c:if test="${order != null && order.size() > 0 }">
		<table cellpadding="1" cellspacing="1" align="center" class="global_tableresult" style="width: 100%">
			<tr>
				<td width="140px;">交易流水号</td>
				<td	width="140px;">商户订单号</td>
				<td width="80px;">商户编号</td>
				<td width="50px;">订单金额</td>
				<td width="50px;">商户手续费</td>
				<td width="60px;">订单状态</td>
				<td width="60px;">清算状态</td>
				<td width="60px;">退款状态</td>
				<td width="110px;">下单时间</td>
				<td width="110px;">支付时间</td>
			</tr>
			<c:forEach items="${order}" var="ords" varStatus="i">
				<tr>
					<td>${ords.code }</td>
					<td>
						<div style="vnd.ms-excel.numberformat:@;charset=UTF-8">
							${ords.requestCode }
						</div>
					</td>
					<td><c:out value="${ords.receiver }" /></td>
					<td><fmt:formatNumber value="${ords.amount }" pattern="#.##"/></td>
					<td><fmt:formatNumber value="${ords.receiverFee }" pattern="#.##"/></td>
					<td>
						<c:if test="${ords.status == 'WAIT_PAY'}">等待付款</c:if>
						<c:if test="${ords.status == 'SUCCESS'}">成功</c:if>
						<c:if test="${ords.status == 'CLOSED'}">关闭</c:if>
						<c:if test="${ords.status == 'FAILED'}">失败</c:if>
					</td>
					<td>
						<c:if test="${ords.clearingStatus == 'UN_CLEARING'}">未清算</c:if>
						<c:if test="${ords.clearingStatus == 'CLEARING_SUCCESS'}">清算成功</c:if>
						<c:if test="${ords.clearingStatus == 'CLEARING_FAILED'}">清算失败</c:if>
					</td>
					<td>
						<c:if test="${ords.refundStatus == 'NOT_REFUND'}">未退款</c:if>
						<c:if test="${ords.refundStatus == 'START_REFUND'}">成功退款</c:if>
						<c:if test="${ords.refundStatus == 'REFUND_PART'}">部分退款</c:if>
						<c:if test="${ords.refundStatus == 'REFUND_ALL'}">全部退款</c:if>
					</td>
					<td>
						<fmt:formatDate value="${ords.orderTime}" pattern="yyyy-MM-dd HH:mm"/>
					</td>
					<td>
						<fmt:formatDate value="${ords.successPayTime}" pattern="yyyy-MM-dd HH:mm"/>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${order eq null or order.size() == 0 }">
		无符合条件的查询结果
	</c:if>
</body>
</html>