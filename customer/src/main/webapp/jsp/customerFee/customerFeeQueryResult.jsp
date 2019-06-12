<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript">
	
		function detail(id){
			var url = "${pageContext.request.contextPath}/customerFeeDetailAction.action?customerFee.id="+id+"";
			window.location.href=url;		
		}			
		function toUpdate(id){
			var url = "${pageContext.request.contextPath}/toUpdateCustomerFeeAction.action?customerFee.id="+id+"";
			window.location.href=url;		
		}			
		
	</script>	
</head>
<body>	
	<div style="overflow: auto;">
		<c:if test="${customerFees != null && customerFees.size() > 0 }">
			<table cellpadding="1" cellspacing="1" align="center" class="global_tableresult" style="">
				<tr><td colspan="4"><div class="pop_tit" ><h2>费率信息</h2></div></td></tr>
				<tr>
					<td width="160px;">状态</td>
					<td	width="140px;">产品类型</td>
					<td	width="140px;">费率类型</td>
					<td	width="140px;">商户费率</td>
				</tr>
				<c:forEach items="${customerFees}" var="fees" varStatus="i">
					<tr>
						<td>
							<c:if test="${fees.status.name() eq 'TRUE'}">
								开通
							</c:if>
							<c:if test="${fees.status.name() eq 'FALSE'}">
								关闭
							</c:if>
							<c:if test="${fees.status.name() eq 'AUDIT'}">
								审核中
							</c:if>
						</td>
						<td>
							<c:if test="${fees.productType.name() == 'B2C'}">个人网银</c:if>
							<c:if test="${fees.productType.name() == 'B2B'}">企业网银</c:if>
							<c:if test="${fees.productType.name() == 'AUTHPAY'}">认证支付</c:if>
							<c:if test="${fees.productType.name() == 'REMIT'}">代付</c:if>
							<c:if test="${fees.productType.name() == 'WXJSAPI'}">微信公众号支付</c:if>
							<c:if test="${fees.productType.name() == 'WXNATIVE'}">微信二维码支付</c:if>
							<c:if test="${fees.productType.name() == 'ALIPAY'}">支付宝</c:if>
						</td>
						<td>
							<c:if test="${fees.feeType.name() eq 'FIXED'}">
								固定
							</c:if>
							<c:if test="${fees.feeType.name() eq 'RATIO'}">
								百分比
							</c:if>
						</td>
						<td>
							${fees.fee} 
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${customerFees eq null or customerFees.size() == 0 }">
			无费率信息
		</c:if>
	</div>
</body>
</html>