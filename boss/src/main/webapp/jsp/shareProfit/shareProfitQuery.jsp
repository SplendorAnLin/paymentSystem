<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>  
  <title>经营分析</title>
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
              <span class="label w-90">商户编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="customer_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">服务商编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="agent_no">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">接口编号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="interface_code">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单号:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="order_code">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">订单来源:</span>
              <div class="input-wrap">
                <input type="text" class="input-text" name="source">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">产品类型:</span>
              <div class="input-wrap">
                <dict:select styleClass="input-select" nullOption="true" name="product_type" dictTypeId="BF_SHARE_PAYTYPE"></dict:select>
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">分润类型:</span>
              <div class="input-wrap">
                <dict:select name="profit_type" nullOption="true" styleClass="input-select" dictTypeId="PROFIT_TYPE"></dict:select>
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
              <span class="label w-90">通道成本:</span>
              <div class="input-wrap">
                <input type="text" name="channel_cost_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="channel_cost_end" class="input-text double-input">
              </div>
            </div>
          </div>
          <div class="item">
            <div class="input-area">
              <span class="label w-90">平台利润:</span>
              <div class="input-wrap">
                <input type="text" name="platform_profit_start" class="input-text double-input">
              </div>
              <span class="cut"> - </span>
              <div class="input-wrap">
                <input type="text" name="platform_profit_end" class="input-text double-input">
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
          <a class="btn" id="show_viewCount" data-action="shareProfitCount.action" href="javascript:void(0);">查看合计</a>
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
        <p class="label">总手续费:</p>
        <p class="value"><span class="sum-fee"></span> 元</p>
      </div>
    </div>
    <div class="col fl">
      <div class="row fix">
        <p class="label">成本:</p>
        <p class="value"><span class="sum-cost"></span> 元</p>
      </div>
      <div class="row fix">
        <p class="label">平台利润:</p>
        <p class="value"><span class="sum-platform-shareProfit"></span> 元</p>
      </div>
      <div class="row fix">
        <p class="label">代理商利润:</p>
        <p class="value"><span class="sum-agent-shareProfit"></span> 元</p>
      </div>
    </div>
  </div>
  
  
  
  <%@ include file="../include/bodyLink.jsp"%>
  <script>
    // 查看合计
    $('#show_viewCount').data('sumFn', function(chunk, viewCount) {
      var result = eval(chunk);
      $('.sum-amount', viewCount).text(utils.toFixed(result[1]) || 0);
      $('.sum-count', viewCount).text(utils.toFixed(result[0], 0) || 0);
      $('.sum-cost', viewCount).text(utils.toFixed(result[3]) || 0);
      $('.sum-fee', viewCount).text(utils.toFixed(result[2]) || 0);
      $('.sum-platform-shareProfit', viewCount).text(utils.toFixed(result[5]) || 0);
      $('.sum-agent-shareProfit', viewCount).text(utils.toFixed(result[4]) || 0);
    });
  </script>
</body>
</html>
