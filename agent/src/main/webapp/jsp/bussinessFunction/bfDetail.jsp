<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>

<html>
<head>	
	<title>功能编码详细信息</title>
	<script type="text/javascript">
		function sub(){
			var bfId = '${bussinessFunction.id}';
			var roleIdArray = new Array();
			var checkedBox = $("input:checked");
				if(checkedBox.length==0){
					dialogMessage("请至少选择一条记录");
					return;
				}else{
					for(var i=0;i<checkedBox.length;i++){
						roleIdArray.push(checkedBox[i].value);
					}
					var roleIds = roleIdArray.join(',');
					$.ajax({
						type:"post",
						url:"addBfRole.action",
						data:{bfId:bfId,roleIds:roleIds},
						success:function(msg){
							alert(msg);
							window.location.href = window.location.href;
						}
					});
				}
		}
	
	</script>
</head>
<body>
	<div class="detail_tit"><h2>功能编码详细信息</h2></div>
		<table class="global_table" cellpadding="0" cellspacing="0">
			<tr>
				<th width="30%">BF_CODE：</th>
				<td width="70%">
					${bussinessFunction.code}
				</td>
			</tr>	
			<tr>	
				<th>BF名称：</th>
				<td>
					${bussinessFunction.name}						
				</td>
			</tr>
			<tr>	
				<th>BF描述：</th>
				<td>
					${bussinessFunction.description}							
				</td>
			</tr>	
			<tr>
				<th>所属子系统：</th>
				<td>
					<dict:write dictId="${bussinessFunction.sysCode}" dictTypeId="bfSys"></dict:write>
				</td>
			</tr>	
			<tr>
				<th>所属类别：</th>
				<td>
					${menu.name}
				</td>
			</tr>
			<tr>	
				<th>状态：</th>
				<td>
					<c:if test="${bussinessFunction.status=='TRUE'}">可用</c:if>
					<c:if test="${bussinessFunction.status=='FALSE'}">不可用</c:if>
				</td>			
			</tr>
			<tr>
				<th>关联菜单：</th>
				<td>
					<c:forEach items="${bussinessFunction.menus}" var="menu">
						${menu.name}>>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>关联功能：</th>
				<td>
					<c:forEach items="${bussinessFunction.functions}" var="function">
						${function.name}(${function.action})&nbsp;&nbsp;&nbsp;
					</c:forEach>
				</td>
			</tr>
			<tr>
				<th>关联岗位：</th>
				<td>
					<c:forEach items="${bussinessFunction.posts}" var="post">
						${post.postName}&nbsp;&nbsp;&nbsp;
					</c:forEach>
				</td>
			</tr>
			
			<c:if test="${auth.username == 'lei.zhong'}">
				<tr>
					<th>选择角色：</th>
					<td>
						<c:forEach items="${allRoles}" var="allRole" varStatus="i">
							<c:if test="${marks[i.index]==0}">
								<input type="checkbox" name="roleId" value="${allRole.id}"/>${allRole.name}&nbsp;&nbsp;
							</c:if>
							<c:if test="${marks[i.index]==1}">
								<input type="checkbox" checked="checked" name="roleId" value="${allRole.id}"/>${allRole.name}&nbsp;&nbsp;
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td colspan="2">
					<center>
						<input type="button" class="global_btn" value="确定" onclick="sub()"/>
					</center>
					</td>
				</tr>
			</c:if>	
			<c:if test="${auth.username == 'zhen.wang'}">
				<tr>
					<th>关联角色：</th>
					<td>
					<c:forEach items="${bussinessFunction.roles}" var="role">
						${role.name}&nbsp;&nbsp;&nbsp;
					</c:forEach>
					</td>
				</tr>
			</c:if>	
		</table>
		<br>
		<center>
		  	<input type="button" class="global_btn" value="返回" onclick="javascript:history.go(-1);"/>
		</center>
</body>
</html>
