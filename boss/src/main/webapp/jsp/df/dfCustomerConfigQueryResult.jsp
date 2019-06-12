<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript">
		function toHistory(ownerId){
			var url = "dpayServiceConfigHistory.action?ownerId="+ownerId;
			window.location.href = url;
		}
	</script>
	<style>
		td{
			mso-number-format:"\@";
		}
	</style>
</head>
<body>
	<c:if test="${dpayServiceConfig.list.size()>0 }">
		<vlh:root value="dpayServiceConfig" url="?" includeParameters="*" configName="defaultlook">
			<div style="max-height: 450px;overflow: auto;">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="dpayServiceConfig">
					<vlh:column title="持有者编号" property="owner_id" attributes="width='10%'"/>
					<vlh:column title="持有者角色" property="owner_role" attributes="width='10%'"/>
					<vlh:column title="每日笔数限制" property="limit_count_day" attributes="width='10%'"/>
					<vlh:column title="每日金额限制" property="limit_amount_day" attributes="width='10%'" format="#,#00" />
					<vlh:column title="每笔交易限额" property="limit_amount_single" attributes="width='10%'" format="#,#00"/>
					<vlh:column title="风险预存期" property="freeze_day_for_risk" attributes="width='10%'"/>
					<vlh:column title="账户支持类型" attributes="width='10%'">
						<c:if test="${dpayServiceConfig.target_account_support_type=='1' }">
							对私
						</c:if>
						<c:if test="${dpayServiceConfig.target_account_support_type=='2' }">
							对公
						</c:if>
						<c:if test="${dpayServiceConfig.target_account_support_type=='3' }">
							对公/对私
						</c:if>
					</vlh:column>
					<vlh:column title="是否有效" property="valid" attributes="width='10%'">
						<c:if test="${dpayServiceConfig.valid=='1' }">
							有效
						</c:if>
						<c:if test="${dpayServiceConfig.valid=='0' }">
							无效
						</c:if>
					</vlh:column>
					<vlh:column title="创建时间" property="create_date" format="yyyy-MM-dd" attributes="width='10%'"/>
					<vlh:column title="操作" attributes="width='10%'">
						<a href="toDfUpdateSet.action?ownerId=${dpayServiceConfig.owner_id }&ownerRole=${dpayServiceConfig.owner_role }" onclick="">修改</a>|
						<a href="#" onclick="toHistory('${dpayServiceConfig.owner_id }');">历史</a>
					</vlh:column>
				</vlh:row>
			</table>
			</div>
			<div class="gpage">
				<vlh:paging showSummary="true" pages="8" attributes="width='700'" />
			</div>
		</vlh:root>
	</c:if>
	<c:if test="${dpayServiceConfig.list.size()==0 }">
		无符合条件的查询结果
	</c:if>
</body>
</html>