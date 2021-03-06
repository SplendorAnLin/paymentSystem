<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>账户明细-结果</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
    <a class="fr" data-exprot="findAccountHistoryExportBy.action" href="javascript:void(0);">导出</a>
  </div>
  <c:if test="${accountRecordedDetails != null && accountRecordedDetails.size() > 0}">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>账户编号</th>
              <th>交易系统流水号</th>
              <th>资金标识</th>
              <th>业务类型</th>
              <th>支付方式</th>
              <th>原金额</th>
              <th>交易金额</th>
              <th>余额</th>
              <th>创建日期</th>
              <th>交易日期</th>
            </tr>
          </thead>
          <tbody>
        <c:forEach items="${accountRecordedDetails }" var="ard">
          <tr>
            <td>${ard.accountNo }</td>
            <td>${ard.transFlow }</td>
            <td><dict:write dictId="${ard.symbol.name() }" dictTypeId="CAPITAL_IDENTIFICATION_COLOR"></dict:write></td>
            <td><dict:write dictId="${ard.bussinessCode }" dictTypeId="BUSSINESS_CODE_COLOR"></dict:write></td>
            <td><dict:write dictId="${ard.payType }" dictTypeId="BF_INTERFACE_TYPE_NAME"></dict:write></td>
            <td>
              <c:if test="${ard.symbol.name() == 'PLUS'}">
                <fmt:formatNumber value="${ard.remainBalance - ard.transAmount}" pattern="#.##" />
              </c:if>
              <c:if test="${ard.symbol.name() == 'SUBTRACT'}">
                <fmt:formatNumber value="${ard.remainBalance + ard.transAmount}" pattern="#.##" />
              </c:if>
            </td>
            <td>
              <c:choose>
                <c:when test="${ard.symbol.name() == 'PLUS'}">
                  <span style="color: #4CAF50">+
                    <fmt:formatNumber value="${ard.transAmount }" pattern="#.##"/>
                  </span>
                </c:when>
                <c:otherwise>
                  <span style="color: red">-
                    <fmt:formatNumber value="${ard.transAmount }" pattern="#.##"/>
                  </span>
                </c:otherwise>
              </c:choose>
            </td>
            <td><fmt:formatNumber value="${ard.remainBalance }" pattern="#.##" /></td>
            <td><fmt:formatDate value="${ard.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td><fmt:formatDate value="${ard.transTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
          </tr>
        </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${accountRecordedDetails eq null or accountRecordedDetails.size() == 0}">
    <p class="pd-10">无符合条件的记录</p>
  </c:if>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
