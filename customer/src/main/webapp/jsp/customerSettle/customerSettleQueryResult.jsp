<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
</head>
<body>	
	<div style="overflow: auto;">
		<c:if test="${customerSettle != null }">
			<table cellpadding="1" cellspacing="1" align="center" class="global_tableresult" style="">
				<tr><td colspan="3"><div class="pop_tit" ><h2>结算卡信息</h2></div></td></tr>
				<tr>
					<th width="160px;">开户行</th>
					<th	width="140px;">开户名</th>
					<th	width="140px;">银行账号</th>
				</tr>
				<%-- <c:forEach items="${customerSettle}" var="fees" varStatus="i"> --%>
					<tr>
						<td>
							${customerSettle.openBankName }
						</td>
						<td>
							${customerSettle.accountName }
						</td>
						<td>
							${customerSettle.accountNo }
						</td>
					</tr>
				<%-- </c:forEach> --%>
			</table>
		</c:if>
		<c:if test="${customerSettle eq null }">
			无费率信息
		</c:if>
	</div>
</body>
</html>