<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>交易订单-结果</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body class="pb-2">
  
  <div class="title-h1 fix">
    <span class="primary fl">查询结果</span>
    <a class="fr" href="javascript:void(0);" data-exprot="${pageContext.request.contextPath}/payOrderExportAction.action">导出</a>
  </div>
  
  <c:if test="${tradeOrders != null && tradeOrders.size() > 0 }">
    <div class="table-warp">
      <div class="table-sroll">
        <table class="data-table shadow--2dp  w-p-100 tow-color">
          <thead>
            <tr>
              <th>交易流水号</th>
              <th>商户订单号</th>
              <th>收款方</th>
              <th>收款方简称</th>
              <th>支付方式</th>
              <th>订单金额</th>
              <th>手续费</th>
              <th>订单状态</th>
              <th>清算状态</th>
              <th>下单时间</th>
              <th>支付时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${tradeOrders}" var="ords" varStatus="i">
              <tr>
                <input type="hidden" value='<c:out value="${ords.id }"></c:out>' />
                <input type="hidden" value='<c:out value="${ords.receiver }"></c:out>' class="hiddenReceiver" />
                <td><c:out value="${ords.code }" /></td>
                <td><c:out value="${ords.requestCode }"></c:out></td>
                <td><c:out value="${ords.receiver }" /></td>
                <td><span class="shortName" data-receiver="<c:out value="${ords.receiver }"></c:out>"></span></td>
                <td><dict:write dictId="${ords.payType }" dictTypeId="BF_SHARE_PAYTYPE"></dict:write></td>
                <td><fmt:formatNumber value="${ords.amount }" pattern="#.##" /></td>
                <td><fmt:formatNumber value="${ords.receiverFee }" pattern="#.##" /></td>
                <td><dict:write dictId="${ords.status }" dictTypeId="TRADE_ORDER_STATUS_COLOR"></dict:write></td>
                <td><dict:write dictId="${ords.clearingStatus }" dictTypeId="LIQUIDATION_STATUS_COLOR"></dict:write></td>
                <td><fmt:formatDate value="${ords.orderTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                <td><fmt:formatDate value="${ords.successPayTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                <td>
                  <button data-dialog="${pageContext.request.contextPath}/findOrderByCodeAction.action?orderCode=${ords.code }" class="btn-small">详细</button>
                </td>
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
  <script>
    // 补发通知
	$(window).load(function() {
		// ajax获取简称
		$('.shortName').each(function() {
			var span =  $(this);
			var customerNo = span.attr('data-receiver');
			Api.boss.getCustShortName(customerNo, function(shrotName) {
				if (!shrotName)
					return;
				span.text(shrotName);
			});
		});
    });
  </script>
</body>
</html>
