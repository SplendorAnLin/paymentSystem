<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%		
	response.setHeader( "Pragma ", "public"); 
	response.setHeader( "Cache-Control ", "must-revalidate, post-check=0, pre-check=0 "); 
	response.addHeader( "Cache-Control ", "public "); 
	response.addHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode("分润信息", "utf8")+"-"+ com.pay.common.util.DateUtil.formatDate(new Date(), "yyyy-MM-dd")+".xls");
	response.setContentType("application/vnd.ms-excel.numberformat:@;charset=UTF-8"); 
%>

<html>
<head>
<style>
table {
	border: thin solid #333;
	border-collapse: collapse;
	border-spacing: 0px;
}

th {
	background-color: #ccc;
	border: thin solid #333;
}

td {
	border: thin solid #000;
}
</style>
</head>
</head>
<body>
	<s:if test="#attr['spExport'].list.size()>0">
		<vlh:root value="spExport" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="1" cellspacing="1" align="center"
				class="global_tableresult" style="width: 100%">
				<vlh:row bean="spExport">
					<s:set name="spExport" value="#attr['spExport']" />
					<vlh:column title="订单编号" property="order_code"	 attributes="width='150px'" />
					<vlh:column title="商户编号" property="customer_no" attributes="width='80px'" />
					<vlh:column title="产品类型"  attributes="width='80px'">
						<c:if test="${spExport.product_type eq 'B2C' }">个人网页</c:if>
						<c:if test="${spExport.product_type eq 'B2B' }">企业网银</c:if>
						<c:if test="${spExport.product_type eq 'AUTHPAY' }">认证支付</c:if>
						<c:if test="${spExport.product_type eq 'REMIT' }">代付</c:if>
						<c:if test="${spExport.product_type eq 'WXJSAPI' }">微信公众号</c:if>
						<c:if test="${spExport.product_type eq 'WXNATIVE' }">微信扫码</c:if>
						<c:if test="${spExport.product_type eq 'ALIPAY' }">支付宝</c:if>
						<c:if test="${spExport.product_type eq 'HOLIDAY_REMIT' }"> 假日付</c:if>
						<c:if test="${spExport.product_type eq 'RECEIVE' }">代收</c:if>
					</vlh:column>
					<vlh:column title="订单金额" property="amount" attributes="width='150px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.amount}</div>
					</vlh:column>
					<vlh:column  title="订单手续费" property="fee" attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.fee}</div>
					</vlh:column>
					<vlh:column title="分润时间" property="create_time" attributes="width='150px'"
						format="yyyy-MM-dd HH:mm:ss" />
					<vlh:column  title="订单完成时间" property="order_time" attributes="width='150px'"
						format="yyyy-MM-dd HH:mm:ss" />
					<vlh:column title="商户费率"	 property="customer_fee" attributes="width='100px'" >
					<div style= "vnd.ms-excel.numberformat:@">${spExport.customer_fee}</div>
					</vlh:column>
					<vlh:column title="服务商费率" property="agent_fee" attributes="width='100px'" >
					<div style= "vnd.ms-excel.numberformat:@">${spExport.agent_fee}</div>
					</vlh:column>
					<vlh:column title="分润类型" attributes="width='100px'">
						<s:if test="${spExport.profit_type eq 'FIXED_PROFIT' }">固定</s:if>
						<s:if test="${spExport.profit_type eq 'RATIO_PROFIT' }">比例</s:if>
					</vlh:column>
					<vlh:column title="分润比例"	 attributes="width='80px'" property="profit_ratio" >
						<div style= "vnd.ms-excel.numberformat:@">${spExport.profit_ratio}</div>
					</vlh:column>
					<vlh:column property="agent_profit" title="服务商利润" attributes="width='80px'" >
					<div style= "vnd.ms-excel.numberformat:@">${spExport.agent_profit}</div>
					</vlh:column>
				</vlh:row>
			</table>
		</vlh:root>
	</s:if>
	<s:else>
		无查询结果
	</s:else>

</body>
</html>