<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>	
	<script type="text/javascript">
		function doSubmit(){
			var linkMan = $("#linkMan").val().trim();
			var phone = $("#phone").val().trim();
			var ip = $("#ip").val().trim();
			var domain = $("#domain").val().trim();
			var auditType = $("[name = 'serviceConfigBean.manualAudit']").find("option:selected").val();
			
			if(ip != ''){
				var exp=/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
				var reg = obj.match(exp);
				if(reg==null){
				alert("IP地址不合法，请重新输入！");
				}
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
			if(!re.test($("#phone").val())){
			    alert('请输入有效的手机号码！'); 
			    return false; 
			}
			
			$("#formID").submit();
		}
		
		/**
		* 检查输入的URL地址是否正确
		* 输入:str  字符串
		*  返回:true 或 flase; true表示格式正确
		*/
		function checkURL(str) {
		    if (str.match(/(http[s]?):\/\/[^\/\.]+?\..+\w$/i) == null) {
		        return false
		    }
		    else {
		        return true;
		    }
		}
		
		/* function customerStatusChange(){
			var custStatus = $("[name = 'customer.status']").find("option:selected").val();
			if(custStatus == 'FALSE'){
				$("[name = 'serviceConfigBean.valid']").find("option[value='FALSE']").attr("selected",true);
			}
		} */
	</script>
</head>
<body>
		
		<div class="detail_tit"><h2>修改商户</h2></div>
		
			<br><br><br>
		<form action="customerUpdate.action" method="post" id="formID">
			<input type="hidden" value="${customer.id}" name="customer.id"/>
			<input type="hidden" value="${customer.customerNo}" name="customer.customerNo"/>
			<table class="global_table" cellpadding="0" cellspacing="0">
				<tr><td width="100%" colspan="4" align="center">商户信息</td></tr>
				<tr>
					<th width="20%">商户编号：</th>
					<td width="30%">
						${customer.customerNo}
					</td>
					<th width="20%"></th>
					<td width="30%">
					</td>
				</tr>
				<tr>
					<th width="20%">商户名称：</th>
					<td width="30%">
						${customer.name}
					</td>
					<th width="20%">联系人：</th>
					<td width="30%">
						<input type="text" id= "linkMan" name="customer.linkMan" value="${customer.linkMan}" class="inp" />
					</td>
				</tr>
				<tr>
					<th>联系电话：</th>
					<td>						
						<input class="inp" id ="phone" type="text" name="customer.phone" value="${customer.phone}"/>
					</td>
					<th>状态：</th>
					<td>
						<select name="customer.status" style="width: 150px;">
							<option value="TRUE" <c:if test="${customer.status eq 'TRUE'}">selected="selected"</c:if>>启用</option>
							<option value="FALSE"<c:if test="${customer.status eq 'FALSE'}">selected="selected"</c:if>>停用</option>
						</select>
					</td>
				</tr>
				<tr><td width="100%" colspan="4" align="center">代付信息</td></tr>
				<tr>
					<th>状态：</th>
					<td>
						<select name="serviceConfigBean.valid" style="width: 150px;">
							<option value="TRUE"<c:if test="${serviceConfigBean.valid eq 'TRUE'}">selected="selected"</c:if>>启用</option>
							<option value="FALSE"<c:if test="${serviceConfigBean.valid eq 'FALSE'}">selected="selected"</c:if>>停用</option>
						</select>
					</td>
					<th>审核：</th>
					<td>
						<select name="customer.manualAudit" style="width: 150px;">
							<option value="FALSE"<c:if test="${customer.manualAudit eq 'FALSE'}">selected="selected"</c:if>>人工</option>
							<option value="TRUE"<c:if test="${customer.manualAudit eq 'TRUE'}">selected="selected"</c:if>>自动</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>短信验证：</th>
					<td>
						<select name="customer.usePhoneCheck" style="width: 150px;">
							<option value="FALSE"<c:if test="${customer.usePhoneCheck eq 'FALSE'}">selected="selected"</c:if>>停用</option>
							<option value="TRUE"<c:if test="${customer.usePhoneCheck eq 'TRUE'}">selected="selected"</c:if>>启用</option>
						</select>
					</td>
					<th></th>
					<td>						
					</td>
				</tr>
				<tr>
					<th>IP：</th>
					<td>
						<input class="inp" id ="ip" type="text" name="customer.custIp" value="${customer.custIp}"/><font color="red">*不填不校验</font>
					</td>
					<th>域名：</th>
					<td>
						<input class="inp" id ="domain" type="text" name="customer.domain" value="${customer.domain}"/><font color="red">*不填不校验</font>
					</td>
				</tr>
			</table>
							
			<br>		
			<center>
				<input type="button" value="确定" class="global_btn" onclick="doSubmit();"/> 
			  	<input type="reset"  value="重 置" class="global_btn"/>
			<center>
			<br><br><br><br><br>
			<div>
<!-- 				<font color="blue">提示：1、自动审核可不填手机号、代付密码！</font></br> -->
			</div>
			
		</form>
	</div>

</body>
</html>
