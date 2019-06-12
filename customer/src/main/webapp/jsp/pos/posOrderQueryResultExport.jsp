<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%
	response.setHeader( "Pragma ", "public");
	response.setHeader( "Cache-Control ", "must-revalidate, post-check=0, pre-check=0 ");
	response.addHeader( "Cache-Control ", "public ");
	response.addHeader("Content-Disposition", "attachment; filename=posOrderInfo.xls");
	response.setContentType("application/vnd.ms-excel.numberformat:@;charset=UTF-8");
%>
<html>
<head>
	<style type="text/css">
		.txt{
			mso-ignore:padding;
			mso-generic-font-family:auto;
			mso-font-charset:134;
			mso-number-format:"\@";
			mso-background-source:auto;
			mso-pattern:auto;
		}
		td{
			white-space:nowrap;
			border:1px dotted #333;
		}
	</style>
</head>
<body class="w-p-100 min-width">
	<div class="ctx-iframe">
		<div class="table-warp pd-r-10">
			<div class="table-scroll">
				<s:if test="#attr['posOrderQueryExport'].list.size()>0">
					<vlh:root value="posOrderQueryExport" url="?" includeParameters="*"
						configName="defaultlook">
						<table class="table-two-color">
							<thead>
								<tr>
									<th>交易流水号</th>
									<th>商户订单号</th>
									<th>收款方</th>
									<th>收款方简称</th>
									<th>POS机具序列号</th>
									<th>支付方式</th>
									<th>订单金额</th>
									<th>手续费</th>
									<th>订单状态</th>
									<th>入账状态</th>
									<th>下单时间</th>
									<th>完成时间</th>
								</tr>
							</thead>
							<vlh:row bean="posOrderQueryExport">
								<s:set name="order" value="#attr['posOrderQueryExport']" />
								<input type="hidden"
										value="${order.customer_no }"
										class="hiddenReceiver" />
								<vlh:column>
									<div style="vnd.ms-excel.numberformat:@;charset=UTF-8">
										${order.external_id }</div>
								</vlh:column>
								<vlh:column>
									<div style="vnd.ms-excel.numberformat:@;charset=UTF-8">
										${order.customer_order_code }</div>
								</vlh:column>
								<vlh:column property="customer_no" />
								<vlh:column property="short_name" />
								<vlh:column property="pos_sn" />
								<vlh:column>POS收单</vlh:column>
								<vlh:column property="amount" format="#0.00#"/>
								<vlh:column property="customer_fee" format="#0.00#"/>
								<vlh:column property="status">
									<dict:write dictId="${order.status }" dictTypeId="POS_ORDER_STATUS"></dict:write>
								</vlh:column>
								<vlh:column property="credit_status" >
									<dict:write dictId="${order.credit_status }" dictTypeId="CREDIT_STATUS_COLOR"></dict:write>
								</vlh:column>
								<vlh:column property="create_time"  format="yyyy-MM-dd HH:mm:ss"/>
								<vlh:column property="complete_time"  format="yyyy-MM-dd HH:mm:ss"/>
							</vlh:row>
						</table>
					</vlh:root>
				</s:if>
				<s:else>
					无符合条件的查询结果
				</s:else>
			</div>
		</div>

	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>