<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<%		
	response.setHeader( "Pragma ", "public"); 
	response.setHeader( "Cache-Control ", "must-revalidate, post-check=0, pre-check=0 "); 
	response.addHeader( "Cache-Control ", "public "); 
	response.addHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode("设备采购单", "utf8")+"["+ com.pay.common.util.DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm")+"].xls");
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

	<s:if test="#attr['deviceExport'].list.size()>0">
		<vlh:root value="deviceExport" url="?" includeParameters="*" configName="defaultlook">
			<table cellpadding="0" cellspacing="1" class="global_tableresult">
				<vlh:row bean="deviceExport">					
					<s:set name="deviceExport" value="#attr['deviceExport']" />
					<vlh:column title="所属批次号" property="batch_number"/>
					<vlh:column title="收款码编号" property="customer_pay_no" />
					<vlh:column title="效验码" property="check_code"	/>
					<vlh:column title="二维码链接" property="qrcode_url"/>
					<vlh:column title="创建时间" property="create_time" format="yyyy-MM-dd HH:mm:ss"/>
				</vlh:row>
			</table>
		</vlh:root>
	</s:if>
	<s:else>
		<table cellpadding="0" cellspacing="1" class="global_tableresult">
			<tr><td>暂无数据</td></tr>
		</table>
	</s:else>
</body>
</html>