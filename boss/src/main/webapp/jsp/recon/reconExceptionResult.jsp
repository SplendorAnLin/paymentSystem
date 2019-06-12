<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <title>差错查询 - 结果</title>
  <%@ include file="../include/header.jsp" %>
</head>
<body class="pb-2">

<div class="title-h1 fix">
  <span class="primary fl">查询结果</span>
  <a class="fr" href="javascript:infoExport();">导出</a>
</div>
<c:if test="${page.object != null && page.object.size() > 0 }">
  <div class="table-warp">
    <div class="table-sroll">
      <table class="data-table shadow--2dp  w-p-100 tow-color">
        <thead>
        <tr>
          <th><input type="checkbox" class="checkbox"></th>
          <th>对账单编号</th>
          <th>对账类型</th>
          <th>交易订单/账户订单</th>
          <th>接口订单/银行通道订单</th>
          <th>对账异常类型</th>
          <th>金额</th>
          <th>处理状态</th>
          <th>处理备注</th>
          <th>对账日期</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.object }" var="rec">
          <tr>
            <td>
              <c:if test="${rec.handleStatus =='HANDLING'}">
                <input type="checkbox" class="checkbox" value="${rec.reconOrderId }"/>
              </c:if>
            </td>
            <td class="reconNo">${rec.reconOrderId }</td>
            <td><dict:write dictId="${rec.reconType.name() }" dictTypeId="RECON_TYPE"></dict:write></td>
            <td class="orderCode">${rec.tradeOrderCode }</td>
            <td class="orderCode">${rec.interfaceOrderCode }</td>
            <td>${rec.reconExceptionType.remark }</td>
            <td><fmt:formatNumber value="${rec.amount}" pattern="#.##"/></td>
            <td><dict:write dictId="${rec.handleStatus }" dictTypeId="HANDLE_STATUS"></dict:write></td>
            <td>${rec.handleRemark }</td>
            <td><fmt:formatDate value="${rec.reconDate }" pattern="yyyy-MM-dd"/></td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
  <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"
       current="${page.currentPage}"></div>
</c:if>
<c:if test="${page.object eq null or page.object.size() == 0 }">
  无符合条件的记录
</c:if>

<%@ include file="../include/bodyLink.jsp" %>
<script>
    // 导出
    function infoExport() {
        window.open('exceptionExport.action?' + $('#findReconByDateForm', window.parent.document).serialize());
    }
</script>
</body>
</html>
