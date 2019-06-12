<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <title>结果</title>
  <%@ include file="../../include/header.jsp" %>
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
          <td>账户编号</td>
          <td>交易订单号</td>
          <td>订单金额</td>
          <td>资金变动方向</td>
          <td>交易时间</td>
          <td>系统编码</td>
          <td>业务编码</td>
          <td>对账日期</td>
          <td>创建时间</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.object }" var="rec">
          <tr>
            <td>${rec.accountNo}</td>
            <td>${rec.tradeOrderCode}</td>
            <td><fmt:formatNumber value="${rec.amount}" pattern="#.##"/></td>
            <td>${rec.fundSymbol}</td>
            <td><fmt:formatDate value="${rec.transTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${rec.systemCode}</td>
            <td>${rec.bussinessCode}</td>
            <td><fmt:formatDate value="${rec.reconDate }" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${rec.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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

<%@ include file="../../include/bodyLink.jsp" %>
<script>
    // 导出
    function infoExport() {
        window.open('reconOrderDataExport.action?' + $('#reconOrderDataQueryForm', window.parent.document).serialize());
    }
</script>
</body>
</html>
