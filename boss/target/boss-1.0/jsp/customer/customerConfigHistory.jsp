<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>操作历史</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1200px;padding: 10px;">

  <div class="title-h1 fix">
    <span class="primary fl">商户交易配置修改历史</span>
  </div>
  <c:if test="${customerCofig != null && customerCofig.size() > 0 }">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>商户编号</th>
              <th>产品类型</th>
              <th>交易开始时间</th>
              <th>交易结束时间</th>
              <th>单笔最高金额</th>
              <th>单笔最低金额</th>
              <th>日交易上限</th>
              <th>请求日期</th>
              <th>操作人</th>
              <th>变更原因</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${customerCofig }" var="acd">
              <tr>
                <td><c:out value="${acd.customerNo }" /></td> 
                <td><dict:write dictId="${acd.productType }" dictTypeId="PRODUCT_TYPE"></dict:write></td>
                <td><c:out value="${acd.startTime }" /></td>
                <td><c:out value="${acd.endTime }" /></td>
                <td><fmt:formatNumber value="${acd.maxAmount }" pattern="#.##" /></td>
                <td><fmt:formatNumber value="${acd.minAmount }" pattern="#.##" /></td>
                <td><fmt:formatNumber value="${acd.dayMax }" pattern="#.##" /></td>
                <td><fmt:formatDate value="${acd.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><c:out value="${acd.operator }" /></td>
                <td><c:out value="${acd.reason }" /></td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrapCustomize" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${customerCofig eq null or customerCofig.size() == 0 }">
    无符合条件的查询结果
  </c:if>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
   // 分页
   $('#PageWrapCustomize').pageCustom('customerConfigHistory.action', 'customerConfig.customerNo=<%=request.getParameter("customerConfig.customerNo")%>&customerConfig.productType=<%=request.getParameter("customerConfig.productType")%>');
  </script>
</body>
</html>
