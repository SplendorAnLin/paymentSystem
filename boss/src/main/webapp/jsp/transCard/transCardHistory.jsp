<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>历史记录</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body class="pb-2" style="min-width:1500px!important;">
  <div class="title-h1 fix">
    <span class="primary fl">历史记录</span>
  </div>
  <c:if test="${transCardHistories != null && transCardHistories.size() > 0}">
    <div class="table-warp">
     <div class="table-sroll">
      <table class="data-table shadow--2dp w-p-100 tow-color">
        <thead>
          <tr>
			<th>商户号</th>
			<th>订单号</th>
			<th>请求接口编号</th>
			<th>金额</th>
			<th>编号</th>
			<th>结算卡编号</th>
			<th>银行卡姓名</th>
			<th>银行卡号</th>
			<th>卡类型</th>
			<th>卡属性</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${transCardHistories }" var="trs">
            <tr>
            	<td>${trs.customerNo }</td>
            	<td>${trs.orderId }</td>
            	<td>${trs.interfaceCode }</td>
            	<td><fmt:formatNumber value="${trs.amount }"  pattern="#.##" /></td>
            	<td>${trs.code }</td>
            	<td>${trs.settleCode }</td>
            	<td>${trs.accountName }</td>
            	<td>${trs.accountNo }</td>
            	<td><dict:write dictId="${trs.cardType }" dictTypeId="TRANS_TYPE"></dict:write> </td>
            	<td><dict:write dictId="${trs.cardAttr }" dictTypeId="CARD_ATTR"></dict:write> </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
     </div>
    </div>
    <div class="jump-rarp mt-20 text-center" id="PageWrapCustomize" total_count="${page.totalResult}" total="${page.totalPage}"  current="${page.currentPage}"></div>
  </c:if>
  <c:if test="${transCardHistories eq null or transCardHistories.size() == 0}">
    <p style="padding-bottom:20px" class="pd-10">无符合条件的记录</p>
  </c:if>
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
