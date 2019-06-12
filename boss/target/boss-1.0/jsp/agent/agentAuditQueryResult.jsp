<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>服务商审核-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
  </div>
  <c:if test="${page.object != null && page.object.size() > 0 }">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>服务商编号</th>
              <th>服务商上级编号</th>
              <td>服务商等级</td>
              <th>服务商全称</th>
              <th>服务商简称</th>
              <th>服务商类型</th>
              <th>业务联系手机号</th>
              <th>服务商联系人</th>
              <th>服务商状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${page.object}" var="agent">
              <tr>
                <td>${agent.agent_no }</td>
                <td>${agent.paren_id }</td>
                <td><dict:write dictId="${agent.agent_level }" dictTypeId="SERVICE_PROVIDER_LEVEL"></dict:write></td>
                <td>${agent.full_name }</td>
                <td>${agent.short_name }</td>
                <td><dict:write dictId="${agent.agent_type }" dictTypeId="ACCOUNT_TYPE"></dict:write></td>
                <td>${agent.phone_no }</td>
                <td>${agent.link_name }</td>
                <td><dict:write dictId="${agent.status }" dictTypeId="SERVICE_PROVIDER_STATUS_COLOR"></dict:write></td>
                <td>
                  <button data-myiframe='{
                    "target": "${pageContext.request.contextPath}/agentAuditDetail.action?agent.agentNo=${agent.agent_no}",
                    "btns": [
                      {"lable": "取消"},
                      {"lable": "通过审核", "trigger": ".btn-submit-accept"},
                      {"lable": "拒绝审核", "trigger": ".btn-submit-reject"}
                    ]
                  }' class="btn-small">审核</button>
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
    <p class="pd-10">无符合条件的查询结果</p>
  </c:if>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
