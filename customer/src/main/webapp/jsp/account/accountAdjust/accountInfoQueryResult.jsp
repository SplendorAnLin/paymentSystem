<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/header.jsp"%>
<html>
<head>
<script type="text/javascript">
	$(function() {
		initPage('${page.currentPage}', '${page.totalResult}',
				'${page.totalPage}', '${page.entityOrField}', parent.document
						.getElementById("form"));
	});
</script>
</head>
<body class="w-p-100 min-width">
	<div class="ctx-iframe">
		<div class="title-h1 fix">
			<span class="primary fl">查询结果</span>
		</div>
		<div class="table-warp pd-r-10">
			<%-- <c:if test="${accounts != null && accounts.size() > 0}"> --%>
				<div class="table-scroll">
					<table class="table-two-color">
						<thead>
							<tr>
								<th>账户编号</th>
								<th>账户类型</th>
								<th>账户状态</th>
								<th>会员编号</th>
								<th>会员等级</th>
								<th>币种</th>
								<th>总余额</th>
								<th>在途金额</th>
								<th>冻结余额</th>
								<th>创建时间</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<%-- <c:forEach items="${accounts }" var="acc">
								<tr>
									<td>${acc.code }</td>
									<td><c:if test="${acc.type.name() == 'CASH'}">现金</c:if> <c:if
											test="${acc.type.name() == 'DEPOSIT'}">保证金 </c:if> <c:if
											test="${acc.type.name() == 'FINANCING'}">理财</c:if> <c:if
											test="${acc.type.name() == 'CREDIT'}">信用</c:if></td>
									<td><c:if test="${acc.status.name() == 'NORMAL'}"><span style="color:#009688">正常</span></c:if>
										<c:if test="${acc.status.name() == 'END_IN'}"><span style="color:#FF5722">止收</span></c:if> <c:if
											test="${acc.status.name() == 'EN_OUT'}"><span style="color:#BDBDBD">止支</span></c:if> <c:if
											test="${acc.status.name() == 'FREEZE'}"><span style="color:rgb(3, 9, 244)">冻结</span></c:if></td>							
									<td>${acc.userNo }</td>
									<td><c:if test="${acc.userRole.name() == 'CUSTOMER'}">商户</c:if>
										<c:if test="${acc.userRole.name() == 'AGENT'}">服务商</c:if> <c:if
											test="${acc.userRole.name() == 'LEAGUER'}">会员 </c:if></td>
									<td>${acc.currency }</td>
									<td><fmt:formatNumber value="${acc.balance }"
											pattern="#.##" /></td>
									<td><fmt:formatNumber value="${acc.transitBalance }"
											pattern="#.##" /></td>
									<td><fmt:formatNumber value="${acc.freezeBalance }"
											pattern="#.##" /></td>
									<td><fmt:formatDate value="${acc.createTime }"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td>
										<c:if test="${acc.status.name() == 'FREEZE'}">
											<button class="btn-loading btn mr-10 mb-10 btn-size-small">禁用</button>
										</c:if>
											<button
												data-url="toAccountAdjustA.action?id=${acc.code }&&balance=${acc.balance }"
												data-title="账户调帐" data-rename="调帐"
												class="pop-modal btn-loading btn mr-10 mb-10 btn-size-small">调帐</button>
										<button
											data-url="accountHistoryAction.action?accountNo=${acc.code }"
											data-title="账户调账历史" data-nofoot="true"
											class="pop-modal btn-loading btn mb-10 btn-size-small">调账历史</button>
									</td>
								</tr>
							</c:forEach> --%>
							<tr>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td></td>
								<td>
									<button
												data-url="toaccountAdjust.action"
												data-title="账户调帐" data-rename="调帐"
												class="pop-modal btn-loading btn mr-10 mb-10 btn-size-small">调帐</button>
										<button
											data-url="toAccountHistoryInfoQueryResult.action"
											data-title="账户调账历史" data-nofoot="true"
											class="pop-modal btn-loading btn mb-10 btn-size-small">调账历史</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<%@ include file="../../include/page.jsp"%>
			<%-- </c:if> --%>
		</div>
	</div>
	<%-- <c:if test="${accounts eq null or accounts.size() == 0}">
			无符合条件的记录
		</c:if> --%>
	<%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>