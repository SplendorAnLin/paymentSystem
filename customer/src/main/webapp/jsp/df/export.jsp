<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%		
	response.setHeader( "Pragma ", "public"); 
	response.setHeader( "Cache-Control ", "must-revalidate, post-check=0, pre-check=0 "); 
	response.addHeader( "Cache-Control ", "public "); 
	response.addHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode("代付单", "utf8")+"-"+ com.pay.common.util.DateUtil.formatDate(new Date(), "yyyy-MM-dd")+".xls");
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

	<s:if test="#attr['dpayRequestExport'].list.size()>0">
		<vlh:root value="dpayRequestExport" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="dpayRequestExport">					
					<s:set name="dpayRequestExport" value="#attr['dpayRequestExport']" />
					<vlh:column title="订单号" property="flow_no"	 attributes="width='220px'" />
					<vlh:column title="商户订单号"  attributes="width='150px'" >
						<div style= "vnd.ms-excel.numberformat:@">${dpayRequestExport.request_no}</div>
					</vlh:column>
					<vlh:column title="收款人姓名" property="account_name" attributes="width='300px'" />
					<vlh:column title="收款账号" attributes="width='200px'" >
						<div style= "vnd.ms-excel.numberformat:@">${dpayRequestExport.account_no}</div>
					</vlh:column>		
					<vlh:column title="金额" attributes="width='100px'">
						<div style= "vnd.ms-excel.numberformat:@">${dpayRequestExport.amount}</div>
					</vlh:column>
					<vlh:column title="手续费" attributes="width='40px'">
						<div style= "vnd.ms-excel.numberformat:@">${dpayRequestExport.fee}</div>
					</vlh:column>
					<vlh:column title="开户行" property="bank_name" attributes="width='280px'" />
					<vlh:column title="账户类型" attributes="width='80px'">
						<s:if test="${dpayRequestExport.account_type=='INDIVIDUAL' }">对私</s:if>
						<s:if test="${dpayRequestExport.account_type=='OPEN' }">对公</s:if>
					</vlh:column>
					<vlh:column title="状态" attributes="width='80px'">
						<s:if test="${dpayRequestExport.audit_status=='WAIT' }">
							待审核
						</s:if>
						<s:if test="${dpayRequestExport.audit_status=='REFUSE' }">
							审核拒绝
						</s:if>
						<s:if test="${dpayRequest.audit_status=='REMIT_REFUSE' }">
							付款拒绝
						</s:if>
						<s:if test="${(dpayRequestExport.audit_status=='PASS' || dpayRequestExport.audit_status=='REMIT_WAIT') && (dpayRequestExport.status=='WAIT' || dpayRequestExport.status=='HANDLEDING')}">
							处理中
						</s:if>
						<s:if test="${dpayRequestExport.status=='SUCCESS'}">
							成功
						</s:if>
						<s:if test="${dpayRequestExport.status=='FAILED'}">
							失败
						</s:if>
					</vlh:column>
					<%-- <vlh:column title="卡类型" attributes="width='80px'">
						<s:if test="${dpayRequestExport.card_type=='DEBIT' }">借记卡</s:if>
						<s:if test="${dpayRequestExport.card_type=='CREDIT' }">贷记卡</s:if>
					</vlh:column>
					<vlh:column title="认证类型" attributes="width='80px'">
						<s:if test="${dpayRequestExport.cert_type=='NAME' }">账户名称</s:if>
						<s:if test="${dpayRequestExport.cert_type=='ID' }">身份证</s:if>
						<s:if test="${dpayRequestExport.cert_type=='PROTOCOL' }">协议</s:if>
					</vlh:column>
					<vlh:column title="认证号码" attributes="width='250px'">
						<div style= "vnd.ms-excel.numberformat:@">${dpayRequestExport.cert_no}</div>
					</vlh:column> --%>
					<vlh:column title="完成描述" attributes="width='100px'" property="error_msg"/>
					<vlh:column title="打款原因" attributes="width='200px'" property="description"/>
					<vlh:column title="创建时间" property="create_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
					<vlh:column title="审核时间" property="audit_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
					<vlh:column title="完成时间" property="finish_date" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
				</vlh:row>
			</table>
		</vlh:root>
	</s:if>
	<s:else>
		无查询结果
	</s:else>
</body>
</html>