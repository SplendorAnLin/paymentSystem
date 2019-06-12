<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#username").addClass("validate[required]") ;
			$("#password").addClass("validate[required]") ;
			$("#newpassword").addClass("validate[required,length[8,16]]") ;
			$("#newpasswordconfirm").addClass("validate[required,confirm[newpassword]] text-input") ;
			
			$("#formID").validationEngine();
		});
	</script>
</head>
<body>
		
		<div class="detail_tit"><h2>审核密码修改</h2></div>
		
		<form action="operatorAuditPasswordUpdate.action" method="post" id="formID">
			
			<table class="global_table" cellpadding="0" cellspacing="0">
				<tr>
					<th width="20%">登录名：</th>
					<td width="40%">
						<input type="text" id= "username" name="operator.username" value="${sessionScope.auth.username}" readonly="readonly" class="inp" />
					</td>
				</tr>
				<tr>	
					<th>原密码：</th>
					<td>
						<input type="password" id="password" name="operator.complexPassword" class="inp" />
					</td>
				</tr>
				<tr>
					<th>新密码：</th>
					<td>						
						<input class="inp" id ="newpassword" type="password" name="newpassword"/>						
					</td>
				</tr>
				<tr>	
					<th>新密码确认：</th>
					<td>
						<input class="inp" id="newpasswordconfirm" type="password" name="newpasswordconfirm"/>
					</td>
				</tr>				
				<tr>	
					<th>&nbsp;</th>
					<td>
						<s:actionerror/>
						<s:fielderror />
					</td>
				</tr>
				
			</table>
							
			<br>		
			<center>
				<input type="submit" value="确定" class="global_btn" /> 
			  	<input type="reset"  value="重 置" class="global_btn"/>
			<center>
			
		</form>
	</div>

</body>
</html>
