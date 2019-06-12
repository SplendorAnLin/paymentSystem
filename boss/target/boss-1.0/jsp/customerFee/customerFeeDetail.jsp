<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>

<html>
	<head>
		<script type="text/javascript">			
		function backQuery() {
			var url = "${pageContext.request.contextPath}/customerFeeQueryAction.action";
			window.location.href=url;
		}
		</script>	
	</head>
	<body>	
		
		<div class="pop_tit" ><h2>商户费率详细信息</h2></div>
		
		<table cellpadding="0" cellspacing="1" class="global_tabledetail">
			<tr><td colspan="4" style="text-align: center;"><B>商户基本信息</B></td></tr>			
			<tr>
				<th width="20%">商户编号：</th>
				<td width="30%">
					${customerFee.customerNo}				
				</td>
				<th width="20%">商户简称：</th>
				<td width="30%">
			    </td>
			</tr>
			<tr>
				<th width="20%">产品类型：</th>
				<td width="30%">
				<dict:write dictId="${customerFee.productType }" dictTypeId="PAYTYPE"></dict:write>
			    </td>
				<th width="20%">状态：</th>
				<td width="30%">
				<dict:write dictId="${customerFee.status }" dictTypeId="SERVICE_PROVIDER_STATUS_COLOR"></dict:write>
			    </td>
			</tr>
			<tr>
				<th width="20%">费率类型：</th>
				<td width="30%">
					<dict:write dictId="${customerFee.feeType }" dictTypeId="FEE_TYPE"></dict:write>
			    </td>
				<th width="20%">费率：</th>
				<td width="30%">${customerFee.fee}
			    </td>
			</tr>
			<tr>
				<th width="20%">最大费率：</th>
				<td width="30%">
					<fmt:formatNumber currencySymbol="" type="currency" value="${customerFee.maxFee}" />
			    </td>
				<th width="20%">最小费率：</th>
				<td width="30%">
					<fmt:formatNumber currencySymbol="" type="currency" value="${customerFee.minFee}" />
			    </td>
			</tr>
			
							
		</table>	
		<br/>
		<center>
			<input type="button" class="global_btn" value="返回" onclick="backQuery();"/>
		</center>
		<br/>
	</body>
</html>
