<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../include/header.jsp"%>
<html>
<head>
<script type="text/javascript">
		function sub(){
   			 $("#form").submit();
  		}
	</script>
</head>
<body class="w-p-100 min-width" onload="query();">
	<div class="ctx-iframe">
		<form class="rest-from"
			action="${pageContext.request.contextPath }/toAccountAdjustQueryResult.action"
			method="post" id="form" target="queryResult">
			<div class="form-box mb-40 mt-20 ml-20">
				<div class="coll-1 ib mr-20">
					<div class="block">
						<span class="lable w-75">账号编号:</span>
						<div class="b-input w-170">
							<div class="input-wrap w-p-100">
								<input type="text" class="input-text rest-text"
									name="accountQueryBean.accountNo" id="accountNo">
							</div>
						</div>
					</div>
					<div class="block">
						<span class="lable w-75">会员编号:</span>
						<div class="b-input w-170">
							<div class="input-wrap w-p-100">
								<input type="text" class="input-text rest-text"
									name="accountQueryBean.userNo" id="userNo">
							</div>
						</div>
					</div>
					<div class="block">
						<span class="lable w-75">创建日期:</span>
						<div class="ib v-m">
							<div class="input-wrap w-170">
								<input type="text" class="input-text input-date rest-text"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"value="<%=DateUtil.today() %>"
									name="accountQueryBean.openStartDate" id="createTimeStart">
							</div>
							<span class="cut"> - </span>
							<div class="input-wrap w-170">
								<input type="text" class="input-text input-date rest-text"
									onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="<%=DateUtil.today() %>"
									name="accountQueryBean.openEndDate" id="createTimeEnd">
							</div>
						</div>
					</div>
				</div>
				<div class="coll-2 ib">
					<div class="block">
						<span class="lable w-75">会员等级:</span>
						<div class="b-input w-170">
							<dict:select styleClass="input-select" nullOption="true" dictTypeId="MEMBERSHIP_LEVEL" name="accountQueryBean.userRole" id="userRole"></dict:select>
						</div>
					</div>
					<div class="block">
						<span class="lable w-75">账户状态:</span>
						<div class="b-input w-170">
							<dict:select nullOption="true" styleClass="input-select" name="accountQueryBean.status" dictTypeId="ACCOUNT_STATUS_INFO" id="status"></dict:select>
						</div>
					</div>
					<div class="block">
						<span class="lable w-75">账户类型:</span>
						<div class="b-input w-170">
							<dict:select nullOption="true" styleClass="input-select" name="accountQueryBean.accountType" id="accountType" dictTypeId="ACCOUNT_BASE_TYPE"></dict:select>
						</div>
					</div>
					<input type="hidden" value="1" name="accountAdjustInfo" />
				</div>
				<div class="rowr-4 text-right" style="width: 434px;">
					<button class="btn verif-btn btn-loading query-btn"
						onclick="sub();">查询</button>
					<input type="reset" class="btn " type="reset" value="重 置"/>
				</div>
			</div>
		</form>
		<iframe name="queryResult" class="redirect table-result" src=""
			frameborder="0"></iframe>
	</div>
	<%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>