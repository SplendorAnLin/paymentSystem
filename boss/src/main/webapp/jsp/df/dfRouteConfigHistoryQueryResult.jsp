<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
<head>
</head>
<body>	
	<div class="pop_tit" ><h2>付款路由配置历史记录</h2></div>
	<s:if test="#attr['routeConfigHistory'].list.size()>0">
		<vlh:root value="routeConfigHistory" url="?" includeParameters="*" configName="defaultlook">
			
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="routeConfigHistory">					
					<s:set name="routeConfig" value="#attr['routeConfigHistory']" />
					<vlh:column title="状态" >
					<dict:write dictId="${routeConfig.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write>
					</vlh:column>
					<vlh:column title="配置时间" >
						<s:date name="#routeConfig.create_date" format="yyyy-MM-dd HH:mm:ss" />
					</vlh:column>
					<vlh:column title="操作人" property="oper">
					</vlh:column>
				</vlh:row>
			</table>
			
			<div class="gpage">
				<vlh:paging showSummary="true" pages="5" attributes="width='500'" />
			</div>				
		</vlh:root>
	</s:if>	

	<s:if test="#attr['routeConfigHistory'].list.size()==0">
		无符合条件的查询结果
	</s:if>
</body>
</html>