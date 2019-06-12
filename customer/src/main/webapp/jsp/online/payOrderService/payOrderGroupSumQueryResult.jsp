<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%@ include file="../include/commons-include.jsp"%>
<html>
<head></head>
<body>
	<div class="pop_tit"><h2>订单分组合计结果</h2></div>
	<div style="overflow: scroll;max-height: 500px;">
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
					<tr>
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
	</div>
</body>
</html>