<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript">		
		function toModify(id){
			var url = "${pageContext.request.contextPath}/tofunctionModify.action?id="+id;
			window.parent.parent.refresh("popframe-1", url);
			window.parent.parent.showDialog("popdiv-1");	
		}
		function toDelete(id){
			$( "#dialog" ).dialog( "destroy" );
			$( "#dialog-confirm-info").html("确认是否删除该功能?");	
			$( "#dialog-confirm" ).dialog({
				resizable: false,
				height:140,
				modal: true,
				buttons: { "确定": function() {
							$( this ).dialog( "close" );
							  deleteFunction(id)	
						   },
						   "取消": function() { $( this ).dialog( "close" );}
					      }
			});
			
		}
		function deleteFunction(id){
			$.post( "<%=request.getContextPath()%>/deleteFunction.action",
				{"id":id}, 
		  		function (data){
		  			if("success"==$.trim(data)){
		  				window.location.href = window.location.href;
		  			}else{
		  				dialogMessage("删除失败");
		  			}
		  		}
		  	);
		}
	</script>	
	<style>
		.global_tableresult .search_results0{
			text-align:left;padding-left:10px;
		}
		.global_tableresult .search_results1{
			text-align:left;padding-left:10px;
		}
	</style>
</head>
<body>	

	<s:if test="#attr['function'].list.size()>0">
		<vlh:root value="function" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="function">					
					<s:set name="function" value="#attr['function']" />		
					<vlh:column title="功能名称" property="name"	 attributes="width='12%'" />
					<vlh:column title="ACTION" property="action" attributes="width='15%'" />					
					<vlh:column title="状态" attributes="width='5%'">
						<dict:write dictId="${function.status}" dictTypeId="STATUS_COLOR"></dict:write>
					</vlh:column>
					<vlh:column title="是否验证" attributes="width='5%'">
						<dict:write dictId="${function.is_check}" dictTypeId="YesNo"></dict:write>
					</vlh:column>
					<vlh:column title="类型" property="type"	 attributes="width='10%'" />
					<vlh:column title="描述" property="remark" attributes="width='20%'" />
					<vlh:column title="操作" attributes="width='10%'">
						<a href="javascript:toModify(${function.id })">修改</a>
						&nbsp;&nbsp;
						<a href="javascript:toDelete(${function.id })">删除</a>
					</vlh:column>
				</vlh:row>
			</table>
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='500'" />
			</div>				
		</vlh:root>
	</s:if>	
	<s:if test="#attr['function'].list.size()==0">
		无符合条件的查询结果
	</s:if>
</body>
</html>
