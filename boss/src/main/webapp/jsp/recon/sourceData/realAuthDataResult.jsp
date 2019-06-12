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
          <td>交易订单号</td>
          <td>交易类型</td>
          <td>支付类型</td>
          <td>接口类型</td>
          <td>接口订单号</td>
          <td>订单金额</td>
          <td>订单手续费</td>
          <td>交易完成时间</td>
          <td>对账日期</td>
          <td>创建时间</td>
          <td>备注</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.object }" var="rec">
          <tr>
            <td>${rec.tradeOrderCode}</td>
            <td>${rec.transType}</td>
            <td>${rec.payType}</td>
            <td>${rec.interfaceType}</td>
            <td>${rec.interfaceOrderCode}</td>
            <td><fmt:formatNumber value="${rec.amount}" pattern="#.##"/></td>
            <td><fmt:formatNumber value="${rec.fee}" pattern="#.##"/></td>
            <td><fmt:formatDate value="${rec.transTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><fmt:formatDate value="${rec.reconDate }" pattern="yyyy-MM-dd"/></td>
            <td><fmt:formatDate value="${rec.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${rec.remark}</td>
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
