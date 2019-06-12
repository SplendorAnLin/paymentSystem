<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html class="w-p-100">
<head>
</head>
<body class="w-p-100 min-width">
	<div class="ctx-iframe">
		<div class="title-h1 fix">
			<span class="primary fl">费率信息</span>
		</div>
		<c:if test="${agentFees != null && agentFees.size() > 0 }">
			<div class="table-warp pd-r-10">
				<div class="table-scroll">
					<table class="table-two-color">
						<thead>
							<tr>
								<th>状态</th>
								<th>产品类型</th>
								<th>费率类型</th>
								<th>商户费率</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${agentFees}" var="fees" varStatus="i">
								<tr>
									<td><c:if test="${fees.status.name() eq 'TRUE'}">
								<span style="color:#00FF00">开通</span>
							</c:if> <c:if test="${fees.status.name() eq 'FALSE'}">
								<span style="color:#FF0000">关闭</span>
							</c:if> <c:if test="${fees.status.name() eq 'AUDIT'}">
								<span style="color:c_yellow">审核中</span>
							</c:if></td>
									<td><c:if test="${fees.productType.name() == 'B2C'}">个人网银</c:if>
										<c:if test="${fees.productType.name() == 'B2B'}">企业网银</c:if> <c:if
											test="${fees.productType.name() == 'AUTHPAY'}">认证支付</c:if> <c:if
											test="${fees.productType.name() == 'REMIT'}">代付</c:if> <c:if
											test="${fees.productType.name() == 'WXJSAPI'}">微信公众号支付</c:if>
										<c:if test="${fees.productType.name() == 'WXNATIVE'}">微信二维码支付</c:if>
										<c:if test="${fees.productType.name() == 'ALIPAY'}">支付宝</c:if>
									</td>
									<td><c:if test="${fees.feeType.name() eq 'FIXED'}">
								固定
							</c:if> <c:if test="${fees.feeType.name() eq 'RATIO'}">
								百分比
							</c:if></td>
									<td>${fees.fee}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:if>
		<c:if test="${agentFees eq null or agentFees.size() == 0 }">
			无费率信息
		</c:if>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>