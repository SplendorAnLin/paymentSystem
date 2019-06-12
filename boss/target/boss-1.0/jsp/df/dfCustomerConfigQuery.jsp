<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/datePicker.js"></script>
	<script type="text/javascript">
	
		function doAdd(){
			var url="toDfUpdateAdd.action";
			$("#queryResult").attr("src",url);
		}
		
	</script>	
</head>
<body onload="query();">	
	<div class="search_tit"><h2>代付配置</h2></div>
	<form id="dfSetQuery" action="dfSetQuery.action" method="post" target="queryResult">
		<input type="hidden" name="valid" value="1">
		<table class="global_table" cellpadding="0" cellspacing="0">
			<tr>
				<th width="10%">创建时间：</th>
				<td width="20%">
					<input type="text" value="" id="createDate1" name="createDate1"  value="<%=DateUtil.today()%>"
						class="Wdate width80"/>
					 - 	 					
			    	<input type="text" value="" id="createDate2" name="createDate2"  value="<%=DateUtil.today()%>"
			    	    class="Wdate width80"/>
				</td>	
				<th width="10%">商户编号：</th>
				<td width="20%">
					<input class="inp" type="text" name="ownerId"/>
				</td>	
				<th width="10%">打款账户支持类型：</th>
				<td width="20%">
					<select style="width: 100px" name="accountSupport">
						<option value=""></option>
						<option value="2">对公</option>
						<option value="1">对私</option>
						<option value="3">对公/对私</option>
					</select>
				</td>	
			</tr>
		</table>
		
		<br>
		<center>
			<input type="submit" class="global_btn"  value="查 询"/>
			<input type="reset" class="global_btn" value="重 置" />
		</center>
		<br/>
	</form>	
	<div class="total_panel">
		<div class="total">			
			<a href="javascript:void 0;" onclick="doAdd();" style="text-decoration: none">新增</a>
		</div>	
		<p id="totalResult"></p>							
 	</div>
	<iframe name="queryResult" id="queryResult" src="" width="100%" height="520px" scrolling="no" frameborder="0" noresize ></iframe>
</body>
</html>