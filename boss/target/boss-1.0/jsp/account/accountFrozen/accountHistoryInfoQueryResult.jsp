<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户资金操作历史</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 1200px;padding: 10px;">
  
  <div class="title-h1 fix">
    <span class="primary fl">冻结/解冻操作历史</span>
  </div>
  <c:if test="${accountFreezeDetailResponse != null && accountFreezeDetailResponse.size() > 0}">
    <div class="table-warp">
     <div class="table-sroll">
      <table class="data-table shadow--2dp  w-p-100 tow-color">
        <thead>
          <tr>
            <th>账号</th>
            <th>冻结金额</th>
            <th>冻结编码</th>
            <th>冻结状态</th>
            <th>流水号</th>
            <th>冻结日期</th>
            <th>解冻日期</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${accountFreezeDetailResponse }" var="ard">
            <tr>
              <td>${ard.accountNo }</td>
              <td><fmt:formatNumber value="${ard.freezeBalance }"  pattern="#.##" /></td>
              <td>${ard.freezeNo }</td>
              <td><dict:write dictId="${ard.status.name() }" dictTypeId="ACCOUNT_HISTORY_STATUS_COLOR"></dict:write></td>
              <td>${ard.transFlow }</td>
              <td><fmt:formatDate value="${ard.freezeDate }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              <td><fmt:formatDate value="${ard.freezeLimit }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
     </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrapCustomize" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${accountFreezeDetailResponse eq null or accountFreezeDetailResponse.size() == 0}">
    <p class="pd-10">无符合条件的记录</p>
  </c:if>
  
  <%@ include file="../../include/bodyLink.jsp"%>
  <script>
   // 分页
   $('#PageWrapCustomize').pageCustom('accountFZHistoryAction.action', 'accountNo=<%=request.getParameter("accountNo")%>');
  </script>
</body>
</html>
