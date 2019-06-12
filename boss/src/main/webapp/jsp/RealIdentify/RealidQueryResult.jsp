<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<head>
		 <%@ include file="../include/header.jsp"%>
		<title>实名认证-结果</title>
	</head>

<body class="pb-2">

	<div class="title-h1 fix">
		<span class="primary fl">查询结果</span>
	</div>

	<c:if test="${page.object != null && page.object.size() > 0}">
		<div class="table-warp">
			<div class="table-sroll">
				<table class="data-table shadow--2dp  w-p-100 tow-color">
					<thead>
						<tr>
							<th>合作方编号</th>
							<th>合作方订单号</th>
							<th>接口订单号</th>
							<th>卡类型</th>
							<th>身份证</th>
							<th>银行卡号</th>
							<th>姓名</th>
							<th>手机号</th>
							<th>通道编号</th>
							<th>业务类型</th>
							<th>认证状态</th>
							<th>交易状态</th>
							<th>是否实时</th>
							<th>完成时间</th>
							<th>清算时间</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.object }" var="real">
							<tr>
								<td>${real.customerNo }</td>
								<td>${real.requestCode }</td>
								<td>${real.interfaceRequestId }</td>
								<td><dict:write dictId="${real.cardType }" dictTypeId="CARD_TYPE"></dict:write></td>
								<td>${real.certNo }</td>
								<td>${real.bankCardNo }</td>
								<td>${real.payerName }</td>
								<td>${real.payerMobNo }</td>
								<td>${real.interfaceCode }</td>
								<td><dict:write dictId="${real.busiType }" dictTypeId="BUSI_TYPE"></dict:write></td>
								<td><dict:write dictId="${real.authResult }" dictTypeId="AUTH_RESULT"></dict:write></td>
								<td><dict:write dictId="${real.authOrderStatus }" dictTypeId="AUTH_ORDER_STATUS"></dict:write></td>
								<td><dict:write dictId="${real.isActual }" dictTypeId="IS_ACTUAL"></dict:write></td>
								<td><fmt:formatDate value="${real.completeTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${real.clearTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="jump-rarp mt-20 text-center" id="PageWrap"
			total_count="${page.totalResult}" total="${page.totalPage}"
			current="${page.currentPage}"></div>
	</c:if>
	<c:if test="${page.object eq null or page.object.size() == 0 }">
    	无符合条件的记录
  </c:if>
	<%@ include file="../include/bodyLink.jsp"%>
</body>

</html>