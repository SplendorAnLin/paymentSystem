<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%		
	response.setHeader( "Pragma ", "public"); 
	response.setHeader( "Cache-Control ", "must-revalidate, post-check=0, pre-check=0 "); 
	response.addHeader( "Cache-Control ", "public "); 
	response.addHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode("经营分析", "utf8")+"-"+ com.pay.common.util.DateUtil.formatDate(new Date(), "yyyy-MM-dd")+".xls");
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
</head>
<body >	

	<s:if test="#attr['spExport'].list.size()>0">
		<vlh:root value="spExport" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="spExport">					
					<s:set name="spExport" value="#attr['spExport']" />
					<vlh:column title="订单编号" property="order_code"	 attributes="width='150px'" />
					<vlh:column title="接口编号" property="interface_code" attributes="width='150px'" />
					<vlh:column title="订单来源" property="source"	 attributes="width='150px'" />
					<vlh:column title="产品类型" attributes="width='80px'">
						<dict:write dictId="${spExport.product_type }" dictTypeId="BF_SHARE_PAYTYPE"></dict:write>
					</vlh:column>
					<vlh:column title="商户编号" property="customer_no" attributes="width='150px'" />
					<vlh:column title="订单金额" attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.amount}</div>
					</vlh:column>
					<vlh:column title="商户费率"	 attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.customer_fee}</div>
					</vlh:column>
					<vlh:column title="订单手续费"	 attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.fee}</div>
					</vlh:column>
					<vlh:column title="上级服务商编号" property="agent_no"	 attributes="width='150px'" />
					<vlh:column title="上级服务商费率" property="agent_fee" attributes="width='100px'" >
					<div style= "vnd.ms-excel.numberformat:@">${spExport.agent_fee}</div></vlh:column>
					<vlh:column title="分润类型" attributes="width='80px'">
						<dict:write dictId="${spExport.profit_type }" dictTypeId="CUSTOMER_SUB_TYPE"></dict:write>
					</vlh:column>
					<vlh:column title="上级分润比例"	 attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.profit_ratio}</div>
					</vlh:column>
					<vlh:column title="上级服务商利润"	 attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.agent_profit}</div>
					</vlh:column>
					<vlh:column title="直属服务商编号" property="direct_agent"	 attributes="width='150px'" />
					<vlh:column title="直属服务商费率" property="direct_agent_fee" attributes="width='100px'" >
					<div style= "vnd.ms-excel.numberformat:@">${spExport.direct_agent_fee}</div></vlh:column>
					<vlh:column title="分润类型" attributes="width='80px'">
						<dict:write dictId="${spExport.profit_type }" dictTypeId="CUSTOMER_SUB_TYPE"></dict:write>
					</vlh:column>
					<vlh:column title="直属分润比例"	 attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.direct_profit_ratio}</div>
					</vlh:column>
					<vlh:column title="直属服务商利润"	 attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.direct_agent_profit}</div>
					</vlh:column>
					<vlh:column title="间属服务商编号" property="indirect_agent"	 attributes="width='150px'" />
					<vlh:column title="间属服务商费率" property="indirect_agent_fee" attributes="width='100px'" >
					<div style= "vnd.ms-excel.numberformat:@">${spExport.indirect_agent_fee}</div></vlh:column>
					<vlh:column title="分润类型" attributes="width='80px'">
						<dict:write dictId="${spExport.profit_type }" dictTypeId="CUSTOMER_SUB_TYPE"></dict:write>
					</vlh:column>
					<vlh:column title="间属分润比例"	 attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.indirect_profit_ratio}</div>
					</vlh:column>
					<vlh:column title="间属服务商利润"	 attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.indirect_agent_profit}</div>
					</vlh:column>
					<vlh:column title="通道成本"	 attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.channel_cost}</div>
					</vlh:column>
					<vlh:column title="平台利润"	 attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.platfrom_profit}</div>
					</vlh:column>
					<vlh:column title="订单完成时间" property="order_time" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
					<vlh:column title="分润时间" property="create_time" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
				</vlh:row>
			</table>
		</vlh:root>
	</s:if>
	<s:else>
		无查询结果
	</s:else>
</body>
</html>