<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>
</head>
<body>
	<div class="search_tit"><h2>商户查询</h2></div>
	<form action="customerQuery.action" method="post" target="queryResult" id="form1">
		<table class="global_table" cellpadding="0" cellspacing="0">	
			<tr>
				<th width="10%">商户名称：</th>
				<td width="20%">
					<input type="text" name="name" class="inp width150"/>	
				</td>
				<th width="10%">商户编号：</th>
				<td width="20%">
					<input type="text" name="customer_no" class="inp width150"/>	
				</td>
			</tr>
			<tr>
				<th width="10%">联系电话：</th>
				<td width="20%">
					<input type="text" name="phone" class="inp width150"/>	
				</td>
				
				<th width="10%">状态：</th>
				<td width="20%">
					<select name="status">
						<option></option>
						<option value="TRUE">启用</option>
						<option value="FALSE">停用</option>
					</select>
				</td>
			</tr>
		</table>
		<br>
		<center>
			<input type="submit" id="btn" class="global_btn" value="查 询" />
			<input type="reset" class="global_btn" value="重 置" />
		</center>
	</form>
	<iframe name="queryResult" src="" width="100%" height="520px" scrolling="no" frameborder="0" noresize ></iframe>

</body>
</html>
