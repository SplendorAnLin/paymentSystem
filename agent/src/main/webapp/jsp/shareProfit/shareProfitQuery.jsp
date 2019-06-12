<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>分润信息</title>
  <%@ include file="../include/header.jsp"%>
</head>
<body>
  
  <div class="query-box">

    <!--查询表单-->
    <div class="query-form mt-10">
      <form action="shareProfitQuery.action" method="post" target="query-result">
        <div class="adaptive fix">
          <div class="item">
            <div class="input-area">
              <span class="label w-90">产品类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="product_type" styleClass="input-select" dictTypeId="BF_SHARE_PAYTYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">分润类型:</span>
              <div class="input-wrap">
                <dict:select nullOption="true" name="profit_type" styleClass="input-select" dictTypeId="PROFIT_TYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单金额:</span>
              <div class="input-wrap">
                <input type="text" name="amount_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="amount_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">分润比率:</span>
              <div class="input-wrap">
                <input type="text" name="profit_ratio_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="profit_ratio_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商费率:</span>
              <div class="input-wrap">
                <input type="text" name="agent_fee_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="agent_fee_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商利润:</span>
              <div class="input-wrap">
                <input type="text" name="agent_profit_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="agent_profit_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单手续费:</span>
              <div class="input-wrap">
                <input type="text" name="fee_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="fee_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">商户费率:</span>
              <div class="input-wrap">
                <input type="text" name="customer_fee_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="customer_fee_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">分润时间:</span>
              <div class="input-wrap">
                <input type="text" name="create_time_start" class="input-text double-input date-start default-time ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="create_time_end" class="input-text double-input date-end default-time-end ignoreEmpy">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单完成时间:</span>
              <div class="input-wrap">
                <input type="text" name="order_time_start" class="input-text double-input date-start default-time ignoreEmpy">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="order_time_end" class="input-text double-input date-end default-time-end ignoreEmpy">
              </div>
            </div>
          </div>
        </div>
        <div class="text-center mt-10">
          <button class="btn query-btn">查 询</button>
          <a class="btn" id="show_viewCount" data-action="shareProfitCount.action"  href="javascript:void(0);">查看合计</a>
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
        <p class="value "><span class="sum-amount"></span></p>
      </div>
      <div class="row fix">
        <p class="label">总笔数:</p>
        <p class="value"><span class="sum-count"></span></p>
      </div>
    </div>
    <div class="col fl">
      <div class="row fix">
        <p class="label">总手续费:</p>
        <p class="value"><span class="sum-fee"></span></p>
      </div>
      <div class="row fix">
        <p class="label">总利润:</p>
        <p class="value"><span class="sum-profit"></span></p>
      </div>
    </div>
  </div>
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 查看合计
    $('#show_viewCount').data('sumFn', function(data, viewCount) {
      var result = eval(data);
      
      $('.sum-amount', viewCount).text(utils.toFixed(result[1], 2) || '0.00');
      $('.sum-count', viewCount).text(utils.toFixed(result[0], 0) || 0);
      $('.sum-fee', viewCount).text(utils.toFixed(result[2], 2) || '0.00');
      $('.sum-profit', viewCount).text(utils.toFixed(result[3], 2) || '0.00');
    });
  </script>
</body>
</html>
