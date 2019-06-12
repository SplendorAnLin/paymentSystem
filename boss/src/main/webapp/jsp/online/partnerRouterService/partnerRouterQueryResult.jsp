<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户路由-结果</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  
  <c:if test="${partnerRouters.size() > 0}">
  
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>商户编码</th>
              <th>商户角色</th>
              <th>商户路由状态</th>
              <th>创建时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${partnerRouters }" var="protuers">
              <tr>
                <td><c:out value="${protuers.partnerCode }" /></td>
                <td><dict:write dictId="${protuers.partnerRole }" dictTypeId="PARTNER_ROLE"></dict:write>
                <td><dict:write dictId="${protuers.status }" dictTypeId="ACCOUNT_STATUS_COLOR"></dict:write></td>
                <td><fmt:formatDate value="${protuers.createTime }" pattern="yyyy-MM-dd" /></td>
                <td>
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/partnerRouter/toPartnerRouterMofidyOrDetail.action?partnerRouterBean.code=${protuers.code }&operateType=UPDATE",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "修改", "trigger": ".btn-submit"}
                    ]
                  }' class="btn-small">修改</button>
                  <button data-dialog="${pageContext.request.contextPath}/toPartnerRouterMofidyOrDetail.action?partnerRouterBean.code=${protuers.code }&operateType=QUERY" class="btn-small">详细</button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${partnerRouters eq null or partnerRouters.size() == 0}"> 
    无符合条件的查询结果
  </c:if>
  
  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
