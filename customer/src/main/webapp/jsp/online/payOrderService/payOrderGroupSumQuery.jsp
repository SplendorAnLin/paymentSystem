<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/header.jsp"%>
<%@ include file="../include/commons-include.jsp"%>
<head>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/date.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-timepicker-addon.js"></script>
	<script type="text/javascript">
		function mySubmit(){
			var flag = true;
			var groupType = $("select[name='groupType']").find("option:selected").val();
			if(groupType == ''){
				flag = false;
				showMsg("分组类型不能为空!");
			}
			if(flag){
				$("#operate").val("query");
				$("#form").submit();
			}
		}
		
		function payOrderGroupSumExport(){
			$("#operate").val("export");
			$("#form").attr("target", "exportResult");
			$("#form").submit();
			$("#operate").val("query");
			$("#form").attr("target", "queryResult");
		}
		
		function showMsg(msg){
			$( "#dialog-message-info").html(msg);
			$( "#dialog-message" ).dialog({
				resizable: false,
				height:260,
				modal: true,
				buttons: {"确认": function() { $( this ).dialog( "close" );}}
			});
		}
		
	</script>
</head>
<body>		
	<div class="search_tit"><h2>在线支付交易合计</h2></div>
	<form:form action="${pageContext.request.contextPath}/payOrder/payOrderGroupSum.htm" modelAttribute="payOrderGroupSumBean" id="form" target="queryResult">
		<input type="hidden" value="" id="operate" name="operate"/>
		<table class="global_table" cellpadding="0" cellspacing="0">
			<tr>
				<th width="10%">分组类型</th>
				<td width="15%">
					<select name="groupType">
						<option value=""></option>
						<option value="customerNo">商户编号</option>
						<option value="orderTime">下单日期</option>
					</select>
				</td>
				<th width="10%">下单时间</th>
				<td width="15%">
					<input type="text" name="orderTimeStart" id="tradeOrderStartTime" class="Wdate width80" />
					 - 
					<input type="text" name="orderTimeEnd" id="tradeOrderEndTime" class="Wdate width80"/>
				</td>
				<th width="10%">支付方式</th>
				<td width="15%">
					<dict:select dictTypeCode="InterfaceType" nullOption="true" name="interfaceType" id="interfaceType"></dict:select>
				</td>
			</tr>		
		</table>
		<br>
		<center>
			<input type="button" class="global_btn" value="查 询" onclick="mySubmit();"/>
			<input type="button" class="global_btn" value="导出" onclick="payOrderGroupSumExport();"/>
			<input type="reset" class="global_btn" value="重 置" />
		</center>
		<br>
	</form:form>
	<iframe name="queryResult" src="" width="100%" height="520px" scrolling="no" frameborder="0" noresize ></iframe>
	<iframe name="exportResult" src="" width="100%" height="520px" scrolling="no" frameborder="0" noresize ></iframe>
</body>
