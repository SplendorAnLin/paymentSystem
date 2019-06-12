<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../include/header.jsp"%>
<head>		
	<script type="text/javascript">	
	</script>
</head>

<body>		
	<div class="search_tit"><h2>我的账户信息</h2></div>
	<table class="global_table" cellpadding="0" cellspacing="0">
		<tr>
			<th width="30%">账户余额：</th>
			<td width="70%">
				<span style="color:red;font-size:14px;"><fmt:formatNumber value='${accountBean.balance}' pattern='#.##'/></span>&nbsp;元
			</td>
		</tr>
		<tr>	
			<th>账户状态：</th>
			<td>
				<dict:write dictId="${accountBean.status}" dictTypeId="AccountStatus"></dict:write>
			</td>						
		</tr>
		<tr>
			<th>账户编号：</th>
			<td>
				${accountBean.accountNo}
			</td>
		</tr>
		<tr>	
			<th>账户类型：</th>
			<td>
				<dict:write dictId="${accountBean.accountType}" dictTypeId="AccountType"></dict:write>
			</td>						
		</tr>	
		
		<s:if test="${accountRechargeBean != null}">
			<tr>
				<th width="30%"></th>
				<td width="70%"></td>
			</tr>
			<tr>
				<th width="30%">账户余额：</th>
				<td width="70%">
					<span style="color:red;font-size:14px;"><fmt:formatNumber value='${accountRechargeBean.balance}' pattern='#.##'/></span>&nbsp;元
				</td>
			</tr>
			<tr>	
				<th>账户状态：</th>
				<td>
					<dict:write dictId="${accountRechargeBean.status}" dictTypeId="AccountStatus"></dict:write>
				</td>						
			</tr>
			<tr>
				<th>账户编号：</th>
				<td>
					${accountRechargeBean.accountNo}
				</td>
			</tr>
			<tr>	
				<th>账户类型：</th>
				<td>
					<dict:write dictId="${accountRechargeBean.accountType}" dictTypeId="AccountType"></dict:write>
				</td>						
			</tr>
		</s:if>
	</table>
</body>
