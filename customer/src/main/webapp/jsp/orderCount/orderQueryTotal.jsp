<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<body>	
<s:if test="#attr['totalOrderCountInfo'].list.size()>0">	
	<vlh:root value="totalOrderCountInfo" url="?" includeParameters="*" configName="defaultLook">
		<vlh:row bean="totalOrderCountInfo">
			<s:set name="order" value="#attr['totalOrderCountInfo']" />			
			<font color='#00f'>交易总金额：</font>
			<s:if test="#order.amount == null"><font color='#00f'> 0.0</s:if>
			<s:else><vlh:column property="amount"></vlh:column></s:else>
			&nbsp;&nbsp;
			<font color='#00f'>交易总笔数：</font>  
			<vlh:column property="ordercount"></vlh:column>
			&nbsp;&nbsp;
			<font color='#00f'>交易手续费：</font>
			<s:if test="#order.customer_fee == null"><font color='#00f'> 0.0</s:if>
			<s:else><vlh:column property="customer_fee"></vlh:column></s:else>
			
		</vlh:row>
	</vlh:root>
</s:if>
</body>
</html>
