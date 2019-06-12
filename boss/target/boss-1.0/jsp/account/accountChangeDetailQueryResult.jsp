<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户操作历史</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1300px;padding: 10px;">
  
  <div class="title-h1 fix">
    <span class="primary fl">账户操作历史</span>
  </div>
  <c:if test="${accountChangeDetails != null && accountChangeDetails.size() > 0}">
    <div class="table-warp">
      <div class="table-sroll">
      <table class="data-table shadow--2dp  w-p-100 tow-color">
        <thead>
          <tr>
            <th>账户编号</th>
            <th>账户类型</th>
            <th>账户状态</th>
            <th>系统编码</th>
            <th>系统流水号</th>
            <th>业务类型码</th>
            <th>用户编号</th>
            <th>用户角色</th>
            <th>请求日期</th>
            <th>操作人</th>
            <th>变更原因</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${accountChangeDetails }" var="acd">
            <tr>
              <td>${acd.accountNo }</td>
              <td><dict:write dictId="${acd.accountType.name() }" dictTypeId="ACCOUNT_BASE_TYPE"></dict:write></td>
              <td><dict:write dictId="${acd.accountStatus.name() }" dictTypeId="ACCOUNT_STATUS_INFO_COLOR"></dict:write></td>
              <td>${acd.systemCode }</td>
              <td>${acd.systemFlow }</td>
              <td><dict:write dictId="${acd.bussinessCode }" dictTypeId="BUSS_CODE"></dict:write></td>
              <td>${acd.userNo }</td>
              <td><dict:write dictId="${acd.userRole.name() }" dictTypeId="USER"></dict:write></td>
              <td><fmt:formatDate value="${acd.requestTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              <td>${acd.operator }</td>
              <td>${acd.changeReason }</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrapCustomize" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${accountChangeDetails eq null or accountChangeDetails.size() == 0}">
    无符合条件的记录
  </c:if>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
   // 分页
   $('#PageWrapCustomize').pageCustom('findAccountChangeRecordsAction.action', 'accountNo=<%=request.getParameter("accountNo")%>');
  </script>
</body>
</html>
