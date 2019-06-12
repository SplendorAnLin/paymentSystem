<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../include/header.jsp"%>
<html>
	<body>	
		<c:if test="${tradeOrder != null }">
			<table cellpadding="0" cellspacing="1" class="global_tabledetail">
				<tr>
					<th width="20%">交易流水号</th>
					<td width="30%"><c:out value="${tradeOrder.code }" /></td>
					<th width="20%">商户订单号</th>
					<td width="30%"><c:out value="${tradeOrder.requestCode }" /></td>
				</tr>
				<tr>
					<th width="20%">商户编号</th>
					<td width="30%"><c:out value="${tradeOrder.receiver }" /></td>
					<th width="20%">订单状态</th>
					<td width="30%">
						<dict:write dictId="${tradeOrder.status }" dictTypeId="TRADE_ORDER_STATUS_COLOR"></dict:write>
					</td>
				</tr>
					
				<tr>
					<th width="20%">订单金额</th>
					<td width="30%"><fmt:formatNumber value="${tradeOrder.amount }" pattern="#.##"/></td>
					<th width="20%">实付金额</th>
					<td width="30%"><fmt:formatNumber value="${tradeOrder.paidAmount }" pattern="#.##"/></td>
				</tr>
				<tr>
					<th width="20%">交易成本</th>
					<td width="30%"><fmt:formatNumber value="${tradeOrder.cost }" pattern="#.##"/></td>
					<th width="20%">商户手续费</th>
					<td width="30%"><fmt:formatNumber value="${tradeOrder.receiverFee }" pattern="#.##"/></td>
				</tr>
				<tr>
					<th width="20%">清分状态</th>
					<td width="30%">
						<dict:write dictId="${tradeOrder.clearingStatus }" dictTypeId="LIQUIDATION_STATUS_COLOR"></dict:write>
					</td>
					<th width="20%">订单创建时间</th>
					<td width="30%"><fmt:formatDate value="${tradeOrder.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<th width="20%">超时时间</th>
					<td width="30%"><fmt:formatDate value="${tradeOrder.timeout }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<th width="20%">关闭时间 </th>
					<td width="30%"><fmt:formatDate value="${tradeOrder.closeTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				
				<c:if test="${authSaleBean != null}">
					<tr>
						<th width="20%">姓名</th>
						<td width="30%">${authSaleBean.payerName }</td>
						<th width="20%">电话 </th>
						<td width="30%">${authSaleBean.payerMobNo }</td>
					</tr>
					<tr>
						<th width="20%">身份证</th>
						<td width="30%">${authSaleBean.certNo }</td>
						<th width="20%">银行卡号 </th>
						<td width="30%">${authSaleBean.bankCardNo }</td>
					</tr>
				</c:if>
				<tr>
					<th width="20%">支付成功时间 </th>
					<td width="30%" colspan="3"><fmt:formatDate value="${tradeOrder.successPayTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				<tr>
					<td colspan="4" align="center"> 
						<input type="button" class="global_btn" value="返回" onclick="javascript:history.go(-1)"> 
					</td>
				</tr>	
			</table>
		</c:if>
		<c:if test="${payments.size() > 0 && payments != null}">
			<table cellpadding="0" cellspacing="1" class="global_tabledetail">
				<tr>		
					<td colspan="12" align="left" style="height:10px"><b>交易流水信息</b></td>
				</tr>
				<tr>
					<td>支付流水号</td>
					<td>支付方式</td>
					<td>应付金额</td>
					<!-- <td>卖方账号</td> -->
					<td>商户手续费</td>
					<td>支付状态</td>
					<td>支付接口编号</td>
					<td>支付接口订单号</td>
					<td>接口成本 </td>
					<td>交易开始时间</td>
					<td>交易结束时间</td>
				</tr>
				<c:forEach items="${payments }" var="pay">
					<tr>
						<td><c:out value="${pay.code }"></c:out></td>
						<td>
						<!-- B2C, B2B, AUTHPAY, REMIT, WXJSAPI, WXNATIVE, ALIPAY -->
							<dict:write dictId="${pay.payType }" dictTypeId="PRODUCT_TYPE"></dict:write>
						</td>
						<td><fmt:formatNumber value="${pay.amount }" pattern="#.##"/></td>
						<!--<td><c:out value=" pay.receiverAccount  "></c:out></td>-->
						<td><c:out value="${pay.receiverFee }"></c:out></td>
						<td>
							<dict:write dictId="${pay.status }" dictTypeId="PAY_STATUS_COLOR"></dict:write>
						</td>
						<td><c:out value="${pay.payinterface }"></c:out></td>
						<td><c:out value="${pay.payinterfaceRequestId }"></c:out></td>
						<td><fmt:formatNumber value="${pay.payinterfaceFee }" pattern="#.##"/></td>
						<td><fmt:formatDate value="${pay.payTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td><fmt:formatDate value="${pay.completeTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</body>
</html>