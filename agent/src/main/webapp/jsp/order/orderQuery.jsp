<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<head>		
	<script type="text/javascript">	
		$(document).ready(function(){ 		
			$( "#radio" ).buttonset();
			var checkBillStatus = "<s:property value='#parameters.checkBillStatus' />";
			if(checkBillStatus == "NEED_UPLOAD"){
// 				sub();
			}
			
// 			//设置默认值
// 			var endTimeObject = document.getElementById("createTimeEnd");
// 			var endNow = new Date();
// 			var now = new Date();
// 			var endMoth = endNow.getMonth();
// 			var endYear = endNow.getFullYear();
// 			var endDay =  endNow.getDate();
// 			if(endMoth == 0){
// 				endYear = endYear - 1;
// 				endMoth = 12;
// 			}
// 			endMoth = endMoth - 1;
// 			now.setMonth(endMoth);
// 			now.setFullYear(endYear);
// 			var end = formatDate(endNow);
// 			var start = formatDate(now);
// 			$("#createTimeStart").val(start);
			
		});
		
// 		function formatDate(now){
// 			var month = now.getMonth() + 1;
// 			var day = now.getDate();
// 			var year = now.getFullYear();
// 			var wday = now.getDay();
// 			return year+"-"+month+"-"+day ; 
// 		}
		
		function orderExport(){
			$("#form1").attr("action", "exportOrderQuery.action");
			$("#form1").attr("target", "exportResult");
			
			var isTrue = true;
			$("input[title='createTime']").each(function(){
				if($(this).val() == ''){
					dialogMessage("请选择所要查询的时间范围");
					isTrue = false;
					return false;
				}
			});
			if(isTrue){
				var end = new Date($("#createTimeEnd").val().replace(/-/g,"/"));
				var start = new Date($("#createTimeStart").val().replace(/-/g,"/"));
				var times = end.getTime() - start.getTime();
				var days = parseInt(times / (1000 * 60 * 60 * 24));
				if(days > 30){
					dialogMessage("您选择的时间不能大于30天");
					return false;
				}else{
					$("#form1").submit();
				}
			}
		}
		function orderTotalQuery(){
			var loading_script = "<img src='<%=request.getContextPath()%>/image/loading_7.gif' />";
			$("#totalResult").html(loading_script);
					
			var param = $("#form1").serialize();
			$.post( "<%=request.getContextPath()%>/totalOrderQuery.action",
					param,
			  		function (data){
			  			$("#totalResult").html(data);
			  		}
			);			
		}
		function sub(){
			$("#totalResult").html("");
			$("#form1").attr("action", "orderQuery.action");
			$("#form1").attr("target", "queryResult");
			
			var isTrue = true;
			$("input[title='createTime']").each(function(){
				if($(this).val() == ''){
					dialogMessage("请选择所要查询的时间范围");
					isTrue = false;
					return false;
				}
			});
			if(isTrue){
				var end = new Date($("#createTimeEnd").val().replace(/-/g,"/"));
				var start = new Date($("#createTimeStart").val().replace(/-/g,"/"));
				var times = end.getTime() - start.getTime();
				var days = parseInt(times / (1000 * 60 * 60 * 24));
				if(days > 30){
					dialogMessage("您选择的时间不能大于30天");
					return false;
				}else{
					$("#form1").submit();
				}
			}
		}
	</script>
</head>

<body>		
	<div class="search_tit"><h2>订单查询</h2></div>
	<form  method="post"  id="form1">
		
		<table class="global_table" cellpadding="0" cellspacing="0">
				 <tr>		
					<th>创建时间：</th>
						<td style="border-style: none" colspan="2">
							<input type="text" id="createTimeStart" title='createTime' name="createTimeStart" value="<s:if test="${param.checkBillStatus!='NEED_UPLOAD' }"><%=DateUtil.today() %></s:if>" 
								class="Wdate width80" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
							 - 	 					
					    	<input type="text" id="createTimeEnd" title='createTime' name="createTimeEnd" value="<s:if test="${param.checkBillStatus!='NEED_UPLOAD' }"><%=DateUtil.today() %></s:if>"
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
			<tr>
				<th width="13%">终端号：</th>
				<td width="20%">
					<input class="inp" type="text" name="posCati" />
				</td>						
				<th width="13%">卡号(末四位)：</th>
				<td width="20%">
					<input class="inp" type="text" name="panEnd" />
				</td>
				<th width="13%">卡类型：</th>
				<td width="20%">
					<dict:select dictTypeId="CardType" name="cardType" nullOption="true" nullLabel="" styleClass="width150" />
				</td>							
			</tr>	
			<tr >
				<th width="10%">授权号：</th>
				<td width="20%">
					<input class="inp" type="text" name="authorizationCode" />
				</td>
				<th>交易类型：</th>
				<td>
					<select name="transType" style="width: 150px">
						<option value=""></option>
						<option value="PURCHASE">消费</option>
						<option value="PRE_AUTH">预授权</option>
						<option value="SPECIFY_QUANCUN">指定圈存</option>
						<option value="NOT_SPECIFY_QUANCUN">非指定圈存</option>
						<option value="CASH_RECHARGE_QUNCUN">现金圈存</option>
						<option value="OFFLINE_PURCHASE">脱机消费</option>
					</select>
				</td>
				<th>参考号：</th>
				<td>
					<input class="inp" type="text" name="externalId" />
				</td>						
			</tr>				
			<tr>
				<th width="10%">交易状态：</th>
				<td width="20%">
					<select name="status" style="width:150px">
						<option value=""></option>
						<option value="SUCCESS','SETTLED" <s:if test="${param.checkBillStatus=='NEED_UPLOAD' }">selected="selected"</s:if>>成功</option>
						<option value="AUTHORIZED">已授权</option>
						<option value="INIT">未付</option>
						<option value="REPEAL">撤销</option>
					</select>
				</td>
				<th>交易金额：</th>
				<td>
					<input id="amountStart" type="text" name="amountStart" class="width60"/> - 
					<input id="amountEnd" type="text" name="amountEnd" class="width60" />
				</td>
				<th>
					调单状态：
				</th>
				<td>
					<select name="check_bill_record_status" style="width:150px">
						<option value=""></option>
						<option value="='NEED_UPLOAD'" <s:if test="${param.checkBillStatus=='NEED_UPLOAD' }">selected="selected"</s:if>>待调单</option>
						<option value="!='NEED_UPLOAD'">已调单</option>
						<option value="is null">不需要调单</option>
						<option value="='SET_SETTLED'">可结算</option>
					</select>
				</td>
			</tr>	
		</table>
		
		<br>
		<center>
			<input type="button" class="global_btn" value="查 询" onclick="sub();"/>
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
