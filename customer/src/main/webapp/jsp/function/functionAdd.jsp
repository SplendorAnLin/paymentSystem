<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.*"%>

<html>
<head>	
	<title>新增银行</title>

	<script type="text/javascript">
	</script>
</head>
<body>
	<div class="detail_tit"><h2>新增功能</h2></div>
	<form action="functionAdd.action" method="post" id="form1">
		
		<table class="global_table" cellpadding="0" cellspacing="0">
			<tr>
				<th width="30%">功能名称：</th>
				<td width="70%">
					<input class="inp width220" type="text" name="function.name" />
				</td>
			</tr>	
			<tr>	
				<th>ACTION：</th>
				<td>
					<input class="inp width220" type="text" name="function.action" value=".action" />							
				</td>
			</tr>	
			<tr>
				<th>是否验证：</th>
				<td>
					<dict:select dictTypeId="YesNo" styleClass="width150" name="function.isCheck"></dict:select>
				</td>
			</tr>	
			<tr>	
				<th>状态：</th>
				<td>
					<dict:select dictTypeId="Status" styleClass="width150" name="function.status"></dict:select>
				</td>			
			</tr>	
			<tr>
				<th>备注：</th>
				<td>
					<textarea rows="5" cols="60" name="function.remark"></textarea>
				</td>			
			</tr>	
		</table>
		
		<br>
		<center>
			<input type="submit"  value="提 交" class="global_btn""/> 
		  	<input type="reset"  value="重 置" class="global_btn"/>
		</center>
	</form>
</body>
</html>
