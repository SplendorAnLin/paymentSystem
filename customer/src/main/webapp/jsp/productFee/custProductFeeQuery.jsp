<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>

<head>		
	<script type="text/javascript">
		function totalQuery(){
			var loading_script = "<img src='<%=request.getContextPath()%>/image/loading_7.gif' />";
			$("#totalResult").html(loading_script);
					
			var param = $("#form1").serialize();
			$.post( "<%=request.getContextPath()%>/custProductFeeTotalQuery.action",
					param,
			  		function (data){
			  			$("#totalResult").html(data);
			  		}
			);	
		}
	</script>
</head>

<body>		
	<div class="search_tit"><h2>商户产品费用查询</h2></div>
	<form action="custProductFeeQuery.action" method="post" target="queryResult" id="form1">
		<input type="hidden" name="customerNo" value="<s:property value="#session['auth'].customerno" />"/>
		<table class="global_table" cellpadding="0" cellspacing="0">	
			<tr>
				<th width="10%">产品编号：</th>
				<td width="20%">
					<input type="text" name="productNo" class="inp width150"/>	
				</td>
				<th width="10%">产品名称：</th>
				<td width="20%">
					<input type="text" name="productName" class="inp width150"/>	
				</td>
				<th width="10%">计费来源：</th>
				<td width="20%">
					<dict:select id="billingSource" dictTypeId="BillingSource" name="billingSource" nullOption="true" value="N" styleClass="width150" />
				</td>
			</tr>
			<tr>
				<th>产品费用范围：</th>
				<td>
					<input type="text" name="prodFeeBegin" class="inp width60"/>~
					<input type="text" name="prodFeeEnd" class="inp width60"/>
				</td>
				<th>业务请求编号：</th>
				<td>
					<input type="text" name="requestId" class="inp width150"/>
				</td>
				<th>交易时间：</th>
				<td>
					<input id="transTimeBegin" type="text" name="transTimeBegin" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate width80" />~
					<input id="transTimeEnd" type="text" name="transTimeEnd" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate width80" />
				</td>
			</tr>
		</table>
		<br>
		<center>
			<input type="submit" id="btn" class="global_btn" value="查 询" />
			<input type="reset" class="global_btn" value="重 置" />
		</center>
	</form>
	<div style="margin:10px 0px 0px 0px;padding:4px 15px; border-top:1px solid #ddd; background:#eee;height:30px;" >
 		<div class="total">
			<a href="javascript:totalQuery();" style="text-decoration: none">查看合计</a>
			<p id="totalResult" style="margin: 0px;float: left;width: 100%;"></p>
		</div>
 	</div>
	<iframe name="queryResult" src="" width="100%" height="520px" scrolling="no" frameborder="0" noresize ></iframe>
</body>