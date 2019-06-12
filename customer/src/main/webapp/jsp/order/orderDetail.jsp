<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
	<head>
		<script type="text/javascript">					
		</script>	
	</head>
	<body>	
		
		<div class="pop_tit" ><h2>订单详细信息</h2></div>
		
		<s:set name="entity" value="#attr['order'].list[0]" />
					
		<table cellpadding="0" cellspacing="1" class="global_tabledetail">			
			<tr>
				<th width="20%">商户编号：</th>
				<td width="30%">
					${entity.customer_no}				
				</td>
				<th>交易类型：</th>
				<td>
					<dict:write dictTypeId="TransType" dictId="${entity.trans_type}"></dict:write>
				</td>
			</tr>	
			<tr>
				<th>终端号：</th>
				<td>${entity.pos_cati}</td>
				<th>批次号：</th>
				<td>${entity.pos_batch}</td>
			</tr>	
			<tr>
				<th>流水号：</th>
				<td>${entity.pos_request_id}</td>
				<th>授权号：</th>
				<td>${entity.authorization_code}</td>
			</tr>
			<tr>
				<th>参考号：</th>
				<td>${entity.external_id}</td>
				<th>商户订单号：</th>
				<td>${entity.customer_order_code}</td>
			</tr>
			<tr>
				<th>卡号：</th>
				<td colspan="3"><boss:pan pan="${entity.pan}" /></td>
			</tr>				
			<tr>
				<th>金额：</th>
				<td>${entity.amount}</td>
				<th>手续费：</th>
				<td>${entity.customer_fee}</td>
			</tr>
			<tr>
				<th>发卡行：</th>
				<td>${entity.issuer}</td>
				<th>卡类型：</th>
				<td>
					<dict:write dictTypeId="CardType" dictId="${entity.card_type}"></dict:write>
				</td>
			</tr>	
			<tr>				
				<th>交易状态：</th>
				<td>
					<dict:write dictTypeId="OrderStatus" dictId="${entity.status}"></dict:write>
				</td>
				<th>是否入账：</th>
				<td>
					<dict:write dictTypeId="YesNo" dictId="${entity.credit_status}"></dict:write>
				</td>
			</tr>	
			<tr>				
				<th>创建时间：</th>
				<td>
					<s:date name="#entity.create_time" format="yyyy-MM-dd HH:mm" />
				</td>
				<th>完成时间：</th>
				<td>
					<s:date name="#entity.complete_time" format="yyyy-MM-dd HH:mm" />
				</td>
			</tr>
			<tr>				
				<th>结算时间：</th>
				<td>
					<s:date name="#entity.settle_time" format="yyyy-MM-dd HH:mm" />
				</td>
				<th>入账时间：</th>
				<td>
					<s:date name="#entity.credit_time" format="yyyy-MM-dd HH:mm" />
				</td>
			</tr>						
		</table>				
	</body>
</html>
