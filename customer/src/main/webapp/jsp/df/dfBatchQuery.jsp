<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<head>		
	<script type="text/javascript">	
		
	</script>
</head>

<body>
	<input type="hidden" id="cbs"/>		
	<div class="search_tit"><h2>代付查询</h2></div>
	<form  method="post" action="dfBatchQuery.action" target="queryResult">
		<table class="global_table" cellpadding="0" cellspacing="0">
			<tr>
				<th style="width: 10%">创建时间：</th>
				<td style="width: 20%">
					<input type="text" value="<%=DateUtil.today() %>" name="createDate1"
						class="Wdate width80" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					 - 	 					
			    	<input type="text" value="<%=DateUtil.today() %>" name="createDate2"
			    	    class="Wdate width80" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
			    </td>
				<th style="width: 10%">批次号：</th>
				<td style="width: 20%">
					<input class="inp" type="text" name="batchNo"/>
				</td>
				<th style="width: 10%">流水号：</th>
				<td style="width: 20%">
					<input class="inp" type="text" name="flowNo"/>
				</td>
				<!-- <th style="width: 10%">状态：</th>
				<td style="width: 20%">
					<select style="width: 150px" name="status">
						<option value=""></option>
						<option value="WAIT">待处理</option>
						<option value="HANDLED">已处理</option>
						<option value="FAILED">失败</option>
					</select>
				</td>	 -->						
			</tr>				
		</table>	
		<br>
		<center>
			<input type="submit" class="global_btn" value="查 询"/>
			<input type="reset" class="global_btn" value="重 置" />
		</center>
	</form>	
	
	<iframe name="queryResult" src="" width="100%" height="550px" scrolling="no" frameborder="0" noresize ></iframe>	
</body>
