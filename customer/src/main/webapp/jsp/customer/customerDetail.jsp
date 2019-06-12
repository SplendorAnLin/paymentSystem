<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>	
	<script type="text/javascript">
		function check(){
			var name = $("#name").val().trim();
			var linkMan = $("#linkMan").val().trim();
			var phone = $("#phone").val().trim();
			var addr = $("#addr").val().trim();
			
			if(name == ''){
				alert('商户名称不能为空，请重新输入！'); 
			    return false; 
			}
			
			if(linkMan == ''){
				alert('联系人不能为空，请重新输入！'); 
			    return false; 
			}
			
			if(phone == ''){
				alert('联系电话不能为空，请重新输入！'); 
			    return false; 
			}
			
			var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
			if(!myreg.test($("#phone").val())) 
			{ 
			    alert('请输入有效的手机号码！'); 
			    return false; 
			}
			
			if(addr == ''){
				alert('商户名称不能为空，请重新输入！'); 
			    return false; 
			}
		}
	</script>
</head>
<body>
		
		<div class="detail_tit"><h2>修改公交商户</h2></div>
		
		<form action="customerUpdate.action" method="post" id="formID" onsubmit="return check();">
			<input name="customer.id" value="${customer.id}" type="hidden">
			<table class="global_table" cellpadding="0" cellspacing="0">
				<tr>
					<th width="40%">商户名称：</th>
					<td width="60%">
						${customer.name}
					</td>
				</tr>
				<tr>
					<th width="40%">联系人：</th>
					<td width="60%">
						<input type="text" id= "linkMan" name="customer.linkMan" value="${customer.linkMan}" class="inp" />
					</td>
				</tr>
 				<tr>
					<th>联系电话：</th>
					<td>						
						<input class="inp" id ="phone" type="text" name="customer.phone" value="${customer.phone}"/>						
					</td>
				</tr>
				<tr>	
					<th>地址：</th>
					<td>
						<input class="inp" id="addr" type="text" name="customer.addr" value="${customer.addr}"/>
					</td>
				</tr>
				<tr>	
					<th>状态：</th>
					<td>
						<select name="customer.status">
							<option value="TRUE" <c:if test="${customer.status eq 'TRUE'}">selected="selected"</c:if>>启用</option>
							<option value="FALSE" <c:if test="${customer.status eq 'FALSE'}">selected="selected"</c:if>>停用</option>
						</select>
						
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
