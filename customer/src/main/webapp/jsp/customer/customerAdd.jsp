<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>	
	<script type="text/javascript">
		function check(){
			var name = $("#name").val().trim();
			var linkMan = $("#linkMan").val().trim();
			var phone = $("#phone").val().trim();
			var ip = $("#ip").val().trim();
			var domain = $("#domain").val().trim();
			var auditType = $("[name = 'serviceConfigBean.manualAudit']").find("option:selected").value();
			
			if(ip != ''){
				var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
				var reg = obj.match(exp);
				if(reg==null){
				alert("IP地址不合法，请重新输入！");
				}
			}
			
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
			
			var regu =/^[1][3|5|8|7][0-9]{9}$/;
			var re = new RegExp(regu);
			if(!re.test($("#phone").val())) 
			{ 
			    alert('请输入有效的手机号码！'); 
			    return false; 
			}
		}
	</script>
</head>
<body>
		
		<div class="detail_tit"><h2>新增商户</h2></div>
		
			<br><br><br>
		<form action="customerAdd.action" onsubmit="return check();" method="post" id="formID">
			<table class="global_table" cellpadding="0" cellspacing="0">
				<tr><td width="100%" colspan="4" align="center">商户信息</td></tr>
				<tr>
					<th width="20%">商户名称：</th>
					<td width="30%">
						<input type="text" id= "name" name="customer.name" value="" class="inp" />
					</td>
					<th width="20%">联系人：</th>
					<td width="30%">
						<input type="text" id= "linkMan" name="customer.linkMan" value="" class="inp" />
					</td>
				</tr>
				<tr>
					<th>联系电话：</th>
					<td>						
						<input class="inp" id ="phone" type="text" name="customer.phone"/>						
					</td>
					<th>状态：</th>
					<td>
						<select name="customer.status" style="width: 150px;">
							<option value="TRUE">启用</option>
							<option value="FALSE">停用</option>
						</select>
					</td>
				</tr>
				<tr><td width="100%" colspan="4" align="center">代付信息</td></tr>
				<tr>
					<th>状态：</th>
					<td>
						<select name="serviceConfigBean.valid" style="width: 150px;">
							<option value="TRUE">启用</option>
							<option value="FALSE">停用</option>
						</select>
					</td>
					<th>审核：</th>
					<td>
						<select name="customer.manualAudit" style="width: 150px;">
							<option value="FALSE">人工</option>
							<option value="TRUE">自动</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>短信验证：</th>
					<td>
						<select name="customer.usePhoneCheck" style="width: 150px;">
							<option value="FALSE">停用</option>
							<option value="TRUE">启用</option>
						</select>
					</td>
					<th></th>
					<td>
					</td>
				</tr>
				<tr>
					<th>IP：</th>
					<td>
						<input class="inp" id ="ip" type="text" name="customer.custIp"/><font color="red">*不填不校验</font>
					</td>
					<th>域名：</th>
					<td>
						<input class="inp" id ="domain" type="text" name="customer.domain"/><font color="red">*不填不校验</font>
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
