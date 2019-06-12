<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<head>		
	<script type="text/javascript">	
		function loadChMenus(){
		var pid = $("#type1").val();
		$.ajax({
			type:"post",
			url:"loadChMenus.action",
			data:{pid:pid},
			success:function(msg){
				var html='';
				if(msg==''){
					$("#type2").html(html);
					return;
				}else{
					$.each(msg,function(i,entity){
						html += '<option value="'+entity.id+'">'+entity.name+'</option>';
						$("#type2").html(html);
					});
				}
			}
		});
	}
	</script>
</head>
<body>	

<div class="container">		
	<div class="search_tit"><h2>业务功能编码查询</h2></div>
	<form action="bfQuery.action" method="post" target="queryResult">
		
		<table class="global_table" cellpadding="0" cellspacing="0">

			<tr>
				<th width="20%">所属子系统：</th>
				<td width="30%">
					<dict:select dictTypeId="bfSys" name="sysCode" styleClass="width180" nullOption="true"></dict:select>
				</td>
				<th width="20%">类别：</th>
				<td width="30%">
				<select id="type1" onchange="loadChMenus()" style="width:120px">
					<option value=""></option>
					<c:forEach items="${menus1}" var="m1">
						<option value="${m1.id}">${m1.name}</option>
					</c:forEach>
				</select> 
				<select id="type2" name="type" style="width:120px">
					<option value=""></option>
				</select>
				</td>						
			</tr>
			<tr>
				<th>BF_CODE：</th>
				<td><input type="text" name="bfCode" class="inp width180"></td>
				<th>状态：</th>
				<td><select name="status" style="width:180px">
				<option value="TRUE">可用</option>
				<option value="FALSE">不可用</option>
				</td>
			</tr>
			<tr>
				<th>功能名称：</th>
				<td><input type="text" name="bfName" class="inp width180"></td>
				<th></th>
				<td>
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
	<iframe name="queryResult" src="" width="100%" height="520px" scrolling="no" frameborder="0" noresize ></iframe>
</div>
</body>
