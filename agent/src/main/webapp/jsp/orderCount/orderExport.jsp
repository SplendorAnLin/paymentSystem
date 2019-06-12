<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<%		
	response.setHeader( "Pragma ", "public"); 
	response.setHeader( "Cache-Control ", "must-revalidate, post-check=0, pre-check=0 "); 
	response.addHeader( "Cache-Control ", "public "); 
	response.addHeader("Content-Disposition", "attachment; filename=pos_export.xls");
	response.setContentType("application/vnd.ms-excel.numberformat:@;charset=UTF-8"); 
%>

<html>
<head>
	<style>
		table{border:thin solid #333;border-collapse: collapse;border-spacing:0px;}
		th{background-color:#ccc;border:thin solid #333;}
		td{border:thin solid #000;}
	</style>
</head>
<body>	
	<table width="1400" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="5" style="height:40px;border:0px;font-size:14px;font-weight:bold;text-align:center">交易统计</td>
		</tr>
		<s:if test="#attr['exportOrderCountInfo'].list.size()>0">		
			<s:set name="ordersize" value="#attr['exportOrderCountInfo'].list.size()" />
			<s:set name="amount1" value="0" />
			<s:set name="ordercount1" value="0" />
			<s:set name="customer_fee1" value="0" />
			<vlh:root value="exportOrderCountInfo" url="?" includeParameters="*" configName="defaultLook">
				<vlh:row bean="exportOrderCountInfo">
					<s:set name="order" value="#attr['exportOrderCountInfo']" />
					<vlh:column title="POS终端号" property="pos_cati" attributes="width='15%'"/>
					<vlh:column title="网点名称" property="shopname"  attributes="width='15%'" />
					<vlh:column title="交易总金额" property="amount" attributes="width='15%'"/>
					<vlh:column title="交易总笔数" property="ordercount" attributes="width='15%'" />
					<vlh:column title="手续费" property="customer_fee" attributes="width='15%'" />
					<s:set name="amount1" value="#amount1+#order.amount"></s:set>
					<s:set name="ordercount1" value="#ordercount1+#order.ordercount"></s:set>
					<s:set name="customer_fee1" value="#customer_fee1+#order.customer_fee"></s:set>
				</vlh:row>
			</vlh:root>
			<td></td>
			<td>&nbsp;合计：</td>
			<td align="left">&nbsp;${amount1 }</td>
			<td align="left">&nbsp;${ordercount1 }</td>
			<td align="left">&nbsp;${customer_fee1 }</td>
		</s:if>	
	<s:if test="#attr['exportOrderCountInfo'].list.size()==0">		
		<tr>
			<td colspan="13" style="height:40px;border:0px;font-size:14px;font-weight:bold;text-align:center">no record!please select other condition!</td>
		</tr>
	</s:if>
	</table>
</body>
</html>
