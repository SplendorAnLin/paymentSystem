<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%		
	response.setHeader( "Pragma ", "public"); 
	response.setHeader( "Cache-Control ", "must-revalidate, post-check=0, pre-check=0 "); 
	response.addHeader( "Cache-Control ", "public "); 
	response.addHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode("白名单", "utf8")+"-"+ com.pay.common.util.DateUtil.formatDate(new Date(), "yyyy-MM-dd")+".xls");
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
	<s:if test="#attr['nameListRequestExport'].list.size()>0">
		<vlh:root value="nameListRequestExport" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="nameListRequestExport">					
					<s:set name="nameListRequestExport" value="#attr['nameListRequestExport']" />
					<vlh:column title="姓名" property="user_name"	 attributes="width='220px'" />
					<vlh:column title="证件类型"  attributes="width='150px'" >
						<s:if test="${nameListRequestExport.type_of_certificate == 'ID' }">身份证</s:if>
						<s:if test="${nameListRequestExport.type_of_certificate == 'RESIDENCE' }">居住证</s:if>
						<s:if test="${nameListRequestExport.type_of_certificate == 'PASSPOTR' }">护照</s:if>
						<s:if test="${nameListRequestExport.type_of_certificate == 'MILITARY' }">军人证</s:if>
					</vlh:column>
					<vlh:column title="证件号" property="license_number" attributes="width='300px'" >
						<div style= "vnd.ms-excel.numberformat:@">${nameListRequestExport.license_number}</div>
					</vlh:column>
					<vlh:column title="卡类型" attributes="width='200px'" >
						<s:if test="${nameListRequestExport.card_type == 'PASSBOOK' }">存折</s:if>
						<s:if test="${nameListRequestExport.card_type == 'CARD' }">银行卡</s:if>
					</vlh:column>		
					<vlh:column title="账户" attributes="width='100px'">
						<div style= "vnd.ms-excel.numberformat:@">${nameListRequestExport.account}</div>
					</vlh:column>
					<vlh:column title="手机号" attributes="width='40px'">
						<div style= "vnd.ms-excel.numberformat:@">${nameListRequestExport.phone_number}</div>
					</vlh:column>
					<vlh:column title="状态" attributes="width='280px'" >
						<s:if test="${nameListRequest.status == 'W' }">待审核</s:if>
						<s:if test="${nameListRequest.status == 'Y' }">生效</s:if>
						<s:if test="${nameListRequest.status == 'N' }">审核不通过</s:if>
						<s:if test="${nameListRequest.status == 'E' }">失效</s:if>
					</vlh:column>
					<vlh:column title="失败原因" property="failure" attributes="width='80px'" />
					<vlh:column title="创建时间" property="create_time" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
					<vlh:column title="完成时间" property="success_time" attributes="width='180px'" format="yyyy-MM-dd HH:mm:ss"/>
				</vlh:row>
			</table>
		</vlh:root>
	</s:if>
	<s:else>
		无查询结果
	</s:else>
</body>
</html>