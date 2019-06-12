<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>交易查询</title>
  <%@ include file="../../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="${pageContext.request.contextPath}/findAllTradeOrderAndFeeAction.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="tradeOrderBean.receiver">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户订单号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="tradeOrderBean.requestCode">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">交易流水号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="tradeOrderBean.code">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">支付方式:</span>
              <div class="input-wrap">
                <dict:select name="tradeOrderBean.interfaceType" nullOption="true" styleClass="input-select" dictTypeId="PRODUCT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单状态:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" dictTypeId="TRADE_ORDER_STATUS" name="tradeOrderBean.status" styleClass="input-select"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">清算状态:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" dictTypeId="LIQUIDATION_STATUS" nullOption="true" name="tradeOrderBean.clearingStatus"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">退款状态:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" dictTypeId="REFUND_STATUS" nullOption="true" name="tradeOrderBean.refundStatus"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单金额:</span>
              <div class="input-wrap">
                <input type="text" name="tradeOrderBean.amountStart" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="tradeOrderBean.amountEnd" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">手续费:</span>
              <div class="input-wrap">
                <input type="text" name="tradeOrderBean.receiverfeeStart" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="tradeOrderBean.receiverfeeEnd" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">下单时间:</span>
              <div class="input-wrap">
                <input type="text" name="tradeOrderBean.orderTimeStart" class="input-text double-input date-start default-date ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="tradeOrderBean.orderTimeEnd" class="input-text double-input date-end default-date-end ignoreEmpy">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">支付时间:</span>
              <div class="input-wrap">
                <input type="text" name="tradeOrderBean.successPayTimeStart" class="input-text double-input date-start default-date ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="tradeOrderBean.successPayTimeEnd" class="input-text double-input date-end default-date-end ignoreEmpy">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a class="btn" id="show_viewCount" data-action="payOrderSumAction.action" href="javascript:void(0);">查看合计</a>
          <input type="reset" class="btn clear-form" value="重 置">
        </div>
      </form>
    </div>


    <!--查询结果框架-->
    <div class="query-result mt-20">
      <iframe name="query-result" frameborder="0"></iframe>
    </div>

  </div>
  
  
  <!-- 查看合计弹出框 -->
  <div class="viewCount fix" style="min-width: 250px;">
    <div class="col fl">
      <div class="row fix">
        <p class="label">总金额:</p>
        <p class="value"><span class="sum-amount"></span> 元</p>
      </div>
      <div class="row fix">
        <p class="label">总笔数:</p>
        <p class="value"><span class="sum-count"></span> 笔</p>
      </div>
      <div class="row fix">
        <p class="label">总交易成本:</p>
        <p class="value"><span class="sum-cost"></span> 元</p>
      </div>
    </div>
  </div>
  
  
  
  <%@ include file="../../include/bodyLink.jsp"%>
  <script>
    // 查看合计
    $('#show_viewCount').data('sumFn', function(data, viewCount) {
      var result = JSON.parse(data);
      $('.sum-amount', viewCount).text(utils.toFixed(result.am, 2) || 0);
      $('.sum-count', viewCount).text(utils.toFixed(result.al, 0) || 0);
      $('.sum-cost', viewCount).text(utils.toFixed(result.pf, 2) || 0);
    });
  </script>
</body>
</html>
