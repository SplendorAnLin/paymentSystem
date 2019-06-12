<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<head>		
	<script type="text/javascript">	
	</script>
</head>

<body>		
	<div class="search_tit"><h2>功能查询</h2></div>
	<form action="functionQuery.action" method="post" target="queryResult">
		
		<table class="global_table" cellpadding="0" cellspacing="0">	
			<tr>
				<th width="20%">ACTION名称：</th>
				<td width="30%">
					<input class="inp width220" type="text" name="action" />
				</td>
				<th width="20%">功能名称：</th>
				<td width="30%">
					<input class="inp width220" type="text" name="name" />
				</td>							
			</tr>	
		</table>
		
		<br>
		<center>
			<input type="submit" class="global_btn" value="查 询" />
			<input type="reset" class="global_btn" value="重 置" />
		</center>
	</form>	
	<br>	
	<div class="total_panel">
 		<div class="add">
			<a href="toFunctionAdd.action" style="text-decoration: none">新增</a>			
		</div>								
 	</div>
	<iframe name="queryResult" src="" width="100%" height="520px" scrolling="no" frameborder="0" noresize ></iframe>
</body>
