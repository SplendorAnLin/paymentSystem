<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html class="over-x-h">
<head>
<style>
.lable {
	margin-right: 10px;
}
</style>
</head>
<body>
	<div class="in-pop" style="width: 400px; padding: 20px 0;">
		<form action="" method="post" id="queryDetailForm">
			<c:if test="${account != null}">
				<div class="block">
					<span class="lable w-120">账户编号:</span> <span
						class="static-lable c_gray v-m">${account.userNo }</span>
				</div>
				<div class="block">
					<span class="lable w-120">币种:</span> <span
						class="static-lable c_gray v-m">${account.currency }</span>
				</div>
				<div class="block">
					<span class="lable w-120">状态<:</span> <span
						class="static-lable c_gray v-m">
						<dict:write dictId="${account.status.name() }" dictTypeId="ACCOUNT_STATUS_INFO_COLOR"></dict:write>
						<%-- <c:if
							test="${account.status.name() == 'NORMAL'}">正常</c:if> <c:if
							test="${account.status.name() == 'END_IN'}">止收 </c:if> <c:if
							test="${account.status.name() == 'EN_OUT'}">止支</c:if> <c:if
							test="${account.status.name() == 'FREEZE'}">冻结</c:if> --%></span>
				</div>
				<div class="block">
					<span class="lable w-120">总余额:</span> <span
						class="static-lable c_gray v-m">${account.balance }</span>
				</div>
				<div class="block">
					<span class="lable w-120">在途资金:</span> <span
						class="static-lable c_gray v-m">${account.transitBalance }</span>
				</div>
				<div class="block">
					<span class="lable w-120">冻结资金:</span> <span
						class="static-lable c_gray v-m">${account.freezeBalance }</span>
				</div>
				<div class="block">
					<span class="lable w-120">开户时间:</span> <span
						class="static-lable c_gray v-m"><fmt:formatDate
							value="${account.openDate }" pattern="yyyy-MM-dd HH:mm:ss" /></span>
				</div>
			</c:if>
		</form>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>