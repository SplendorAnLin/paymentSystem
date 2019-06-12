<%@page import ="java.util.Enumeration,java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Iterator"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>跳转中</title>
</head>
<body>
<script>
	function sub() {
		if(document.getElementById("form1").action != ''){
			document.forms[0].submit();
		}else{
			window.location.href = '';
		}
	}
</script>
<body onload="sub()">
	<div style="display: none;">
		<form id="form1" action="${result.url}" method="post">
			<c:forEach items="${result.params }" var="o">
				<input type="text" name="${o.key }" value="${o.value}" />
			</c:forEach>
		</form>
	</div>
</body>
</body>
</html>