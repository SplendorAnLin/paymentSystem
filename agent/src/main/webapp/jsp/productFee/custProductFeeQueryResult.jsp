<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<style>
		td{border:0px solid #f00}
	</style>
	<script type="text/javascript">		
		function queryDetail(id){
			var url = "${pageContext.request.contextPath}/custProductFeeDetailQuery.action?productFeeId=" + id;
			window.location.href = url;
		}
	</script>
</head>
<body>
	<br>	
	<s:if test="#attr['custProductFeeQuery'].list.size()>0">
	<%int count = 1; %>
		<vlh:root value="custProductFeeQuery" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="custProductFeeQuery">
					<s:set name="entity" value="#attr['custProductFeeQuery']" />		
					<vlh:column title="产品" attributes="width='10%'">
						<font title="${entity.productno}">${entity.productname}</font>
					</vlh:column>
					<vlh:column title="计费来源" attributes="width='10%'">
						<s:if test="#entity.billingsource == 'TRANSACTION'">
							<font title="${entity.billingsource}">交易</font>
						</s:if>
						<s:elseif test="#entity.billingsource == 'SETTLEMENT'">							
							<font title="${entity.billingsource}">结算</font>
						</s:elseif>
						<s:else>
							${entity.billingsource}
						</s:else>
					</vlh:column>
					<vlh:column title="业务请求编号" property="requestid" attributes="width='10%'" />
					<vlh:column title="商户" attributes="width='10%'" >
						<font title="${entity.customerno}">${entity.customername}</font>
					</vlh:column>
					<vlh:column title="商户编号" property="customerno" attributes="width='10%'" />
					<vlh:column title="计费金额" property="transamount" attributes="width='8%'" />
					<vlh:column title="交易时间" attributes="width='10%'">
						<s:date name="#entity.transtime" format="yy-MM-dd HH:mm" />
					</vlh:column>
					<vlh:column title="产品费用" property="productfee" attributes="width='8%'" />
					<vlh:column title="计费时间" attributes="width='10%'">
						<s:date name="#entity.createtime" format="yy-MM-dd HH:mm" />
					</vlh:column>
					<vlh:column title="操作" attributes="width='10%'">
						<a href="javascript:queryDetail('${entity.id}');">计费明细</a>
					</vlh:column>
				</vlh:row>
			</table>
			
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='500'" />
			</div>				
		</vlh:root>
	</s:if>	

	<s:if test="#attr['custProductFeeQuery'].list.size()==0">
		无符合条件的查询结果
	</s:if>

	<iframe name="queryResult" src="" width="100%" height="520px" scrolling="no" frameborder="0" noresize ></iframe>
</body>
</html>
