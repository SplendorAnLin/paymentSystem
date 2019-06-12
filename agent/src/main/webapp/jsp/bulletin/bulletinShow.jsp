<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>	
	<script type="text/javascript">
		function showMore(){
			$("#more").slideToggle();
		}
		function detail(id){
			$("#new"+id).hide();
			var url="bulletinDetail.action?bulletin.id="+id;
			window.location.href=url;
			window.parent.parent.parent.refresh("popframe-1", url);
			window.parent.parent.parent.showDialog("popdiv-1");	
		}
	</script>
	<style>
		.bulletion{padding:10px;border:1px solid #e8e7e1}
		.bulletion p{height:18px;text-indent:2em;}
	</style>
</head>
<body>
	<div class="bulletion">
<s:iterator id="bulletion" value="bulletinBoss" status="l" >
<p>${l.index+1 }.${bulletion.title }<a href='javascript:detail("${bulletion.id }")'>详细</a></p>
<s:if test="${l.index==4&&bulletinBoss.size()>5}">
<br/>	 	 	
   	 	<p><a href="javascript:void(0)" onclick="showMore()">更多公告>></a></p>
   	 	<div id="more" style="display: none"> 
</s:if>
<s:if test="${l.index>4 }">
<p>${l.index+1 }.${bulletion.title }<a href='javascript:detail("${l.index}")'>详细</a></p>
</s:if>
<s:if test="${l.index==bulletinBoss.size()-1 }">
</div>  
</s:if>
</s:iterator>
    </div>
</body>
</html>
