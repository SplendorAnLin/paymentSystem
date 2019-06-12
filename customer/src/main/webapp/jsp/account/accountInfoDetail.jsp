<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html class="w-p-100">
<head>
</head>
<body class="w-p-100 min-width">
	<div class="ctx-iframe">
		<div class="title-h1 fix">
			<span class="primary fl">账户信息</span>
		</div>
		<form action="" method="post" id="queryDetailForm">
			<c:if test="${account != null}">
				<div class="table-warp pd-r-10">
					<div class="table-scroll">
						<table class="table-two-color">
							<thead>
								<tr>
									<th>账户编号</th>
									<th>用户号</th>
									<th>币种</th>
									<th>状态</th>
									<th>总余额</th>
									<th>在途资金</th>
									<th>冻结资金</th>
									<th>开户时间</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>${account.code }</td>
									<td>${account.userNo }</td>
									<td>${account.currency }</td>
									<td>
										<dict:write dictId="${account.status.name() }" dictTypeId="ACCOUNT_STATUS_INFO_COLOR"></dict:write>
									</td>
									<td>${account.balance }</td>
									<td>${account.transitBalance }</td>
									<td>${account.freezeBalance }</td>
									<td><fmt:formatDate value="${account.openDate }"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							</tbody>
						</table>
			</c:if>
		</form>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>