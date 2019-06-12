<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
	<title></title>
	<script type="text/javascript">
		$(document).ready(function(){
			//树的基本信息设置
			var setting = {
				showLine : false,
			  	nodesCol : "children", 
				checkable: true 
			};
						
			//菜单树
			var sampleNodes = ${menuTreeJSONString} ;

			
			//创建树
			zTree1 = $("#treeDemo").zTree(setting, sampleNodes);
			zTree1.expandAll(true) ;
			
			//整理出来选中菜单ID的字符串
			$("#formId").submit(function(){
				//整理字符串
				var selectedNode = zTree1.getCheckedNodes();
				var idStr="" ;
				for(var i=0 ; i<selectedNode.length ;i++){
					if( i == 0 ){
						idStr = selectedNode[i].id ;
					}else{
						idStr +=("_"+selectedNode[i].id) ;
					}
				}
				$("#chosedMenuIds").val(idStr) ;
				return true ;
			});
			
		});
		</script>
</head>
<body>	
	<div style="position:relative;width:100%;">
		<form id="formId" action="roleMenuUpdate.action" method="post">
			<input type="hidden" name="id" value="${role.id}" />
			<input id="chosedMenuIds" type="hidden" name="chosedMenuIds" value="" />
			<div style="position:relative;width:100%;height:500px;overflow-y:auto;">
				
				<ul id="treeDemo" class="tree"></ul>
			</div>
			<div style="position:relative;height:20px;"></div>
			<div>
				<p style="position:relative;left:60px;">
					<input type="submit" class="global_btn" value="提交" />
					<input type="reset" class="global_btn" value="取消" />
				</p>
			</div>
		</form>
		
		
	</div>
</body>
</html>