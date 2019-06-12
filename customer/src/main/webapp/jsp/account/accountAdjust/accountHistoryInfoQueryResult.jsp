<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
 <%@ include file="../../include/header.jsp"%>
  <title>财务明细</title>
  <style type="text/css">
  	.color-red
  	{
  		color: red !important;
  	}
  </style>
  <script type="text/javascript"
	src="${applicationScope.staticFilesHost}${pageContext.request.contextPath}/js/accountInfo/accountManagement.js"></script>
<script type="text/javascript">
	$(function() {
		initPage('${page.currentPage}', '${page.totalResult}',
				'${page.totalPage}', '${page.entityOrField}', parent.document
						.getElementById("form"));
	});
</script>
</head>
<body style="width: 100%;padding: 0 0 0.2em 0;">



    <div class="title-h1 fix">
      <span class="primary fl">账户调账历史</span>
    </div>
<c:if test="${accountRecordedDetails != null && accountRecordedDetails.size() > 0}">
    <div class="table-warp">
      <div class="table-sroll">
      <table class="data-table shadow--2dp  w-p-100 tow-color">
        <thead>
          <tr>
           
           <th>会员编号</th>
			<th>会员等级</th>
			<th>业务类型</th>
			<th>资金标识</th>
			<th>交易金额</th>
			<th>余额</th>
           
          </tr>
        </thead>
      <tbody>
		<c:forEach items="${accountRecordedDetails }" var="ard">
			<tr>
				<td>${ard.userNo }</td>
				<td>
					<dict:write dictId="${ard.userRole.name() }" dictTypeId="SYSTEM_NAME"></dict:write>
				</td>
				<td>
					<dict:write dictId="${ard.bussinessCode }" dictTypeId="BUSINESS_TYPE"></dict:write>
				</td>
				<td>
					<dict:write dictId="${ard.symbol.name() }" dictTypeId="CAPITAL_IDENTIFICATION_COLOR"></dict:write>
				</td>
				<td><fmt:formatNumber value="${ard.transAmount }"
						pattern="#.##" /></td>
				<td><fmt:formatNumber value="${ard.remainBalance }"
						pattern="#.##" /></td>
			</tr>
		</c:forEach>
	</tbody>
      </table>
      </div>
    </div>
<%@ include file="../../include/page.jsp"%>
		</c:if>
		<c:if
			test="${accountRecordedDetails eq null or accountRecordedDetails.size() == 0}">
		无符合条件的记录
	</c:if>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="71" total="8"  current="1"></div>





 <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>