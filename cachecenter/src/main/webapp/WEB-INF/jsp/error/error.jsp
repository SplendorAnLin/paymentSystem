<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出错啦</title>
</head>
<body>
	<%
		Exception e = (Exception) request.getAttribute("exception");
	%>
	<H2>
		<%=e.getMessage()%></H2>
	<P />
	<%
		e.printStackTrace(new java.io.PrintWriter(out));
	%>
</body>
</html>