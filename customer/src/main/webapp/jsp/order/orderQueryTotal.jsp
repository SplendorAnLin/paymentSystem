<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<body>	
<s:if test="#attr['totalOrderInfo'].list.size()>0">	
	<vlh:root value="totalOrderInfo" url="?" includeParameters="*" configName="defaultLook">
		<vlh:row bean="totalOrderInfo">
			<s:set name="order" value="#attr['totalOrderInfo']" />			
			<font color='#00f'>交易总金额：</font>
			<s:if test="#order.trxamount == null"><font color='#00f'> 0.0</s:if>
			<s:else><vlh:column property="trxamount"></vlh:column></s:else>
			&nbsp;&nbsp;
			<font color='#00f'>交易总笔数：</font>  <vlh:column property="ordercount"></vlh:column>
		</vlh:row>
	</vlh:root>
</s:if>
</body>
</html>
