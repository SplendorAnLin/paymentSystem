<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/commons-include.jsp"%>
<html>
<head>
</head>
<body>
	&nbsp;&nbsp;
	<font color='#00f'>总金额：</font>
	<c:if test="${results == null}">
		<font color='#00f'> 0.0 
	</c:if>
	<c:if test="${results != null}">
		<c:if test="${results.result == null}">
			<font color='#00f'> 0.0 
		</c:if>
		<c:if test="${results.result != null}">
			<font color="red"><fmt:formatNumber
					value="${results.result.amount}" pattern="#.##" /></font>
		</c:if>
	</c:if>
	&nbsp;&nbsp;
	<font color='#00f'>总手续费：</font>
	<c:if test="${results == null}">
		<font color='#00f'> 0.0 
	</c:if>
	<c:if test="${results != null}">
		<c:if test="${results.result == null}">
			<font color='#00f'> 0.0 
		</c:if>
		<c:if test="${results.result != null}">
			<font color="red"><fmt:formatNumber
					value="${results.result.receiverFee}" pattern="#.##" /></font>
		</c:if>
	</c:if>
	&nbsp;&nbsp;
	<font color='#00f'>总交易成本：</font>
	<c:if test="${results == null}">
		<font color='#00f'> 0.0 
	</c:if>
	<c:if test="${results != null}">
		<c:if test="${results.result == null}">
			<font color='#00f'> 0.0 
		</c:if>
		<c:if test="${results.result != null}">
			<font color="red"><fmt:formatNumber
					value="${results.result.cost}" pattern="#.##" /></font>
		</c:if>
	</c:if>
	&nbsp;&nbsp;
	<font color='#00f'>总笔数：</font>
	<font color="red">${results.count }</font>
</body>
</html>