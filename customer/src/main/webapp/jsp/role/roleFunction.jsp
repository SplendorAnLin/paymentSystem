<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%
//清除页面的缓存（由于在IE下出现页面缓存的现象）    
response.setHeader("Pragma","No-cache");    
response.setHeader("Cache-Control","no-cache");    
response.setDateHeader("Expires", -10);   
%>
<html>
<head>
<script type="text/javascript">
	$(function(){
		//全选
		$('#inputChkAll').click(function(){
			$("input[id='chk']").attr("checked", $(this).attr("checked"));
		});
		
		//反选
		$("#reverse").click(function(){
	        $("input[id='chk']").each(function(idx,item){ 
	           $(item).attr("checked",!$(item).attr("checked"));  
	        });                          
	  	})
	});
	
	//修改角色功能
	function manageAllChoseFunctionId(){
		var addRoleFunction = "";
	    $('input[name="function"]:checked').each(function(){ 
	      	addRoleFunction+=$(this).val()+'-'; 
	    });
	    $("#formId").attr("action","updateRoleFunction.action?functionIds="+addRoleFunction);
	    $("#formId").submit();
	}
</script>
</head>
<body>	

	<form id="formId"  method="post">
		<input type="hidden" name="role.id" value="${role.id}" />
		<input id="roleFunctionString" type="hidden" name="roleFunctionString" value="" />
		
		<div id="roleFunctionModify" style="height:540px;overflow-y:auto;padding:8px;">
					
			<p>
				<input type="radio" name="1" id="inputChkAll" >&nbsp;全部选择
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="radio" name="1" id="reverse">&nbsp;反向选择
			</p>
								
			<p style="color:#008800;padding:10px 0px">已关联的功能列表：</p>
			<div>	
				<s:if test="functionList.size()>0">
					<table class="global_table" cellpadding="0" cellspacing="0" >
						<tr>
							<%int i = 0;%>
							<s:iterator id="function1" value="functionList">
								<td width="25%">
									<input type="checkbox" checked="checked" name="function" value="${function1.id}">&nbsp;
									${function1.name}
								</td>
								<%	
									if( ++i%3 == 0){
										i=0;
										out.println("</tr><tr>");
									}
								%>
							</s:iterator>
						</tr>
					</table>
				</s:if>				
			</div>
									
			<p style="font-size:12px;color:#008800;padding:10px 0px">未关联的功能列表：</p>
			<div>
				<s:if test="othersFunction.size()>0">
					<table class="global_table" cellpadding="0" cellspacing="0" >
						<tr>
							<%int j = 0;%>
							<s:iterator id="function2" value="othersFunction">
								<td width="25%">
									<input type="checkbox"  name="function"  id="chk" value="${function2.id}">&nbsp;
									${function2.name}
								</td>
								<%	
									if( ++j%3 == 0){
										j=0;
										out.println("</tr><tr>");
									}
								%>
							</s:iterator>
						</tr>											
					</table>
				</s:if>
			</div>
					
		 </div>
						
		<center>
			<input type="button" class="global_btn" value="提交" onclick="manageAllChoseFunctionId();"/>
			<input type="reset" class="global_btn" value="取消" />
		<center>
		
	</form>	
</body>
</html>