<%@ page language="java" contentType= "text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<script>	
	$(document).ready(function(){
 		var url = '${pageContext.request.contextPath}/image/loading_${param.id}.gif';
		$("#img").attr('src',url); 
	});	
</script>
<div style="text-align:${param.align};">
	<img id="img" src="" />
</div>
