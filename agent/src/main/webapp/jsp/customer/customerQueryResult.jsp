<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>商户信息-结果</title>
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
              <th>商户编号</th>
              <th>商户名称</th>
              <th>商户简称</th>
              <th>服务商</th>
              <th>商户地址</th>
              <th>状态</th>
              <th>商户类型</th>
              <th>拒绝原因</th>
              <th>开通时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${page.object}" var="stu">
              <tr>
                <td>${stu.customer_no }</td>
                <td>${stu.full_name }</td>
                <td>${stu.short_name }</td>
                <td>${stu.agent_no }</td>
                <td>${stu.addr }</td>
                <td><dict:write dictId="${stu.status }" dictTypeId="CUSTOMER_STATUS_COLOR"></dict:write></td>
                <td><dict:write dictId="${stu.customer_type }" dictTypeId="AGENT_TYPE"></dict:write></td>
                <td><c:if test="${stu.status == 'AUDIT_REFUSE'}">${stu.msg }</c:if></td>
                <td>${stu.create_time }</td>
                <td>
                  <button data-dialog="${pageContext.request.contextPath}/customerDetail.action?customerNo=${stu.customer_no }" class="btn-small">详细</button>
                  <c:if test="${stu.status eq 'AUDIT_REFUSE' }">
                    <button data-myiframe='{
                      "target": "${pageContext.request.contextPath}/toUpdateCustomerAction.action?customerNo=${stu.customer_no }",
                      "btns": [
                        {"lable": "取消"},
                        {"lable": "修改", "trigger": ".btn-submit"}
                      ]
                    }' class="btn-small">修改</button>
                  </c:if>
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
