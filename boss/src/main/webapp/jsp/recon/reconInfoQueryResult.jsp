<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
  <title>内部对账查询 - 结果</title>
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
          <td>对账单编号</td>
          <td>对账日期</td>
          <td>对账类型</td>
          <td>A总笔数</td>
          <td>B总笔数</td>
          <td>A总金额</td>
          <td>B总金额</td>
          <td>A单边笔数</td>
          <td>B单边笔数</td>
          <td>A单边金额</td>
          <td>B单边金额</td>
          <td>金额错误笔数</td>
          <td>对账状态</td>
          <td>创建时间</td>
          <td>对账描述</td>
          <td>失败原因</td>
          <td>匹配元素A</td>
          <td>匹配元素B</td>
         </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.object }" var="rec">
          <tr>
            <td>${rec.code}</td>
            <td><fmt:formatDate value="${rec.reconDate }" pattern="yyyy-MM-dd"/></td>
            <td><dict:write dictId="${rec.reconType.name() }" dictTypeId="RECON_TYPE"></dict:write></td>
            <td>${rec.numsA }</td>
            <td>${rec.numsB}</td>
            <td><fmt:formatNumber value="${rec.amountA}" pattern="#.##"/></td>
            <td><fmt:formatNumber value="${rec.amountB}" pattern="#.##"/></td>
            <td>
              <c:choose>
                <c:when test="${rec.failNumsA=='0'}">${rec.failNumsA}</c:when>
                <c:otherwise><a class="color-red"
                                data-dialog="${pageContext.request.contextPath}/findReconInfo.action?reconException.reconOrderId=${rec.code}&reconException.reconExceptionType=${rec.matchA}"
                                href="javascript:void(0);">${rec.failNumsA}</a></c:otherwise>
              </c:choose>
            </td>
            <td>
              <c:choose>
                <c:when test="${rec.failNumsB=='0'}">${rec.failNumsB}</c:when>
                <c:otherwise><a class="color-red"
                                data-dialog="${pageContext.request.contextPath}/findReconInfo.action?reconException.reconOrderId=${rec.code}&reconException.reconExceptionType=${rec.matchB}"
                                href="javascript:void(0);">${rec.failNumsB}</a></c:otherwise>
              </c:choose>
            </td>
            <td><fmt:formatNumber value="${rec.failAmountA }" pattern="#.##"/></td>
            <td><fmt:formatNumber value="${rec.failAmountB}" pattern="#.##"/></td>
            <td>
              <c:choose>
                <c:when test="${rec.amountErrNum=='0'}">${rec.amountErrNum}</c:when>
                <c:otherwise><a class="color-red"
                                data-dialog="${pageContext.request.contextPath}/findReconInfo.action?reconException.reconOrderId=${rec.code}&reconException.reconExceptionType=AMOUNT_ERR"
                                href="javascript:void(0);">${rec.amountErrNum}</a></c:otherwise>
              </c:choose>
            </td>
            <td><dict:write dictId="${rec.reconStatus.name() }" dictTypeId="RECON_STATUS"></dict:write></td>
            <td><fmt:formatDate value="${rec.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${rec.msg}</td>
            <td>${rec.failureReason}</td>
            <td>${rec.matchA }</td>
            <td>${rec.matchB}</td>
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
         window.open('reconOrderExport.action?'+$('#findReconByDateForm', window.parent.document).serialize());
    }
</script>
</body>
</html>
