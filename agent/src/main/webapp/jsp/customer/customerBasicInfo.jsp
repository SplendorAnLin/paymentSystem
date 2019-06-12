<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
	<head>
		<script type="text/javascript">					
		</script>	
	</head>
	<body>	
		
		<div class="detail_tit" ><h2>商户基本信息</h2></div>
		
		<s:set name="entity" value="#attr['customerInfo'].list[0]" />			
		<table cellpadding="0" cellspacing="1" class="global_tabledetail">			
			<tr>
				<th width="20%">商户编号：</th>
				<td width="30%">
					${entity.customer_no}				
				</td>
				<th width="20%">商户简称：</th>
				<td width="30%">
					${entity.short_name}
			   </td>
			</tr>
			<tr>
				<th width="20%">状态：</th>
				<td width="30%">
					<dict:write dictId="${entity.status}" dictTypeId="STATUS_COLOR"></dict:write>			
				</td>
				<th width="20%">创建时间：</th>
				<td width="30%">
					<s:date name="#entity.create_time" format="yyyy-MM-dd"/>
			   </td>
			</tr>
			<tr>
				<th width="20%">联系人：</th>
				<td width="30%">
					${entity.linkman}
			   </td>
			   <th width="20%">手机号码：</th>
				<td width="30%">
					${entity.phone_no}				
				</td>
			</tr>
			<tr>
			   <th width="20%">收货地址：</th>
				<td width="30%" colspan="3">
					${entity.receive_address}				
				</td>
			</tr>
							
		</table>				
	</body>
</html>
