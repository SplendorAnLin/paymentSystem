<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body onload="sub()">
	<div style="display: none"><!-- style="display: none" -->
		<form id="form1" action="${params.url}" method="post" >
			<c:forEach items="${params }" var="o">
			<c:if test="${o.key  != 'gateway' }">
				<c:if test="${o.key  != 'url' }">
					<c:if test="${o.key  != 'interfaceRequestID' }">
						<c:if test="${o.key  != 'responseCode' }">
							<c:if test="${o.key  != 'payPage' }">
								<c:if test="${o.key  != 'customerName' }">
									<c:if test="${o.key  != 'customerNo' }">
										<c:if test="${o.key  != 'outOrderId' }">
											${o.key}:<input type="text" name="${o.key }" value='${o.value }' /><br/>
										</c:if>
									</c:if>
								</c:if>
							</c:if>
						</c:if>
					</c:if>
				</c:if>
			</c:if>
		</c:forEach>
		</form>
	</div>
</body>
<script type="text/javascript">
	function sub() {
		document.forms[0].submit();
	}
</script>
</html>
