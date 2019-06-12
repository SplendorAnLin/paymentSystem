<body>
	<%
		session.invalidate();
	%>
	<script type="text/javascript">
		window.parent.parent.location = '${pageContext.request.contextPath}';
		//window.parent.parent.location = 'http://cny.bank-pay.com.cn/';
	</script>
</body>