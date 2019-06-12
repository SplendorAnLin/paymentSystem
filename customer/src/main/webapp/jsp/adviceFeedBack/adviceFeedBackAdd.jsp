<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<html>
<head>	
	<title>反馈意见</title>
		
	<script type="text/javascript">
		
	</script>
</head>

<body>
	<div class="detail_tit" ><h2>反馈意见</h2></div>
	
	<form action="adviceFeedBackAdd.action" id="form1" method="post">
	  	
	  	<table cellpadding="0" cellspacing="1" class="global_tabledetail">							
			<tr>
				<th>意见内容： </th>
				<td colspan="3">
					<textarea rows="4" cols="75" id="content" name="adviceFeedBack.content"></textarea>
				</td>
			</tr>	
			
		</table>
		<br>
		<center>
			<input type="submit" class="global_btn" value="提交" />
			<input type="reset" class="global_btn" value="重置" />
		</center>		
	</form>
</body>
</html>
