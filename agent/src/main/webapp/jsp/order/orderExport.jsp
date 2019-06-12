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
			<td colspan="9" style="height:40px;border:0px;font-size:14px;font-weight:bold;text-align:center">订单明细</td>
		</tr>
		<s:if test="#attr['exportOrderInfo'].list.size()>0">		
			<vlh:root value="exportOrderInfo" url="?" includeParameters="*" configName="defaultLook">			
				<vlh:row bean="exportOrderInfo">
					<s:set name="order" value="#attr['exportOrderInfo']" />
					<vlh:column title="交易类型" property="trans_type"	 attributes="width='8%'" >
						<dict:write dictTypeId="TransType" dictId="${order.trans_type}"></dict:write>
					</vlh:column>					
					<vlh:column title="终端号" property="pos_cati"	 attributes="width='10%'" />
					<vlh:column title="卡号" attributes="width='12%'" >
						<boss:pan pan="${order.pan}" />						 										
					</vlh:column>	
					<vlh:column title="卡类型" attributes="width='5%'" >
						<dict:write dictTypeId="CardType" dictId="${order.card_type}"></dict:write>
					</vlh:column>
					<vlh:column title="参考号" property="external_id" attributes="width='10%'" />
					<vlh:column title="金额" property="amount" attributes="width='6%'" />
					<vlh:column title="手续费" property="customer_fee" attributes="width='6%'" />
					<vlh:column title="交易状态" property="status" attributes="width='8%'">
						<s:if test="${order.status == 'SETTLED'}">成功</s:if>
						<s:if test="${order.status == 'SUCCESS'}">成功</s:if>
						<s:if test="${order.status == 'INIT'}"><font color="red">未付</font></s:if>
						<s:if test="${order.status == 'REPEAL'}"><font color="green">撤销</font></s:if>
						<s:if test="${order.status == 'REVERSALED'}"><font color="red">冲正</font></s:if>
					</vlh:column>				
					<vlh:column title="交易时间" attributes="width='10%'">
						<s:date name="#order.create_time" format="yy-MM-dd HH:mm" />
					</vlh:column>							
				</vlh:row>							
			</vlh:root>		
		</s:if>	
	<s:if test="#attr['exportOrderInfo'].list.size()==0">		
		<tr>
			<td colspan="13" style="height:40px;border:0px;font-size:14px;font-weight:bold;text-align:center">no record!please select other condition!</td>
		</tr>
	</s:if>
	</table>
</body>
</html>
