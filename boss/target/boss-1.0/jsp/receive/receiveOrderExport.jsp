<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%		
	response.setHeader( "Pragma ", "public"); 
	response.setHeader( "Cache-Control ", "must-revalidate, post-check=0, pre-check=0 "); 
	response.addHeader( "Cache-Control ", "public "); 
	response.addHeader("Content-Disposition", "attachment; filename="+java.net.URLEncoder.encode("代收订单", "utf8")+"-"+ com.pay.common.util.DateUtil.formatDate(new Date(), "yyyy-MM-dd")+".xls");
	response.setContentType("application/vnd.ms-excel.numberformat:@;charset=UTF-8"); 
%>
<html>
<head>
<style type="text/css">
.txt {
	mso-ignore: padding;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: "\@";
	mso-background-source: auto;
	mso-pattern: auto;
}

td {
	white-space: nowrap;
	border: 1px dotted #333;
}
</style>
</head>
<body>
	<s:if test="#attr['receiveOrderExport'].list.size()>0">
		<vlh:root value="receiveOrderExport" url="?" includeParameters="*"
			configName="defaultlook">
			<table cellpadding="1" cellspacing="1" align="center"
				class="global_tableresult" style="width: 100%">
				<thead>
					<tr>
						<td width="140px;">所有者编号</td>
						<td width="220px;">收款单号</td>
						<td width="80px;">银行编码</td>
						<th width="140px;">账户号</th>
						<th width="80px;">账户名称</th>
						<th width="80px;">账户类型</th>
						<th width="80px;">账号类型</th>
						<th width="50px;">金额</th>
						<th width="50px;">成本</th>
						<th width="80px;">手续费</th>
						<th width="80px;">证件类型</th>
						<th width="140px;">证件号码</th>
						<th width="80px;">订单状态</th>
						<th width="80px;">清算状态</th>
						<th width="140px;">创建时间</th>
						<th width="140px;">完成时间</th>
					</tr>
				</thead>
				<vlh:row bean="defConfig">
					<s:set name="entity" value="#attr['defConfig']" />
					<vlh:column property="customer_no" attributes="width='10%'" />
					<vlh:column property="receive_id" attributes="width='12%'" />
					<vlh:column property="payer_bank_no" attributes="width='10%'" />
					<vlh:column attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${entity.account_no}</div>
					</vlh:column>
					<vlh:column property="account_name" attributes="width='7%'" />
					<vlh:column property="acc_type" attributes="width='8%'">
						<c:if test="${entity.acc_type eq 'OPEN'}">
							<span>对公</span>
						</c:if>
						<c:if test="${entity.acc_type eq 'INDIVIDUAL'}">
							<span>对私</span>
						</c:if>
					</vlh:column>
					<vlh:column property="acc_no_type" attributes="width='8%'">
						<c:if test="${entity.acc_no_type eq 'DEBIT_CARD'}">
							<span>借记卡</span>
						</c:if>
						<c:if test="${entity.acc_no_type eq 'PASSWORK'}">
							<span>存折</span>
						</c:if>
						<c:if test="${entity.acc_no_type eq 'CREDIT_CARD'}">
							<span>信用卡</span>
						</c:if>
						<c:if test="${entity.acc_no_type eq 'OPENNO'}">
							<span>公司账号</span>
						</c:if>
					</vlh:column>
					<vlh:column property="amount" attributes="width='10%'" />
					<vlh:column property="cost" attributes="width='10%'" />
					<vlh:column property="fee" attributes="width='10%'" />
					<vlh:column property="certificates_type" attributes="width='8%'">
						<c:if test="${entity.certificates_type eq 'ID'}">
							<span>身份证</span>
						</c:if>
						<c:if test="${entity.certificates_type eq 'STUDENTCARD'}">
							<span>学生证</span>
						</c:if>
						<c:if test="${entity.certificates_type eq 'MILITARYID'}">
							<span>军官证</span>
						</c:if>
						<c:if test="${entity.certificates_type eq 'PASSPORT'}">
							<span>护照</span>
						</c:if>
					</vlh:column>
					<vlh:column attributes="width='100px'" >
						<div style= "vnd.ms-excel.numberformat:@">${entity.certificates_code}</div>
					</vlh:column>
					<vlh:column attributes="width='8%'">
						<c:if test="${entity.order_status eq 'WAIT'}">
							<span>待审核</span>
						</c:if>
						<c:if test="${entity.order_status eq 'REJECT'}">
							<span>审核拒绝</span>
						</c:if>
						<c:if test="${entity.order_status eq 'PROCESS'}">
							<span>处理中</span>
						</c:if>
						<c:if test="${entity.order_status eq 'FAILED'}">
							<span>失败</span>
						</c:if>
						<c:if test="${entity.order_status eq 'SUCCESS'}">
							<span>成功</span>
						</c:if>
					</vlh:column>
					<vlh:column attributes="width='8%'">
						<c:if test="${entity.clear_status eq 'UN_CLEARING'}">
							<span>未清算</span>
						</c:if>
						<c:if test="${entity.clear_status eq 'CLEARING_FAILED'}">
							<span>清算失败</span>
						</c:if>
						<c:if test="${entity.clear_status eq 'CLEARING_SUCCESS'}">
							<span>已清算</span>
						</c:if>
					</vlh:column>
					<vlh:column property="create_time" attributes="width='150px'"
						format="yyyy-MM-dd HH:mm:ss" />
					<vlh:column property="last_update_time" attributes="width='150px'"
						format="yyyy-MM-dd HH:mm:ss" />
				</vlh:row>
			</table>
		</vlh:root>
	</s:if>
	<s:if test="#attr['receiveOrderQuery'].list.size()==0">
		无符合条件的查询结果
	</s:if>
</body>
</html>