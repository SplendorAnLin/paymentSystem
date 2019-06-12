<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
	<LINK href="${pageContext.request.contextPath}/js/dtree/dtree.css" type="text/css" rel="stylesheet">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/dtree/dtree.js"></script>
	<style>
	    .moreMenu{
		 SCROLLBAR-FACE-COLOR:#EFF4F9;
		 SCROLLBAR-HIGHLIGHT-COLOR:#C9D9F1;
		 OVERFLOW:auto;
		 width:100%;
		 SCROLLBAR-SHADOW-COLOR:#FFFFFF;
		 SCROLLBAR-3DLIGHT-COLOR:#FFFFFF;
		 SCROLLBAR-ARROW-COLOR:#000000;
		 SCROLLBAR-TRACK-COLOR:#EFF4F9;
		 SCROLLBAR-DARKSHADOW-COLOR:#C9D9F1;
		 height:100%
		}

	</style>
	
	<script type="text/javascript">
		$(function() {
			
		});
		
		function menuEdit(id){
			$("#menuId").val(id);
			$("#form1").submit();
		}
	</script>
</head>
<body>

	<div style="width:30%;height:600px" class="moreMenu">
		<form id="form1" action="toMenuEdit.action" method="post" target="menuDetail">
			<input type="hidden" id="menuId" name="menuId" value=""/>
		</form>
		<div class="dtree" style="border:0px solid #ccc;padding:10px;">
		   <p style="background-color:#eee;border:1px solid #ddd;padding:3px 10px;">
		      <a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>
		   </p>
		   <br/>
		    <script type="text/javascript">
		   		var d = new dTree("d");
		   		<c:forEach items="${menuList}" var="m">
		   			d.add(${m.id},${m.parentId},"${m.name}","javascript:menuEdit('${m.id}')","${m.name}");
		   		</c:forEach>
		   		document.write(d);
		   </script>
		</div>
	</div>
	
	<div style="float:right;background-color:#eee; width:69%;margin-top:-600px;height:900px;">
 	 	<iframe id="menuDetail" name="menuDetail" src="" width="100%" height="100%" scrolling="no" frameborder="0" noresize ></iframe>
 	</div>
</body>

</html>