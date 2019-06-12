<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<html class="w-p-100">
<head>
</head>
<body class="w-p-100 min-width">
	<div class="ctx-iframe">
		<div class="title-h1 fix">
			<span class="primary fl">查询结果</span> <a class="export-link f-12 fr"
				href="javascript:void(0);" onclick="window.parent.listExport();">导出</a>
		</div>
		<s:if test="#attr['nameListRequest'].list.size()>0">
			<vlh:root value="nameListRequest" url="?" includeParameters="*"
				configName="defaultlook">
				<div class="table-warp pd-r-10">
					<div class="table-scroll">
						<table class="table-two-color">
							<thead>
								<tr>
									<th>商户编号</th>
									<th>姓名</th>
									<th>证件类型</th>
									<th>证件号</th>
									<th>卡类型</th>
									<th>账户</th>
									<th>手机号</th>
									<th>状态</th>
									<th>失败原因</th>
									<th>创建时间</th>
									<th>完成时间</th>
								</tr>
							</thead>
							<vlh:row bean="nameListRequest">
								<s:set name="nameListRequest" value="#attr['nameListRequest']" />
								<vlh:column property="owner_id" />
								<vlh:column property="user_name" />
								<vlh:column >
								<dict:write dictId="${nameListRequest.type_of_certificate }" dictTypeId="TYPE_OF_CERTIFICATE"></dict:write>
								</vlh:column>
								<vlh:column property="license_number" />
								<vlh:column>
								<dict:write dictId="${nameListRequest.card_typ }" dictTypeId="WHITE_CARD_TYPE"></dict:write>
								</vlh:column>
								<vlh:column property="account" />
								<vlh:column property="phone_number" />
								<vlh:column>
									<dict:write dictId="${nameListRequest.status }" dictTypeId="LIST_STATUS_COLOR"></dict:write>
								</vlh:column>
								<vlh:column property="failure" />
								<vlh:column property="create_time" format="yyyy-MM-dd HH:mm:ss" />
								<vlh:column property="success_time" format="yyyy-MM-dd HH:mm:ss" />
							</vlh:row>
						</table>
					</div>
				</div>
				<%@ include file="../include/vlhPage.jsp"%>
			</vlh:root>
		</s:if>
		<s:else>
		无符合条件的查询结果
	</s:else>
	</div>
	<%@ include file="../include/bodyLink.jsp"%>
</body>
</html>