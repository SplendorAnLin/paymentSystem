<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>交易查询-结果</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
    <a class="fr" href="javascript:void(0);" data-exprot="payOrderExportAction.action">导出</a>
  </div>
  <c:if test="${tradeOrders != null && tradeOrders.size() > 0 }">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th width="80px;">商户编号</th>
              <th width="140px;">商户订单号</th>
              <th width="160px;">交易流水号</th>
              <th width="50px;">订单金额</th>
              <th width="50px;">手续费</th>
              <th width="50px;">支付方式</th>
              <th width="60px;">订单状态</th>
              <th width="60px;">清算状态</th>
              <th width="60px;">退款状态</th>
              <th width="120px;">下单时间</th>
              <th width="110px;">支付时间</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${tradeOrders}" var="ords" varStatus="i">
              <tr>
                <input type="hidden" value='<c:out value="${ords.id }"></c:out>' />
                <input type="hidden" value='<c:out value="${ords.receiver }"></c:out>' class="hiddenReceiver" />
                <td><c:out value="${ords.receiver }" /></td>
                <td><c:out value="${ords.requestCode }"></c:out></td>
                <td><c:out value="${ords.code }" /></td>
                <td><fmt:formatNumber value="${ords.amount }" pattern="#.##" /></td>
                <td><fmt:formatNumber value="${ords.receiverFee }" pattern="#.##" /></td>
                <td><dict:write dictId="${ords.payType}" dictTypeId="PRODUCT_TYPE"></dict:write></td>
                <td><dict:write dictId="${ords.status }" dictTypeId="TRADE_ORDER_STATUS_COLOR"></dict:write></td>
                <td><dict:write dictId="${ords.clearingStatus }" dictTypeId="LIQUIDATION_STATUS_COLOR"></dict:write></td>
                <td><dict:write dictId="${ords.refundStatus }" dictTypeId="REFUND_STATUS_COLOR"></dict:write></td>
                <td><fmt:formatDate value="${ords.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><fmt:formatDate value="${ords.successPayTime}" pattern="yyyy-MM-dd HH:mm:ss" />
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrap" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${tradeOrders eq null or tradeOrders.size() == 0 }">
    <p class="pd-10">无符合条件的查询结果</p>
  </c:if>

  
  
  
  <%@ include file="../../include/bodyLink.jsp"%>
</body>
</html>
