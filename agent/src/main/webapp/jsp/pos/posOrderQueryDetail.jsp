<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>POS交易订单详情</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body style="width: 1400px;padding: 10px;">
  
  <!--交易基本信息-->
  <div class="row">
    <div class="title-h1 fix tabSwitch2">
      <span class="primary fl">交易基本信息</span>
    </div>
    <div class="content">
      <div class="table-info w-p-100">
        <div class="type-table fl w-p-50">
          <div class="type-tr">
            <div class="type-td w-100 text-primary">商户号</div>
            <div class="type-td text-secondary">${order.detail.customerNo }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">订单状态</div>
            <div class="type-td color-green"><dict:write dictId="${order.detail.status }" dictTypeId="POS_ORDER_STATUS"></dict:write></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">清分状态</div>
            <div class="type-td color-green"><dict:write dictId="${order.detail.creditStatus }" dictTypeId="CREDIT_STATUS_COLOR"></dict:write></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">订单金额</div>
            <div class="type-td text-secondary">${order.detail.amount }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">实付金额</div>
            <div class="type-td text-secondary">${order.detail.amount }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">商户手续费</div>
            <div class="type-td text-secondary">${order.detail.customerFee }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">交易流水号</div>
            <div class="type-td text-secondary">${order.detail.externalId }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">商户订单号</div>
            <div class="type-td text-secondary">${order.detail.posRequestId }</div>
          </div>
        </div>
        <div class="type-table fl w-p-50">
          <div class="type-tr">
            <div class="type-td w-100 text-primary">卡号</div>
            <div class="type-td text-secondary">${order.detail.pan }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">卡类型</div>
            <div class="type-td text-secondary"><dict:write dictId="${order.detail.cardType }" dictTypeId="POS_CARD_TYPE"></dict:write></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">接口名称</div>
            <div class="type-td text-secondary">${order.detail.bankInterfaceName }</div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">订单创建时间</div>
            <div class="type-td text-secondary"><fmt:formatDate value="${order.detail.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">结算时间</div>
            <div class="type-td text-secondary"><fmt:formatDate value="${order.detail.settleTime }" pattern="yyyy-MM-dd HH:mm:ss" /></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">完成时间</div>
            <div class="type-td text-secondary"><fmt:formatDate value="${order.detail.completeTime}" pattern="yyyy-MM-dd HH:mm:ss" /></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">入账时间</div>
            <div class="type-td text-secondary"><fmt:formatDate value="${order.detail.creditTime }" pattern="yyyy-MM-dd HH:mm:ss" /></div>
          </div>
          <div class="type-tr">
            <div class="type-td w-100 text-primary">&nbsp</div>
            <div class="type-td text-secondary">&nbsp</div>
          </div>
        </div>
      </div>
    </div>
  </div>
  
  <!--交易订单流水-->
  <div class="row mt-20">
    <div class="title-h1 fix tabSwitch2">
      <span class="primary fl">交易订单流水</span>
    </div>
    <div class="content">
      <c:if test="${order.list.size() > 0 && order.list != null}">
      <div class="table-warp">
        <div class="table-sroll">
          <table class="data-table shadow--2dp  w-p-100 tow-color">
            <thead>
              <tr>
                <th>支付流水号</th>
                <th>支付方式</th>
                <th>应付金额</th>
                <th>商户手续费</th>
                <th>支付状态</th>
                <th>支付接口编号</th>
                <th>接口成本</th>
                <th>交易开始时间</th>
                <th>交易结束时间</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${order.list }" var="pay">
                <tr>
                  <td>${pay.posRequestId }</td>
                  <td>POS收单</td>
                  <td>${pay.amount }</td>
                  <td>${pay.customerFee }</td>
                  <td><dict:write dictId="${pay.status }" dictTypeId="POS_TRANS_STATUS"></dict:write></td>
                  <td>${pay.interfaceName }</td>
                  <td><fmt:formatNumber value="${pay.bankCost }" pattern="#.##" /></td>
                  <td><fmt:formatDate value="${pay.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                  <td><fmt:formatDate value="${pay.completeTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
          </div>
        </div>
      </c:if>
    </div>
  </div>
  
  
  <%@ include file="../include/bodyLink.jsp"%>
</body>
</html>
