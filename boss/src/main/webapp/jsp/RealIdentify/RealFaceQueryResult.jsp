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
							<th>姓名</th>
							<th>手机号</th>
							<th>身份证</th>
							<th>银行卡号</th>
							<th>创建时间</th>
							<th>修改时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.object }" var="bind">
							<tr>
								<td>${bind.payerName }</td>
								<td>${bind.payerMobNo }</td>
								<td>${bind.certNo }</td>
								<td>${bind.bankCardNo }</td>
								<td><fmt:formatDate value="${bind.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${bind.lastUpdateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
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
	<c:if test="${page.object eq null or page.object.size() == 0}">
	无符合条件的记录
	</c:if>
	<%@ include file="../include/bodyLink.jsp"%>
</body>

</html>