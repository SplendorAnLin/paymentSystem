<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
	<script type="text/javascript">
		$(function() {
			
		});
		
		function modify(){
			var status = '${menu.status}';
			if(status == 'TRUE'){
				$("#status1").attr("checked","checked");
			}
			if(status == 'FALSE'){
				$("#status2").attr("checked","checked");
			}
			$("#modify").show();
		}
		
		function addChild(){
			$("#add").show();
		}
		
	</script>
</head>
<body>
	<div class="detail_tit" ><h2>菜单详细信息</h2></div>
		<table class="global_table" cellpadding="0" cellspacing="0">
 			<tr>
 				<th width="20%">菜单名称：</th>
 				<td width="30%">${menu.name}</td>
 				<th width="20%">菜单级别：</th>
 				<td width="30%">${menu.level}</td>
 			</tr>
 			<tr>
 				<th>显示顺序：</th>
 				<td>${menu.displayOrder}</td>
 				<th>菜单状态：</th>
 				<td>
 					<s:if test="${menu.status == 'TRUE'}">可用</s:if>
 					<s:if test="${menu.status == 'FALSE'}">不可用</s:if>
 				</td>
 			</tr>
 			<tr>
 				<th>菜单链接：</th>
 				<td colspan="3">${menu.url}</td>
 			</tr>
 		</table>
 		<br/>
 		<center>
 			<input type="button" class="global_btn" value="修改菜单" onclick="modify()"/>
 			<c:if test="${menu.level != '2'}">
 				<input type="button" class="global_btn" value="添加子菜单" onclick="addChild()"/>
 			</c:if>
 		</center><br/><br/>
 		
 		
 	<!-- *****************修改菜单********************** -->	
 	 	
 	<div id="modify" style="display:none">
 		<div class="detail_tit" ><h2>修改菜单信息</h2></div> 		
 		<form id="form1" action="modifyMenu.action" method="post"> 			
 			<input type="hidden" name="menu.id" value="${menu.id}"/>
 			<table class="global_table" cellpadding="0" cellspacing="0">
 			<tr>
 				<th width="20%">菜单名称：</th>
 				<td width="30%"><input type="text" id="name" name="menu.name"  class="inp width180" value="${menu.name}"/></td>
 				<th width="20%">菜单级别：</th>
 				<td width="30%">${menu.level}</td>
 			</tr>
 			<tr>
 				<th>显示顺序：</th>
 				<td><input type="text" id="displayOrder" name="menu.displayOrder" class="inp width180" value="${menu.displayOrder}"/></td>
 				<th>菜单状态：</th>
 				<td>
 					<input type="radio" id="status1" name="menu.status" value="TRUE" class="inp width10"/>可用
 					&nbsp;&nbsp;&nbsp;
 					<input type="radio" id="status2" name="menu.status" value="FALSE" class="inp width10"/>不可用
 				</td>
 			</tr>
 			<tr>
 				<th>菜单链接：</th>
 				<td colspan="3"><input type="text" id="url" name="menu.url" class="inp width450" value="${menu.url}"/></td>
 			</tr>
 			<c:if test="${menu.level == '2'}">
 			<tr>
 				<th>选择上级菜单：</th>
 				<td colspan="3">
	 				<select id="pid" name="menu.parentId" class="inp width180">
	 					<c:forEach items="${pMenus}" var="pm">
	 						<c:if test="${pm.id == menu.parentId}"><option value="${pm.id}" selected="selected">${pm.name}</option></c:if>
	 						<c:if test="${pm.id != menu.parentId}"><option value="${pm.id}">${pm.name}</option></c:if>
	 					</c:forEach>
	 				</select>
	 			</td>
 			</tr></c:if>
 		</table><br/>
 		<center>
 			<input type="submit" class="global_btn" value="提交" />
 			<input type="reset" class="global_btn" value="重置"/>
 		</center>
 		</form>
 	</div>
 		
	<!-- *****************添加子菜单********************** -->	
 	<div id="add" style="display:none">
 		<div class="detail_tit" ><h2>添加子菜单</h2></div> 
 			<form id="form2" action="addChildMenu.action" method="post">
 			<input type="hidden" name="menu.parentId" value="${menu.id}"/>
 			<table class="global_table" cellpadding="0" cellspacing="0">
 			<tr>
 				<th width="20%">菜单名称：</th>
 				<td width="30%"><input type="text" id="nameAdd" name="menu.name" class="inp width180"/></td>
 				<th></th>
 				<td></td>
 			</tr>
 			<tr>
 				<th>显示顺序：</th>
 				<td><input type="text" id="displayOrderAdd" name="menu.displayOrder" class="inp width180"/></td>
 				<th>菜单状态：</th>
 				<td>
 					<input type="radio" id="statusAdd1" name="menu.status" value="TRUE" checked="checked" class="inp width10"/>可用
 					&nbsp;&nbsp;&nbsp;
 					<input type="radio" id="statusAdd2" name="menu.status" value="FALSE" class="inp width10"/>不可用
 				</td>
 			</tr>
 			<tr>
 				<th>菜单链接：</th>
 				<td colspan="3">
 					<input type="text" id="urlAdd" name="menu.url" class="inp width450"/>
 				</td>
 			</tr>
 		</table><br/>
 		<center>
 			<input type="submit" class="global_btn" value="提交" />
 			<input type="reset" class="global_btn" value="重置"/>
 		</center>
 		</form>
 	</div> 		
</body>

</html>