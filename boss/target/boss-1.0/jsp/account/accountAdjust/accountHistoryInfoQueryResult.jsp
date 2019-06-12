<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户调账历史</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body style="width: 1200px;padding: 10px;">
  
  <div class="title-h1 fix">
    <span class="primary fl">账户调账历史</span>
  </div>
  <c:if test="${findResult != null && findResult.size() > 0}">
    <div class="table-warp">
      <div class="table-sroll">
      <table class="data-table shadow--2dp  w-p-100 tow-color">
        <thead>
          <tr>
            <th>用户号</th>
            <th>角色</th>
            <th>业务类型</th>
            <th>账户编号</th>
            <th>系统编码</th>
            <th>交易系统流水号</th>
            <th>资金标识</th>
            <th>交易金额</th>
            <th>余额</th>
            <th>创建日期</th>
            <th>交易日期</th>
            <th>原因</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${findResult }" var="ard">
            <tr>
              <td>${ard.USER_NO }</td>
              <td><dict:write dictId="${ard.USER_ROLE }" dictTypeId="USER"></dict:write></td>
              <td><dict:write dictId="${ard.BUSSINESS_CODE }" dictTypeId="BUSINESS_TYPE"></dict:write></td>
              <td>${ard.ACCOUNT_NO }</td>
              <td>${ard.SYSTEM }</td>
              <td>${ard.FLOW_ID }</td>
              <td><dict:write dictId="${ard.FUND_SYMBOL }" dictTypeId="CAPITAL_IDENTIFICATION_COLOR"></dict:write></td>
              <td><fmt:formatNumber value="${ard.AMOUNT }" pattern="#.##" /></td>
              <td><fmt:formatNumber value="${ard.REMAIN_AMT }" pattern="#.##" /></td>
              <td><fmt:formatDate value="${ard.CREATE_TIME }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              <td><fmt:formatDate value="${ard.LASTMODIFY_TIME }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
              <td>${ard.REASON }</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrapCustomize" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${findResult eq null or findResult.size() == 0}">
    <p class="pd-10">无符合条件的记录</p>
  </c:if>
  
  
  <%@ include file="../../include/bodyLink.jsp"%>
  <script>
   // 分页
   $('#PageWrapCustomize').pageCustom('accountHistoryAction.action', 'accountNo=<%=request.getParameter("accountNo")%>');
  </script>
</body>
</html>
