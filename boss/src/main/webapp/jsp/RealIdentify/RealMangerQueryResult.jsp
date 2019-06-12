<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
	<head>
		 <%@ include file="../include/header.jsp"%>
		<title>实名认证查询结果</title>
	</head>

	<body class="pb-2">

		<div class="title-h1 fix">
			<span class="primary fl">查询结果</span>
		</div>
	  <c:if test="${page.object != null && page.object.size() > 0}">
		<div class="table-warp">
			<div class="table-sroll">
				<table class="data-table shadow--2dp  w-p-100 tow-color">
					<thead>
						<tr>
							<th>所有者编号</th>
							<th>所有者名称</th>
							<th>实名认证业务类型</th>
							<th>认证状态</th>
							<th>是否实时</th>
							<th>路由模板</th>
							<th>备注</th>
							<th>创建时间</th>
							<th>修改时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.object }" var="auth">
							<tr>
								<td>${auth.customerNo }</td>
								<td>${auth.customerName }</td>
								<td><dict:write dictId="${auth.busiType }" dictTypeId="BUSI_TYPE"></dict:write></td>
								<td><dict:write dictId="${auth.status }" dictTypeId="STATUS"></dict:write></td>
								<td><dict:write dictId="${auth.isActual }" dictTypeId="IS_ACTUAL"></dict:write></td>
								<td>${auth.name }</td>
								<td>${auth.remark }</td>
								<td><fmt:formatDate value="${auth.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${auth.lastUpdateTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>
									<a data-myiframe='{
                "target": "${pageContext.request.contextPath}/findAuthConfigBeanByCustomerNoAndBusiType.action?authConfigBean.customerNo=${auth.customerNo }&authConfigBean.busiType=${auth.busiType }",
                "btnType": 2,
                "btns": [
                  {"lable": "取消"},
                  {"lable": "修改", "trigger": ".btn-submit"}
                ]
              }' class="btn" href="javascript:void(0);">修改</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
	</c:if>
	<c:if test="${page.object eq null or page.object.size() == 0 }">
	无符合条件的记录
	</c:if>

<%@ include file="../include/bodyLink.jsp"%>

	</body>

</html>