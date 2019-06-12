<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html class="w-p-100">
<head>
</head>
<body class="w-p-100 min-width">
	<div class="ctx-iframe">
		<div class="title-h1 fix">
			<span class="primary fl">结算卡信息</span>
		</div>
		<c:if test="${agentSettle != null }">
			<div class="table-warp pd-r-10">
				<div class="table-scroll">
					<table class="table-two-color">
						<thead>
							<tr>
								<th>开户行</th>
								<th>开户名</th>
								<th>银行账号</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${agentSettle}" var="settle" varStatus="i">
								<tr>
									<td>${settle.openBankName }</td>
									<td>${settle.accountName }</td>
									<td>${settle.accountNo }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:if>
		<c:if test="${agentSettle eq null }">
			无费率信息
		</c:if>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>