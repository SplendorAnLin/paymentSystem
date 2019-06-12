<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<head>		
	<script type="text/javascript">	
	</script>
</head>

<body>	
	<s:if test="#attr['accHistoryList'].list.size()>0">
		<vlh:root value="accHistoryList" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">			
				<vlh:row bean ="accHistoryList">
					<s:set name="accHistoryList" value="#attr['accHistoryList']" />	
					<vlh:column title="业务类型" >
						<dict:write dictId="${accHistoryList.biztype}" dictTypeId="AccountBizType"></dict:write>
					</vlh:column>
					<vlh:column title="资金方向" property="accountforward">
						<dict:write dictId="${accHistoryList.accountforward}" dictTypeId="AccountForward2"></dict:write>
					</vlh:column>
					<vlh:column title="金额" property="amount"/>
					<vlh:column title="流水号" property="flowid"/>
					<vlh:column title="业务发生时间">
						<s:date name="#accHistoryList.transtime" format="yy-MM-dd HH:mm" />
					</vlh:column>
				</vlh:row>
			</table>
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='500'" />
			</div>				
		</vlh:root>
	</s:if>	
	<s:if test="#attr['accHistoryList'].list.size()==0">
		没有符合条件的记录！
	</s:if>
</body>
