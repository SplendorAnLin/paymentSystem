<body>  
<% 
session.invalidate();  
%> 
<script type="text/javascript">
	window.parent.parent.location = '${pageContext.request.contextPath}';
</script>
</body>
