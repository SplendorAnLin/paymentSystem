<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html>
<head>	
	<style>
		td{border:0px solid #f00}
	</style>
	<script type="text/javascript">		
		function queryDetail(id){
			var url = "${pageContext.request.contextPath}/toCustomerUpdate.action?customer.id=" + id;
			window.location.href = url;
		}
	</script>
</head>
<body>
<s:if test="#attr['customerQuery'].list.size()>0">
		<vlh:root value="customerQuery" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="customerQuery">
					<s:set name="entity" value="#attr['customerQuery']" />		
					<vlh:column title="商户名称" attributes="width='10%'">
						${entity.name}
					</vlh:column>
					<vlh:column title="商户编号" property="customer_no" attributes="width='15%'" />
					<vlh:column title="联系人" property="link_man" attributes="width='10%'" />
					<vlh:column title="状态" attributes="width='8%'" >
						<c:if test="${entity.status eq 'TRUE'}">
							启用
						</c:if>
						<c:if test="${entity.status eq 'FALSE'}">
							停用
						</c:if>
					</vlh:column>
					<vlh:column title="操作人" property="operator" attributes="width='15%'" />
					<vlh:column title="创建时间" attributes="width='16%'">
						<s:date name="#entity.create_time" format="yyyy-MM-dd" />
					</vlh:column>
					<vlh:column title="操作" attributes="width='10%'">
						<a href="javascript:queryDetail('${entity.id}');">修改信息</a>
					</vlh:column>
				</vlh:row>
			</table>
			
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='500'" />
			</div>				
		</vlh:root>
	</s:if>	

	<s:if test="#attr['customerQuery'].list.size()==0">
		无符合条件的查询结果
	</s:if>
</body>
</html>
