<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
	<script type="text/javascript">
		/**批次明细**/
		function doDetail(code){
			var url="dfBatchDetailQuery.action?code="+code;
			window.location.href=url;
		}
	</script>
</head>
<body>	
	<s:if test="#attr['dpayBatch'].list.size()>0">
		<vlh:root value="dpayBatch" url="?" includeParameters="*" configName="defaultlook">
			<div style="max-height: 360px;overflow: auto;">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="dpayBatch">					
					<s:set name="dpayBatch" value="#attr['dpayBatch']" />
																						
					<vlh:column title="代付批次号" property="batch_no" attributes="width='10%'"/>
					<vlh:column title="流水号" property="flow_no" attributes="width='10%'"/>	
					<vlh:column title="总笔数" property="total_count"	 attributes="width='10%'" />
					<vlh:column title="总金额" property="total_amount" attributes="width='10%'" />
					<vlh:column title="总付款人<br/>手续费" property="total_payer_fee" attributes="width='10%'" />
					<vlh:column title="总收款人<br/>手续费" property="total_rev_fee" attributes="width='10%'" />
					<%-- <vlh:column title="状态" attributes="width='10%'">
						<s:if test="${dpayBatch.status=='WAIT' }">
							待处理
						</s:if>
						<s:if test="${dpayBatch.status=='HANDLED' }">
							已处理
						</s:if>
						<s:if test="${dpayBatch.status=='FAILED' }">
							失败
						</s:if>
					</vlh:column> --%>
					<vlh:column title="时间" property="create_date" attributes="width='10%'" format="yyyy-MM-dd"/>				
					<vlh:column title="操作" attributes="width='10%'">
						<a href="javascript:void 0;" onclick="doDetail('${dpayBatch.id }');">明细</a>
					</vlh:column>			
				</vlh:row>
			</table>
			</div>
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='400'"/>
			</div>				
		</vlh:root>
	</s:if>	
	<s:else>
		无符合条件的查询结果
	</s:else>
</body>
</html>