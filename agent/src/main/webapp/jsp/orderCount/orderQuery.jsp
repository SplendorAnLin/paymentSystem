<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<head>		
	<script type="text/javascript">	
		$(document).ready(function(){ 		
			$( "#radio" ).buttonset();
			var checkBillStatus = "<s:property value='#parameters.checkBillStatus' />";
			if(checkBillStatus == "NEED_UPLOAD"){
				sub();
			}
		});
		function orderExport(){
			$("#form1").attr("action", "exportOrderCountQuery.action");
			$("#form1").attr("target", "exportResult");
			$("#form1").submit();			
		}
		function orderTotalQuery(){
			var loading_script = "<img src='<%=request.getContextPath()%>/image/loading_7.gif' />";
			$("#totalResult").html(loading_script);
					
			var param = $("#form1").serialize();
			$.post( "<%=request.getContextPath()%>/totalOrderCountQuery.action",
					param,
			  		function (data){
			  			$("#totalResult").html(data);
			  		}
			);			
		}
		function sub(){
			$("#totalResult").html("");
			$("#form1").attr("action", "orderCountQuery.action");
			$("#form1").attr("target", "queryResult");
			$("#form1").submit();
		}
	</script>
</head>

<body>		
	<div class="search_tit"><h2>交易统计</h2></div>
	<form  method="post"  id="form1">
		
		<table class="global_table" cellpadding="0" cellspacing="0">
			 <tr>		
				<th>创建时间：</th>
					<td style="border-style: none" colspan="2">
						<input type="text" id="createTimeStart" name="createTimeStart" value="<s:if test="${param.checkBillStatus!='NEED_UPLOAD' }"><%=DateUtil.today() %></s:if>" 
							class="Wdate width80" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
						 - 	 					
				    	<input type="text" id="createTimeEnd" name="createTimeEnd" value="<s:if test="${param.checkBillStatus!='NEED_UPLOAD' }"><%=DateUtil.today() %></s:if>"
				    	    class="Wdate width80" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
				    </td>	    
				    <td colspan="5" style="padding:5px 0px;border-style: none">
				    	<div id="radio">
							<input type="radio" id="radio1" name="radio"  checked="checked" 
								   onclick="dateChange('today','createTimeStart','createTimeEnd')" /><label for="radio1">当日</label>
							<input type="radio" id="radio2" name="radio" 
								   onclick="dateChange('yesterday','createTimeStart','createTimeEnd')"/><label for="radio2">昨日</label>
							<input type="radio" id="radio3" name="radio" 
								   onclick="dateChange('tweek','createTimeStart','createTimeEnd')"/><label for="radio3">本周</label>
						    <input type="radio" id="radio4" name="radio" 
						    	   onclick="dateChange('pweek','createTimeStart','createTimeEnd')"/><label for="radio4">上周</label>
						    <input type="radio" id="radio5" name="radio" 
						    	   onclick="dateChange('tmonth','createTimeStart','createTimeEnd')"/><label for="radio5">本月</label>
						    <input type="radio" id="radio6" name="radio" 
						    	   onclick="dateChange('pmonth','createTimeStart','createTimeEnd')"/><label for="radio6">上月</label>
						</div>
					</td>
				</tr>	
		</table>
		
		<br>
		<center>
			<input type="button" class="global_btn" value="查 询" onclick="sub()"/>
			<input type="reset" class="global_btn" value="重 置" />
		</center>
	</form>	
	
 	<div class="total_panel">
 		<div class="total">
			<a href="javascript:orderTotalQuery();" style="text-decoration: none">查看合计</a>
		</div>
		<div class="export">			
			<a href="javascript:orderExport();" style="text-decoration: none">导出结果</a>
		</div>	
		<p id="totalResult"></p>							
 	</div>
	
	<iframe name="queryResult" src="" width="100%" height="550px" scrolling="no" frameborder="0" noresize ></iframe>
	<iframe name="exportResult" src="" width="100%" height="0px" scrolling="no" frameborder="0" noresize />
	
</body>
